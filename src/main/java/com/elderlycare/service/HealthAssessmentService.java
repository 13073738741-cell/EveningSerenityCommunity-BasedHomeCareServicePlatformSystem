package com.elderlycare.service;

import com.elderlycare.entity.HealthAssessment;
import com.elderlycare.repository.HealthAssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HealthAssessmentService {
    
    @Autowired
    private HealthAssessmentRepository healthAssessmentRepository;
    
    public HealthAssessment save(HealthAssessment healthAssessment) {
        return healthAssessmentRepository.save(healthAssessment);
    }
    
    public Optional<HealthAssessment> findById(Long id) {
        return healthAssessmentRepository.findById(id);
    }
    
    public List<HealthAssessment> findByElderlyId(Long elderlyId) {
        return healthAssessmentRepository.findByElderlyIdOrderByAssessmentDateDesc(elderlyId);
    }
    
    public List<HealthAssessment> findAll() {
        return healthAssessmentRepository.findAll();
    }
    
    public void deleteById(Long id) {
        healthAssessmentRepository.deleteById(id);
    }
}