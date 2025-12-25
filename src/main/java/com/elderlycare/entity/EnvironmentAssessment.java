package com.elderlycare.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "environment_assessment")
public class EnvironmentAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "elderly_id", nullable = false)
    private Long elderlyId;

    @Column(name = "assessment_date", nullable = false)
    private String assessmentDate;

    @Column(name = "housing_type")
    private String housingType;

    @Column(name = "floor_level")
    private Integer floorLevel;

    @Column(name = "safety_score")
    private Integer safetyScore;

    @Column(name = "bathroom_safety")
    private String bathroomSafety;

    @Column(name = "kitchen_safety")
    private String kitchenSafety;

    private String lighting;

    private String ventilation;

    private String accessibility;

    private String hazards;

    private String recommendations;

    @Column(name = "assessor_name")
    private String assessorName;

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

    public String getHousingType() {
        return housingType;
    }

    public void setHousingType(String housingType) {
        this.housingType = housingType;
    }

    public Integer getFloorLevel() {
        return floorLevel;
    }

    public void setFloorLevel(Integer floorLevel) {
        this.floorLevel = floorLevel;
    }

    public Integer getSafetyScore() {
        return safetyScore;
    }

    public void setSafetyScore(Integer safetyScore) {
        this.safetyScore = safetyScore;
    }

    public String getBathroomSafety() {
        return bathroomSafety;
    }

    public void setBathroomSafety(String bathroomSafety) {
        this.bathroomSafety = bathroomSafety;
    }

    public String getKitchenSafety() {
        return kitchenSafety;
    }

    public void setKitchenSafety(String kitchenSafety) {
        this.kitchenSafety = kitchenSafety;
    }

    public String getLighting() {
        return lighting;
    }

    public void setLighting(String lighting) {
        this.lighting = lighting;
    }

    public String getVentilation() {
        return ventilation;
    }

    public void setVentilation(String ventilation) {
        this.ventilation = ventilation;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    public String getHazards() {
        return hazards;
    }

    public void setHazards(String hazards) {
        this.hazards = hazards;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public String getAssessorName() {
        return assessorName;
    }

    public void setAssessorName(String assessorName) {
        this.assessorName = assessorName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}