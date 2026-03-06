package com.example.MEDICINE.DTO;

import java.time.LocalDate;
import java.util.List;

import com.example.MEDICINE.Model.Doctor;
import com.example.MEDICINE.Model.Patient;
import com.example.MEDICINE.Model.PrescriptionMedication;

public class PrescriptionDTO {

    private String prescriptionId;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String diagnosis;
    private String additionalInstructions;
    private byte[] signaturePath;
    private String signType;

    private Doctor doctor;
    private Patient patient;
    private List<PrescriptionMedication> medications;
    public String getPrescriptionId() {
        return prescriptionId;
    }
    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }
    public LocalDate getIssueDate() {
        return issueDate;
    }
    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
    public String getDiagnosis() {
        return diagnosis;
    }
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
    public String getAdditionalInstructions() {
        return additionalInstructions;
    }
    public void setAdditionalInstructions(String additionalInstructions) {
        this.additionalInstructions = additionalInstructions;
    }
    public byte[] getSignaturePath() {
        return signaturePath;
    }
    public void setSignaturePath(byte[] signaturePath) {
        this.signaturePath = signaturePath;
    }
    public String getSignType() {
        return signType;
    }
    public void setSignType(String signType) {
        this.signType = signType;
    }
    public Doctor getDoctor() {
        return doctor;
    }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    public Patient getPatient() {
        return patient;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public List<PrescriptionMedication> getMedications() {
        return medications;
    }
    public void setMedications(List<PrescriptionMedication> medications) {
        this.medications = medications;
    }

    // constructor, getters, setters


    
}
