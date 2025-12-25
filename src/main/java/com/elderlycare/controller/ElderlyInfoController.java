package com.elderlycare.controller;

import com.elderlycare.entity.ElderlyInfo;
import com.elderlycare.service.ElderlyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/elderly")
@CrossOrigin(origins = "*")
public class ElderlyInfoController {
    
    @Autowired
    private ElderlyInfoService elderlyInfoService;
    
    @PostMapping
    public ResponseEntity<ElderlyInfo> createElderly(@RequestBody ElderlyInfo elderlyInfo) {
        ElderlyInfo savedElderly = elderlyInfoService.save(elderlyInfo);
        return new ResponseEntity<>(savedElderly, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ElderlyInfo> getElderlyById(@PathVariable Long id) {
        return elderlyInfoService.findById(id)
                .map(elderly -> new ResponseEntity<>(elderly, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping
    public ResponseEntity<List<ElderlyInfo>> getAllElderly() {
        List<ElderlyInfo> elderlyList = elderlyInfoService.findAll();
        return new ResponseEntity<>(elderlyList, HttpStatus.OK);
    }
    
    @GetMapping("/search/name")
    public ResponseEntity<List<ElderlyInfo>> searchByName(@RequestParam String name) {
        List<ElderlyInfo> results = elderlyInfoService.searchByName(name);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
    
    @GetMapping("/search/phone")
    public ResponseEntity<List<ElderlyInfo>> searchByPhone(@RequestParam String phone) {
        List<ElderlyInfo> results = elderlyInfoService.searchByPhone(phone);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
    
    @GetMapping("/search/idcard")
    public ResponseEntity<ElderlyInfo> searchByIdCard(@RequestParam String idCard) {
        return elderlyInfoService.findByIdCard(idCard)
                .map(elderly -> new ResponseEntity<>(elderly, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ElderlyInfo> updateElderly(@PathVariable Long id, @RequestBody ElderlyInfo elderlyInfo) {
        return elderlyInfoService.findById(id)
                .map(existingElderly -> {
                    elderlyInfo.setId(id);
                    ElderlyInfo updatedElderly = elderlyInfoService.save(elderlyInfo);
                    return new ResponseEntity<>(updatedElderly, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteElderly(@PathVariable Long id) {
        if (elderlyInfoService.findById(id).isPresent()) {
            elderlyInfoService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
