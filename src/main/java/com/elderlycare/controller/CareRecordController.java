package com.elderlycare.controller;

import com.elderlycare.dto.ApiResponse;
import com.elderlycare.entity.CareRecord;
import com.elderlycare.service.CareRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/care-records")
@CrossOrigin(origins = "*")
public class CareRecordController {
    
    @Autowired
    private CareRecordService careRecordService;
    
    @PostMapping
    public ResponseEntity<CareRecord> createCareRecord(@RequestBody CareRecord careRecord) {
        CareRecord savedRecord = careRecordService.save(careRecord);
        return new ResponseEntity<>(savedRecord, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CareRecord> getCareRecordById(@PathVariable Long id) {
        return careRecordService.findById(id)
                .map(record -> new ResponseEntity<>(record, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<CareRecord>>> getAllCareRecords(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String elderlyName,
            @RequestParam(required = false) String careType,
            @RequestParam(required = false) String caregiverName,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        
        List<CareRecord> allRecords = careRecordService.findAll();
        
        // Apply filters
        if (elderlyName != null && !elderlyName.trim().isEmpty()) {
            allRecords = allRecords.stream()
                    .filter(record -> record.getElderlyName() != null && 
                            record.getElderlyName().contains(elderlyName))
                    .toList();
        }
        
        if (careType != null && !careType.trim().isEmpty()) {
            allRecords = allRecords.stream()
                    .filter(record -> record.getCareType() != null && 
                            record.getCareType().contains(careType))
                    .toList();
        }
        
        if (caregiverName != null && !caregiverName.trim().isEmpty()) {
            allRecords = allRecords.stream()
                    .filter(record -> record.getCaregiverName() != null && 
                            record.getCaregiverName().contains(caregiverName))
                    .toList();
        }
        
        // Apply date range filter
        if (startDate != null && !startDate.trim().isEmpty()) {
            allRecords = allRecords.stream()
                    .filter(record -> record.getCareDate() != null && 
                            record.getCareDate().toString().compareTo(startDate) >= 0)
                    .toList();
        }
        
        if (endDate != null && !endDate.trim().isEmpty()) {
            allRecords = allRecords.stream()
                    .filter(record -> record.getCareDate() != null && 
                            record.getCareDate().toString().compareTo(endDate) <= 0)
                    .toList();
        }
        
        // Apply pagination
        int total = allRecords.size();
        if (page != null && pageSize != null && page > 0 && pageSize > 0) {
            int startIndex = (page - 1) * pageSize;
            if (startIndex < allRecords.size()) {
                int endIndex = Math.min(startIndex + pageSize, allRecords.size());
                allRecords = allRecords.subList(startIndex, endIndex);
            } else {
                allRecords = List.of();
            }
        }
        
        ApiResponse<List<CareRecord>> response = ApiResponse.success(allRecords, total);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/elderly/{elderlyId}")
    public ResponseEntity<List<CareRecord>> getCareRecordsByElderlyId(@PathVariable Long elderlyId) {
        List<CareRecord> records = careRecordService.findByElderlyId(elderlyId);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }
    
    @GetMapping("/care-type/{careType}")
    public ResponseEntity<List<CareRecord>> getCareRecordsByCareType(@PathVariable String careType) {
        List<CareRecord> records = careRecordService.findByCareType(careType);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }
    
    @GetMapping("/caregiver/{caregiverName}")
    public ResponseEntity<List<CareRecord>> getCareRecordsByCaregiverName(@PathVariable String caregiverName) {
        List<CareRecord> records = careRecordService.findByCaregiverName(caregiverName);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CareRecord> updateCareRecord(@PathVariable Long id, @RequestBody CareRecord careRecord) {
        return careRecordService.findById(id)
                .map(existingRecord -> {
                    careRecord.setId(id);
                    CareRecord updatedRecord = careRecordService.save(careRecord);
                    return new ResponseEntity<>(updatedRecord, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCareRecord(@PathVariable Long id) {
        if (careRecordService.findById(id).isPresent()) {
            careRecordService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
