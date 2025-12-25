package com.elderlycare.service;

import com.elderlycare.entity.CareRecord;
import com.elderlycare.repository.CareRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CareRecordService {
    
    @Autowired
    private CareRecordRepository careRecordRepository;
    
    public CareRecord save(CareRecord careRecord) {
        return careRecordRepository.save(careRecord);
    }
    
    public Optional<CareRecord> findById(Long id) {
        return careRecordRepository.findById(id);
    }
    
    public List<CareRecord> findByElderlyId(Long elderlyId) {
        return careRecordRepository.findByElderlyIdOrderByCreateDateDesc(elderlyId);
    }
    
    public List<CareRecord> findAll() {
        return careRecordRepository.findAll();
    }
    
    public void deleteById(Long id) {
        careRecordRepository.deleteById(id);
    }
}
