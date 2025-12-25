package com.elderlycare.controller;

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
    public ResponseEntity<List<EnvironmentAssessment>> getAllEnvironmentAssessments() {
        List<EnvironmentAssessment> assessments = environmentAssessmentService.findAll();
        return new ResponseEntity<>(assessments, HttpStatus.OK);
    }
    
    @GetMapping("/elderly/{elderlyId}")
    public ResponseEntity<List<EnvironmentAssessment>> getEnvironmentAssessmentsByElderlyId(@PathVariable Long elderlyId) {
        List<EnvironmentAssessment> assessments = environmentAssessmentService.findByElderlyId(elderlyId);
        return new ResponseEntity<>(assessments, HttpStatus.OK);
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
