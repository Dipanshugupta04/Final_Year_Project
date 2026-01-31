package com.example.MEDICINE.Controller;

import com.example.MEDICINE.Model.Doctor;
import com.example.MEDICINE.Model.Patient;
import com.example.MEDICINE.Repository.PatientRepository;
import com.example.MEDICINE.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    
    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientRepository patientRepository;
    
    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        Patient savedPatient = patientService.savePatient(patient);
        return ResponseEntity.ok(savedPatient);
    }
    
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable String id) {
        try {
            Patient patient = patientService.getPatientById(id);
            return ResponseEntity.ok(patient);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable String id) {
        try {
            patientService.deletePatient(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable String id, @RequestBody Patient patient) {
        try {
            Patient updatedPatient = patientService.updatePatient(id, patient);
            return ResponseEntity.ok(updatedPatient);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<Patient>> searchPatientsByName(@PathVariable String name) {
        List<Patient> patients = patientService.searchByName(name);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/search/email/{email}")
    public ResponseEntity<Patient> searchPatientByEmail(@PathVariable String email) {
        Optional<Patient> patient = patientService.findByEmail(email);
        return patient.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/{patientId}/assign-doctor/{doctorId}")
    public Patient assignDoctor(
            @PathVariable String patientId,
            @PathVariable String doctorId) {
        return patientService.assignDoctor(patientId, doctorId);
    }

    @GetMapping("/{patientId}/doctors")
    public Set<Doctor> getDoctors(@PathVariable String patientId) {
        Patient patient =patientRepository.findByPatientId(patientId);
        return patient.getDoctors();
    }
}