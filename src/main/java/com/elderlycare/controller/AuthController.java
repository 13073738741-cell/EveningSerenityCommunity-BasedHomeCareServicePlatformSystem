package com.elderlycare.controller;

import com.elderlycare.entity.User;
import com.elderlycare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        if (userService.validateUser(username, password)) {
            User user = userService.findByUsername(username);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "登录成功");
            response.put("username", user.getUsername());
            response.put("realName", user.getRealName());
            response.put("role", user.getRole());
            response.put("token", username + "-" + System.currentTimeMillis()); // 简单的 token 生成
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "用户名或密码错误");
            return ResponseEntity.status(401).body(response);
        }
    }
    
    @PostMapping("/init")
    public ResponseEntity<?> initAdmin() {
        User admin = userService.createDefaultAdmin();
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "管理员账户初始化成功");
        response.put("username", admin.getUsername());
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/user")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String token) {
        try {
            // 去掉 "Bearer " 前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            // 从token中获取username（简单实现）
            String username = token.split("-")[0];
            User user = userService.findByUsername(username);
            
            if (user != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("username", user.getUsername());
                response.put("realName", user.getRealName());
                response.put("role", user.getRole());
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "用户不存在");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "获取用户信息失败");
            return ResponseEntity.status(500).body(response);
        }
    }
    
    @PutMapping("/user/realname")
    public ResponseEntity<?> updateRealName(
        @RequestHeader("Authorization") String token,
        @RequestBody Map<String, String> request
    ) {
        try {
            // 去掉 "Bearer " 前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            String username = token.split("-")[0];
            String realName = request.get("realName");
            
            if (realName == null || realName.trim().isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "真实姓名不能为空");
                return ResponseEntity.badRequest().body(response);
            }
            
            User user = userService.updateRealName(username, realName);
            
            if (user != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "更新成功");
                response.put("realName", user.getRealName());
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "用户不存在");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "更新失败");
            return ResponseEntity.status(500).body(response);
        }
    }
}