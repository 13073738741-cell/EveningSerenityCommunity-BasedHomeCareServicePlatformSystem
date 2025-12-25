package com.elderlycare.repository;

import com.elderlycare.entity.ElderlyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElderlyInfoRepository extends JpaRepository<ElderlyInfo, Long> {
    
    Optional<ElderlyInfo> findByIdCard(String idCard);
    
    List<ElderlyInfo> findByNameContainingIgnoreCase(String name);
    
    List<ElderlyInfo> findByPhoneContaining(String phone);
}