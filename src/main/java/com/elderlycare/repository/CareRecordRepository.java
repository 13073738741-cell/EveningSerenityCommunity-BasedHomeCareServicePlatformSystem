package com.elderlycare.repository;

import com.elderlycare.entity.CareRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareRecordRepository extends JpaRepository<CareRecord, Long> {
    
    List<CareRecord> findByElderlyIdOrderByCareDateDesc(Long elderlyId);
    
    List<CareRecord> findByCareType(String careType);
    
    List<CareRecord> findByCaregiverName(String caregiverName);
}