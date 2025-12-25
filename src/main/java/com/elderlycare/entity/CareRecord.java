package com.elderlycare.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "care_record")
public class CareRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "elderly_id", nullable = false)
    private Long elderlyId;

    @Column(name = "create_date", nullable = false)
    private String createDate;

    @Column(name = "care_level")
    private String careLevel;

    @Column(name = "daily_care_needs")
    private String dailyCareNeeds;

    @Column(name = "medical_care_needs")
    private String medicalCareNeeds;

    @Column(name = "social_support")
    private String socialSupport;

    @Column(name = "emergency_plan")
    private String emergencyPlan;

    @Column(name = "caregiver_name")
    private String caregiverName;

    @Column(name = "caregiver_phone")
    private String caregiverPhone;

    @Column(name = "next_review_date")
    private String nextReviewDate;

    private String notes;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCareLevel() {
        return careLevel;
    }

    public void setCareLevel(String careLevel) {
        this.careLevel = careLevel;
    }

    public String getDailyCareNeeds() {
        return dailyCareNeeds;
    }

    public void setDailyCareNeeds(String dailyCareNeeds) {
        this.dailyCareNeeds = dailyCareNeeds;
    }

    public String getMedicalCareNeeds() {
        return medicalCareNeeds;
    }

    public void setMedicalCareNeeds(String medicalCareNeeds) {
        this.medicalCareNeeds = medicalCareNeeds;
    }

    public String getSocialSupport() {
        return socialSupport;
    }

    public void setSocialSupport(String socialSupport) {
        this.socialSupport = socialSupport;
    }

    public String getEmergencyPlan() {
        return emergencyPlan;
    }

    public void setEmergencyPlan(String emergencyPlan) {
        this.emergencyPlan = emergencyPlan;
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

    public String getNextReviewDate() {
        return nextReviewDate;
    }

    public void setNextReviewDate(String nextReviewDate) {
        this.nextReviewDate = nextReviewDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}