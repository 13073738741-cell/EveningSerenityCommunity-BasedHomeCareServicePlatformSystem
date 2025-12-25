package com.elderlycare.service;

import com.elderlycare.entity.ElderlyInfo;
import com.elderlycare.repository.ElderlyInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ElderlyInfoService {
    
    @Autowired
    private ElderlyInfoRepository elderlyInfoRepository;
    
    public ElderlyInfo save(ElderlyInfo elderlyInfo) {
        return elderlyInfoRepository.save(elderlyInfo);
    }
    
    public Optional<ElderlyInfo> findById(Long id) {
        return elderlyInfoRepository.findById(id);
    }
    
    public List<ElderlyInfo> findAll() {
        return elderlyInfoRepository.findAll();
    }
    
    public void deleteById(Long id) {
        elderlyInfoRepository.deleteById(id);
    }
    
    public Optional<ElderlyInfo> findByIdCard(String idCard) {
        return elderlyInfoRepository.findByIdCard(idCard);
    }
    
    public List<ElderlyInfo> searchByName(String name) {
        return elderlyInfoRepository.findByNameContainingIgnoreCase(name);
    }
    
    public List<ElderlyInfo> searchByPhone(String phone) {
        return elderlyInfoRepository.findByPhoneContaining(phone);
    }
}