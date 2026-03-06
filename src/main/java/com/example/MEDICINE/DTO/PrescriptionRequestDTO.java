// PrescriptionRequestDTO.java
package com.example.MEDICINE.DTO;

import java.time.LocalDate;
import java.util.List;

public class PrescriptionRequestDTO {
    private String doctorId;
    private String patientId;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String diagnosis;
    private String additionalInstructions;
    private List<PrescriptionMedicationDTO> medications;
    private String signatureBase64; // Optional field for signature image
    private String signType;
    public String getDoctorId() {
        return doctorId;
    }
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
    public String getPatientId() {
        return patientId;
    }
    public void setPatientId(String patientId) {
        this.patientId = patientId;
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
    public List<PrescriptionMedicationDTO> getMedications() {
        return medications;
    }
    public void setMedications(List<PrescriptionMedicationDTO> medications) {
        this.medications = medications;
    }
    public String getSignatureBase64() {
        return signatureBase64;
    }
    public void setSignatureBase64(String signatureBase64) {
        this.signatureBase64 = signatureBase64;
    }
    public String getSignType() {
        return signType;
    }
    public void setSignType(String signType) {
        this.signType = signType;
    }

    // Getters and setters

    
}
