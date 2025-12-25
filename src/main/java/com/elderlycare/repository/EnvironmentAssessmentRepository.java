package com.elderlycare.repository;

import com.elderlycare.entity.EnvironmentAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnvironmentAssessmentRepository extends JpaRepository<EnvironmentAssessment, Long> {
    
    List<EnvironmentAssessment> findByElderlyIdOrderByAssessmentDateDesc(Long elderlyId);
}