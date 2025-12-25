package com.elderlycare.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "health_assessment")
public class HealthAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "elderly_id", nullable = false)
    private Long elderlyId;

    @Column(name = "assessment_date", nullable = false)
    private String assessmentDate;

    @Column(name = "adl_score")
    private Integer adlScore;

    @Column(name = "iadl_score")
    private Integer iadlScore;

    @Column(name = "chronic_diseases")
    private String chronicDiseases;

    @Column(name = "medication_list")
    private String medicationList;

    @Column(name = "mental_status")
    private String mentalStatus;

    @Column(name = "nutrition_status")
    private String nutritionStatus;

    @Column(name = "fall_risk")
    private String fallRisk;

    @Column(name = "assessor_name")
    private String assessorName;

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

    public String getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(String assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public Integer getAdlScore() {
        return adlScore;
    }

    public void setAdlScore(Integer adlScore) {
        this.adlScore = adlScore;
    }

    public Integer getIadlScore() {
        return iadlScore;
    }

    public void setIadlScore(Integer iadlScore) {
        this.iadlScore = iadlScore;
    }

    public String getChronicDiseases() {
        return chronicDiseases;
    }

    public void setChronicDiseases(String chronicDiseases) {
        this.chronicDiseases = chronicDiseases;
    }

    public String getMedicationList() {
        return medicationList;
    }

    public void setMedicationList(String medicationList) {
        this.medicationList = medicationList;
    }

    public String getMentalStatus() {
        return mentalStatus;
    }

    public void setMentalStatus(String mentalStatus) {
        this.mentalStatus = mentalStatus;
    }

    public String getNutritionStatus() {
        return nutritionStatus;
    }

    public void setNutritionStatus(String nutritionStatus) {
        this.nutritionStatus = nutritionStatus;
    }

    public String getFallRisk() {
        return fallRisk;
    }

    public void setFallRisk(String fallRisk) {
        this.fallRisk = fallRisk;
    }

    public String getAssessorName() {
        return assessorName;
    }

    public void setAssessorName(String assessorName) {
        this.assessorName = assessorName;
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