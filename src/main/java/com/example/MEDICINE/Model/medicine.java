package com.example.MEDICINE.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

@Entity
public class medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    private String uses;

    private String dosage;

    private String maxDosage;

    private String frequency;

    private String description;

    @Column(name = "sideeffects")
    @JsonProperty("sideeffects")
    private String sideEffects;

    private String warnings;

    private String interactions;

    @Column(name = "emergency_symptoms")
    private String emergencySymptoms;

    @Column(name = "howitworks")
    @JsonProperty("howitworks")
    private String howItWorks;

    @Column(name = "details")
    private String details;
    @Column(name = "precautions")
    private String precautions;

    // Constructors
    public medicine() {}

    public medicine(String name, String type, String uses, String dosage, String maxDosage, String frequency,
                    String description, String sideEffects, String warnings, String interactions, String emergencySymptoms,
                    String howItWorks, String details) {
        this.name = name;
        this.type = type;
        this.uses = uses;
        this.dosage = dosage;
        this.maxDosage = maxDosage;
        this.frequency = frequency;
        this.description = description;
        this.sideEffects = sideEffects;
        this.warnings = warnings;
        this.interactions = interactions;
        this.emergencySymptoms = emergencySymptoms;
        this.howItWorks = howItWorks;
        this.details = details;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUses() {
        return uses;
    }

    

    public void setUses(String uses) {
        this.uses = uses;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getMaxDosage() {
        return maxDosage;
    }

    public void setMaxDosage(String maxDosage) {
        this.maxDosage = maxDosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public String getWarnings() {
        return warnings;
    }

    public void setWarnings(String warnings) {
        this.warnings = warnings;
    }

    public String getInteractions() {
        return interactions;
    }

    public void setInteractions(String interactions) {
        this.interactions = interactions;
    }

    public String getEmergencySymptoms() {
        return emergencySymptoms;
    }

    public void setEmergencySymptoms(String emergencySymptoms) {
        this.emergencySymptoms = emergencySymptoms;
    }

    public String getHowItWorks() {
        return howItWorks;
    }

    public void setHowItWorks(String howItWorks) {
        this.howItWorks = howItWorks;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPrecautions() {
        return precautions;
    }

    public void setPrecautions(String precautions) {
        this.precautions = precautions;
    }

   

    // Getters and Setters
    // (generate all getters and setters as per IDE)

    
}
