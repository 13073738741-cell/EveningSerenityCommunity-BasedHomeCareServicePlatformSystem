package com.elderlycare.controller;

import com.elderlycare.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<HealthAssessment>>> getAllHealthAssessments(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Long elderlyId,
            @RequestParam(required = false) String overallResult,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        
        List<HealthAssessment> allAssessments = healthAssessmentService.findAll();
        
        // Apply filters
        if (elderlyId != null) {
            allAssessments = allAssessments.stream()
                    .filter(assessment -> assessment.getElderlyId() != null && 
                            assessment.getElderlyId().equals(elderlyId))
                    .toList();
        }
        
        if (overallResult != null && !overallResult.trim().isEmpty()) {
            allAssessments = allAssessments.stream()
                    .filter(assessment -> assessment.getOverallResult() != null && 
                            assessment.getOverallResult().contains(overallResult))
                    .toList();
        }
        
        if (startDate != null && !startDate.trim().isEmpty()) {
            allAssessments = allAssessments.stream()
                    .filter(assessment -> assessment.getAssessmentDate() != null && 
                            assessment.getAssessmentDate().compareTo(startDate) >= 0)
                    .toList();
        }
        
        if (endDate != null && !endDate.trim().isEmpty()) {
            allAssessments = allAssessments.stream()
                    .filter(assessment -> assessment.getAssessmentDate() != null && 
                            assessment.getAssessmentDate().compareTo(endDate) <= 0)
                    .toList();
        }
        
        // Apply pagination
        int total = allAssessments.size();
        if (page != null && pageSize != null && page > 0 && pageSize > 0) {
            int startIndex = (page - 1) * pageSize;
            if (startIndex < allAssessments.size()) {
                int endIndex = Math.min(startIndex + pageSize, allAssessments.size());
                allAssessments = allAssessments.subList(startIndex, endIndex);
            } else {
                allAssessments = List.of();
            }
        }
        
        ApiResponse<List<HealthAssessment>> response = ApiResponse.success(allAssessments, total);
        return new ResponseEntity<>(response, HttpStatus.OK);
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
