package com.elderlycare.repository;

import com.elderlycare.entity.HealthAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthAssessmentRepository extends JpaRepository<HealthAssessment, Long> {
    
    List<HealthAssessment> findByElderlyIdOrderByAssessmentDateDesc(Long elderlyId);
}