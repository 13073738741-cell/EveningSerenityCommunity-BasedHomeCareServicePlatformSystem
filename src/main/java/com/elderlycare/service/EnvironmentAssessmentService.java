package com.elderlycare.service;

import com.elderlycare.entity.EnvironmentAssessment;
import com.elderlycare.repository.EnvironmentAssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnvironmentAssessmentService {
    
    @Autowired
    private EnvironmentAssessmentRepository environmentAssessmentRepository;
    
    public EnvironmentAssessment save(EnvironmentAssessment environmentAssessment) {
        return environmentAssessmentRepository.save(environmentAssessment);
    }
    
    public Optional<EnvironmentAssessment> findById(Long id) {
        return environmentAssessmentRepository.findById(id);
    }
    
    public List<EnvironmentAssessment> findByElderlyId(Long elderlyId) {
        return environmentAssessmentRepository.findByElderlyIdOrderByAssessmentDateDesc(elderlyId);
    }
    
    public List<EnvironmentAssessment> findAll() {
        return environmentAssessmentRepository.findAll();
    }
    
    public void deleteById(Long id) {
        environmentAssessmentRepository.deleteById(id);
    }
}
