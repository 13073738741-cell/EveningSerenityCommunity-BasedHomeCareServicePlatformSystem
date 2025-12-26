package com.elderlycare.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "care_record")
public class CareRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "elderly_id", nullable = false)
    private Long elderlyId;

    @Column(name = "elderly_name", nullable = false, length = 100)
    private String elderlyName;

    @Column(name = "care_date", nullable = false)
    private LocalDate careDate;

    @Column(name = "care_type", nullable = false, length = 50)
    private String careType;

    @Column(name = "care_content", nullable = false, columnDefinition = "TEXT")
    private String careContent;

    @Column(name = "caregiver_name", nullable = false, length = 100)
    private String caregiverName;

    @Column(name = "caregiver_phone", length = 20)
    private String caregiverPhone;

    @Column(name = "elderly_feedback", columnDefinition = "TEXT")
    private String elderlyFeedback;

    @Column(name = "care_effect", length = 50)
    private String careEffect;

    @Column(name = "next_care_date")
    private LocalDate nextCareDate;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    // 护理等级
    @Column(name = "care_level", length = 50)
    private String careLevel;

    // 关爱时长（小时）
    @Column(name = "care_duration")
    private Double careDuration;

    // 生活照料项目
    @Column(name = "life_care_items", columnDefinition = "TEXT")
    private String lifeCareItems;

    // 生活照料记录
    @Column(name = "life_care_record", columnDefinition = "TEXT")
    private String lifeCareRecord;

    // 血压
    @Column(name = "blood_pressure", length = 20)
    private String bloodPressure;

    // 心率（次/分）
    @Column(name = "heart_rate")
    private Integer heartRate;

    // 体温（℃）
    @Column(name = "temperature")
    private Double temperature;

    // 血糖（mmol/L）
    @Column(name = "blood_sugar")
    private Double bloodSugar;

    // 用药情况
    @Column(name = "medication", columnDefinition = "TEXT")
    private String medication;

    // 健康监测记录
    @Column(name = "health_monitor_record", columnDefinition = "TEXT")
    private String healthMonitorRecord;

    // 心理状态
    @Column(name = "mental_state", length = 50)
    private String mentalState;

    // 心理关怀内容
    @Column(name = "mental_care_content", columnDefinition = "TEXT")
    private String mentalCareContent;

    // 心理关怀记录
    @Column(name = "mental_care_record", columnDefinition = "TEXT")
    private String mentalCareRecord;

    // 关爱总结
    @Column(name = "care_summary", columnDefinition = "TEXT")
    private String careSummary;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 默认构造函数
    public CareRecord() {
    }

    // Getter和Setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getElderlyId() {
        return elderlyId;
    }

    public void setElderlyId(Long elderlyId) {
        this.elderlyId = elderlyId;
    }

    public String getElderlyName() {
        return elderlyName;
    }

    public void setElderlyName(String elderlyName) {
        this.elderlyName = elderlyName;
    }

    public LocalDate getCareDate() {
        return careDate;
    }

    public void setCareDate(LocalDate careDate) {
        this.careDate = careDate;
    }

    public String getCareType() {
        return careType;
    }

    public void setCareType(String careType) {
        this.careType = careType;
    }

    public String getCareContent() {
        return careContent;
    }

    public void setCareContent(String careContent) {
        this.careContent = careContent;
    }

    public String getCaregiverName() {
        return caregiverName;
    }

    public void setCaregiverName(String caregiverName) {
        this.caregiverName = caregiverName;
    }

    public String getCaregiverPhone() {
        return caregiverPhone;
    }

    public void setCaregiverPhone(String caregiverPhone) {
        this.caregiverPhone = caregiverPhone;
    }

    public String getElderlyFeedback() {
        return elderlyFeedback;
    }

    public void setElderlyFeedback(String elderlyFeedback) {
        this.elderlyFeedback = elderlyFeedback;
    }

    public String getCareEffect() {
        return careEffect;
    }

    public void setCareEffect(String careEffect) {
        this.careEffect = careEffect;
    }

    public LocalDate getNextCareDate() {
        return nextCareDate;
    }

    public void setNextCareDate(LocalDate nextCareDate) {
        this.nextCareDate = nextCareDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCareLevel() {
        return careLevel;
    }

    public void setCareLevel(String careLevel) {
        this.careLevel = careLevel;
    }

    public Double getCareDuration() {
        return careDuration;
    }

    public void setCareDuration(Double careDuration) {
        this.careDuration = careDuration;
    }

    public String getLifeCareItems() {
        return lifeCareItems;
    }

    public void setLifeCareItems(String lifeCareItems) {
        this.lifeCareItems = lifeCareItems;
    }

    public String getLifeCareRecord() {
        return lifeCareRecord;
    }

    public void setLifeCareRecord(String lifeCareRecord) {
        this.lifeCareRecord = lifeCareRecord;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(Double bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getHealthMonitorRecord() {
        return healthMonitorRecord;
    }

    public void setHealthMonitorRecord(String healthMonitorRecord) {
        this.healthMonitorRecord = healthMonitorRecord;
    }

    public String getMentalState() {
        return mentalState;
    }

    public void setMentalState(String mentalState) {
        this.mentalState = mentalState;
    }

    public String getMentalCareContent() {
        return mentalCareContent;
    }

    public void setMentalCareContent(String mentalCareContent) {
        this.mentalCareContent = mentalCareContent;
    }

    public String getMentalCareRecord() {
        return mentalCareRecord;
    }

    public void setMentalCareRecord(String mentalCareRecord) {
        this.mentalCareRecord = mentalCareRecord;
    }

    public String getCareSummary() {
        return careSummary;
    }

    public void setCareSummary(String careSummary) {
        this.careSummary = careSummary;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
