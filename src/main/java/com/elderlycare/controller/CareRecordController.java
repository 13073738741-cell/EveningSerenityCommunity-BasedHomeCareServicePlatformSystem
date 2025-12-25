package com.elderlycare.controller;

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
    public ResponseEntity<List<CareRecord>> getAllCareRecords() {
        List<CareRecord> records = careRecordService.findAll();
        return new ResponseEntity<>(records, HttpStatus.OK);
    }
    
    @GetMapping("/elderly/{elderlyId}")
    public ResponseEntity<List<CareRecord>> getCareRecordsByElderlyId(@PathVariable Long elderlyId) {
        List<CareRecord> records = careRecordService.findByElderlyId(elderlyId);
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
