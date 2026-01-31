package com.example.MEDICINE.DTO;

import org.springframework.web.multipart.MultipartFile;

public class PrescriptionFormDataDTO {
    private String doctorId;
    private String patientId;
    private String issueDate;
    private String expiryDate;
    private String diagnosis;
    private String additionalInstructions;
    private String signType;
    private String medicationsJson; // JSON string from frontend

    private MultipartFile signatureFile;

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

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
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

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getMedicationsJson() {
        return medicationsJson;
    }

    public void setMedicationsJson(String medicationsJson) {
        this.medicationsJson = medicationsJson;
    }

    public MultipartFile getSignatureFile() {
        return signatureFile;
    }

    public void setSignatureFile(MultipartFile signatureFile) {
        this.signatureFile = signatureFile;
    }

    // Getters and Setters
    // IMPORTANT: For MultipartFile use standard setter/getter

    
}
