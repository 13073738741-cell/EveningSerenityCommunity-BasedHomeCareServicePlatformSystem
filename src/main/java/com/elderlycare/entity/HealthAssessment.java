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

    // ADL分项得分
    @Column(name = "eating")
    private Integer eating;

    @Column(name = "bathing")
    private Integer bathing;

    @Column(name = "grooming")
    private Integer grooming;

    @Column(name = "dressing")
    private Integer dressing;

    @Column(name = "bowel_control")
    private Integer bowelControl;

    @Column(name = "bladder_control")
    private Integer bladderControl;

    @Column(name = "toilet")
    private Integer toilet;

    @Column(name = "transfer")
    private Integer transfer;

    @Column(name = "walking")
    private Integer walking;

    @Column(name = "stairs")
    private Integer stairs;

    // 认知能力评估
    @Column(name = "memory")
    private Integer memory;

    @Column(name = "attention")
    private Integer attention;

    @Column(name = "language")
    private Integer language;

    @Column(name = "judgment")
    private Integer judgment;

    // 心理健康评估
    @Column(name = "mood")
    private String mood;

    @Column(name = "sleep")
    private String sleep;

    @Column(name = "social")
    private String social;

    // 慢性疾病情况
    @Column(name = "diseases")
    private String diseases;

    @Column(name = "medication")
    private String medication;

    // 评估总结
    @Column(name = "overall_result")
    private String overallResult;

    @Column(name = "suggestions")
    private String suggestions;

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

    // ADL分项得分getter和setter
    public Integer getEating() {
        return eating;
    }

    public void setEating(Integer eating) {
        this.eating = eating;
    }

    public Integer getBathing() {
        return bathing;
    }

    public void setBathing(Integer bathing) {
        this.bathing = bathing;
    }

    public Integer getGrooming() {
        return grooming;
    }

    public void setGrooming(Integer grooming) {
        this.grooming = grooming;
    }

    public Integer getDressing() {
        return dressing;
    }

    public void setDressing(Integer dressing) {
        this.dressing = dressing;
    }

    public Integer getBowelControl() {
        return bowelControl;
    }

    public void setBowelControl(Integer bowelControl) {
        this.bowelControl = bowelControl;
    }

    public Integer getBladderControl() {
        return bladderControl;
    }

    public void setBladderControl(Integer bladderControl) {
        this.bladderControl = bladderControl;
    }

    public Integer getToilet() {
        return toilet;
    }

    public void setToilet(Integer toilet) {
        this.toilet = toilet;
    }

    public Integer getTransfer() {
        return transfer;
    }

    public void setTransfer(Integer transfer) {
        this.transfer = transfer;
    }

    public Integer getWalking() {
        return walking;
    }

    public void setWalking(Integer walking) {
        this.walking = walking;
    }

    public Integer getStairs() {
        return stairs;
    }

    public void setStairs(Integer stairs) {
        this.stairs = stairs;
    }

    // 认知能力评估getter和setter
    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    public Integer getAttention() {
        return attention;
    }

    public void setAttention(Integer attention) {
        this.attention = attention;
    }

    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }

    public Integer getJudgment() {
        return judgment;
    }

    public void setJudgment(Integer judgment) {
        this.judgment = judgment;
    }

    // 心理健康评估getter和setter
    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getSleep() {
        return sleep;
    }

    public void setSleep(String sleep) {
        this.sleep = sleep;
    }

    public String getSocial() {
        return social;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    // 慢性疾病情况getter和setter
    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    // 评估总结getter和setter
    public String getOverallResult() {
        return overallResult;
    }

    public void setOverallResult(String overallResult) {
        this.overallResult = overallResult;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }
}