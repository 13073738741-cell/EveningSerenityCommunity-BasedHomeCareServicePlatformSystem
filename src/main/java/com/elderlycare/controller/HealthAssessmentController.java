package com.elderlycare.controller;

import com.elderlycare.entity.HealthAssessment;
import com.elderlycare.service.HealthAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/health-assessments")
@CrossOrigin(origins = "*")
public class HealthAssessmentController {
    
    @Autowired
    private HealthAssessmentService healthAssessmentService;
    
    @PostMapping
    public ResponseEntity<HealthAssessment> createHealthAssessment(@RequestBody HealthAssessment healthAssessment) {
        HealthAssessment savedAssessment = healthAssessmentService.save(healthAssessment);
        return new ResponseEntity<>(savedAssessment, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<HealthAssessment> getHealthAssessmentById(@PathVariable Long id) {
        return healthAssessmentService.findById(id)
                .map(assessment -> new ResponseEntity<>(assessment, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping
    public ResponseEntity<List<HealthAssessment>> getAllHealthAssessments() {
        List<HealthAssessment> assessments = healthAssessmentService.findAll();
        return new ResponseEntity<>(assessments, HttpStatus.OK);
    }
    
    @GetMapping("/elderly/{elderlyId}")
    public ResponseEntity<List<HealthAssessment>> getHealthAssessmentsByElderlyId(@PathVariable Long elderlyId) {
        List<HealthAssessment> assessments = healthAssessmentService.findByElderlyId(elderlyId);
        return new ResponseEntity<>(assessments, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<HealthAssessment> updateHealthAssessment(@PathVariable Long id, @RequestBody HealthAssessment healthAssessment) {
        return healthAssessmentService.findById(id)
                .map(existingAssessment -> {
                    healthAssessment.setId(id);
                    HealthAssessment updatedAssessment = healthAssessmentService.save(healthAssessment);
                    return new ResponseEntity<>(updatedAssessment, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHealthAssessment(@PathVariable Long id) {
        if (healthAssessmentService.findById(id).isPresent()) {
            healthAssessmentService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
