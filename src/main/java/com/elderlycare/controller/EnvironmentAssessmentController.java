package com.elderlycare.controller;

import com.elderlycare.dto.ApiResponse;
import com.elderlycare.entity.EnvironmentAssessment;
import com.elderlycare.service.EnvironmentAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/environment-assessments")
@CrossOrigin(origins = "*")
public class EnvironmentAssessmentController {
    
    @Autowired
    private EnvironmentAssessmentService environmentAssessmentService;
    
    @PostMapping
    public ResponseEntity<EnvironmentAssessment> createEnvironmentAssessment(@RequestBody EnvironmentAssessment environmentAssessment) {
        EnvironmentAssessment savedAssessment = environmentAssessmentService.save(environmentAssessment);
        return new ResponseEntity<>(savedAssessment, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EnvironmentAssessment> getEnvironmentAssessmentById(@PathVariable Long id) {
        return environmentAssessmentService.findById(id)
                .map(assessment -> new ResponseEntity<>(assessment, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<EnvironmentAssessment>>> getAllEnvironmentAssessments(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Long elderlyId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        
        List<EnvironmentAssessment> allAssessments = environmentAssessmentService.findAll();
        
        // Apply filters
        if (elderlyId != null) {
            allAssessments = allAssessments.stream()
                    .filter(assessment -> assessment.getElderlyId() != null && 
                            assessment.getElderlyId().equals(elderlyId))
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
        
        ApiResponse<List<EnvironmentAssessment>> response = ApiResponse.success(allAssessments, total);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/elderly/{elderlyId}")
    public ResponseEntity<ApiResponse<List<EnvironmentAssessment>>> getEnvironmentAssessmentsByElderlyId(
            @PathVariable Long elderlyId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        List<EnvironmentAssessment> assessments = environmentAssessmentService.findByElderlyId(elderlyId);
        
        // 实现简单的分页逻辑
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, assessments.size());
        
        List<EnvironmentAssessment> pagedAssessments = assessments;
        if (fromIndex < assessments.size()) {
            pagedAssessments = assessments.subList(fromIndex, toIndex);
        } else {
            pagedAssessments = new java.util.ArrayList<>();
        }
        
        ApiResponse<List<EnvironmentAssessment>> response = ApiResponse.success(pagedAssessments, assessments.size());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EnvironmentAssessment> updateEnvironmentAssessment(@PathVariable Long id, @RequestBody EnvironmentAssessment environmentAssessment) {
        return environmentAssessmentService.findById(id)
                .map(existingAssessment -> {
                    environmentAssessment.setId(id);
                    EnvironmentAssessment updatedAssessment = environmentAssessmentService.save(environmentAssessment);
                    return new ResponseEntity<>(updatedAssessment, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnvironmentAssessment(@PathVariable Long id) {
        if (environmentAssessmentService.findById(id).isPresent()) {
            environmentAssessmentService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
