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
}