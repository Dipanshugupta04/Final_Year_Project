package com.example.MEDICINE.Controller;






import com.example.MEDICINE.DTO.PrescriptionDTO;
import com.example.MEDICINE.DTO.PrescriptionFormDataDTO;
import com.example.MEDICINE.DTO.PrescriptionMedicationDTO;
import com.example.MEDICINE.DTO.PrescriptionRequestDTO;
import com.example.MEDICINE.Model.Doctor;
import com.example.MEDICINE.Model.FileEntity;
import com.example.MEDICINE.Model.Patient;
import com.example.MEDICINE.Model.Prescription;
import com.example.MEDICINE.Model.PrescriptionMedication;
import com.example.MEDICINE.Repository.DoctorRepository;
import com.example.MEDICINE.Repository.FileRepository;
import com.example.MEDICINE.Repository.PatientRepository;
import com.example.MEDICINE.Repository.PrescriptionRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {
 private final AtomicLong counter = new AtomicLong(1000);
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @PostConstruct
    public void initCounter() {
        Optional<Long> maxIdNumber = patientRepository.findMaxPatientIdNumber();
        counter.set(maxIdNumber.orElse(1000L));
    }

  @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<?> createPrescription(
    @RequestParam("doctorId") String doctorId,
    @RequestParam("patientId") String patientId,
    @RequestParam("issueDate") String issueDate,
    @RequestParam("expiryDate") String expiryDate,
    @RequestParam("diagnosis") String diagnosis,
    @RequestParam(value = "additionalInstructions", required = false) String additionalInstructions,
    @RequestParam("medicationsJson") String medicationsJson,
    @RequestParam(value = "signatureFile", required = false) MultipartFile signatureFile,
    @RequestParam(value = "signType", required = false) String signType) {

  try {
    LocalDate parsedIssueDate = LocalDate.parse(issueDate);
    LocalDate parsedExpiryDate = LocalDate.parse(expiryDate);

    Doctor doctor = doctorRepository.findByDoctorId(doctorId)
        .orElseThrow(() -> new RuntimeException("Doctor not found"));
    Patient patient = patientRepository.findById(patientId)
        .orElseThrow(() -> new RuntimeException("Patient not found"));

    Prescription prescription = new Prescription();
    prescription.setPrescriptionId("RX-" + LocalDate.now().getYear() + "-" +
        String.format("%04d", counter.incrementAndGet()));
    prescription.setDoctor(doctor);
    prescription.setPatient(patient);
    prescription.setIssueDate(parsedIssueDate);
    prescription.setExpiryDate(parsedExpiryDate);
    prescription.setDiagnosis(diagnosis);
    prescription.setAdditionalInstructions(additionalInstructions);

    if (signatureFile != null && !signatureFile.isEmpty()) {
      prescription.setSignaturePath(signatureFile.getBytes());
      prescription.setSignType(signType);
    }

    ObjectMapper objectMapper = new ObjectMapper();
    List<PrescriptionMedicationDTO> medDTOs = objectMapper.readValue(
        medicationsJson, new TypeReference<List<PrescriptionMedicationDTO>>() {});
    
    List<PrescriptionMedication> meds = new ArrayList<>();
    for (PrescriptionMedicationDTO dto : medDTOs) {
      PrescriptionMedication med = new PrescriptionMedication();
      med.setMedicineName(dto.getMedicineName());
      med.setDosage(dto.getDosage());
      med.setFrequency(dto.getFrequency());
      med.setDuration(dto.getDuration());
      med.setPrescription(prescription);
      meds.add(med);
    }

    prescription.setMedications(meds);

    Prescription saved = prescriptionRepository.save(prescription);

    return ResponseEntity.ok(saved);

  } catch (Exception e) {
    e.printStackTrace();
    Map<String, String> error = new HashMap<>();
    error.put("message", "Error creating prescription: " + e.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}


    

    @GetMapping
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionDTO> getPrescriptionById(@PathVariable String id) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));
    
        PrescriptionDTO dto = new PrescriptionDTO();
        dto.setPrescriptionId(prescription.getPrescriptionId());
        dto.setIssueDate(prescription.getIssueDate());
        dto.setExpiryDate(prescription.getExpiryDate());
        dto.setDiagnosis(prescription.getDiagnosis());
        dto.setAdditionalInstructions(prescription.getAdditionalInstructions());
        dto.setSignaturePath(prescription.getSignaturePath());
        dto.setSignType(prescription.getSignType());
        dto.setDoctor(prescription.getDoctor());
        dto.setPatient(prescription.getPatient());
        dto.setMedications(prescription.getMedications());
    
        return ResponseEntity.ok(dto);
    }

    // @GetMapping("/report/{id}")
    // public ResponseEntity<List<FileEntity>> getseereport(@PathVariable String id) {

    //   List<FileEntity> file=FileRepository.findby
    //     return 
    // }
    
    
}
