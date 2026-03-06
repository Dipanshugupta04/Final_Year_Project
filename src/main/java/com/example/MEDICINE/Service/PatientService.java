package com.example.MEDICINE.Service;

import com.example.MEDICINE.Model.Doctor;
import com.example.MEDICINE.Model.Patient;
import com.example.MEDICINE.Repository.DoctorRepository;
import com.example.MEDICINE.Repository.PatientRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Transactional
public class PatientService {
    
    private final AtomicLong counter = new AtomicLong(1000);
    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private PatientRepository patientRepository;

    @PostConstruct
    public void initCounter() {
        Optional<Long> maxIdNumber = patientRepository.findMaxPatientIdNumber();
        counter.set(maxIdNumber.orElse(1000L));
    }

    public Patient savePatient(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient cannot be null");
        }
    
        // Ensure doctor is saved before assigning to patient
//        Set<Doctor> doctors = patient.getDoctors();
// if (doctors != null) {
//     Set<Doctor> persistedDoctors = new HashSet<>();
//     for (Doctor doctor : doctors) {
//         if (doctor.getId() == null) {
//             doctor = doctorRepository.save(doctor);
//         }
//         persistedDoctors.add(doctor);
//     }
//     patient.setDoctors(persistedDoctors);
// }

    
        // Set patient ID if not already set
        if (patient.getPatientId() == null) {
            String patientId = "PT-" + String.format("%04d", counter.incrementAndGet());
            patient.setPatientId(patientId);
        }
    
        return patientRepository.save(patient);
    }


    public Patient assignDoctor(String patientId, String doctorId) {
        Patient patient = patientRepository.findByPatientId(patientId);
               
        Doctor doctor = doctorRepository.findByDoctorId(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        patient.getDoctors().add(doctor);
        return patientRepository.save(patient);
    }
    

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(String id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
    }

    public void deletePatient(String id) {
        Patient patient = getPatientById(id);
        // Remove patient from all doctors first to maintain relationship integrity
        for (Doctor doctor : patient.getDoctors()) {
            doctor.getPatients().remove(patient);
        }
        patientRepository.delete(patient);
    }
    public List<Patient> searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Search name cannot be empty");
        }
        
        // Search across all patients
        List<Patient> patients = patientRepository.findAll();
            // .stream()
            // .flatMap(patient -> patient.getDoctors().stream()
            //     .flatMap(doctor -> doctor.getPatients().stream()))
            // .filter(patient -> patient.getFirstName().equalsIgnoreCase(name))
            // .distinct()
            // .collect(Collectors.toList());
    
        if (patients.isEmpty()) {
            return null; // or throw an exception
        }
        
        // Return the first match
        return patients;
    }

    public Optional<Patient> findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        return patientRepository.findByEmail(email);
    }

    public List<Patient> findByFirstName(String firstName) {
        return patientRepository.findByFirstNameContainingIgnoreCase(firstName);
    }

    public List<Patient> findByLastName(String lastName) {
        return patientRepository.findByLastNameContainingIgnoreCase(lastName);
    }

    // Doctor relationship management
    public void addDoctorToPatient(String patientId, Doctor doctor) {
        Patient patient = getPatientById(patientId);
        patient.getDoctors().add(doctor);
        doctor.getPatients().add(patient);
        patientRepository.save(patient);
    }

    public void removeDoctorFromPatient(String patientId, Doctor doctor) {
        Patient patient = getPatientById(patientId);
        patient.getDoctors().remove(doctor);
        doctor.getPatients().remove(patient);
        patientRepository.save(patient);
    }

    public Set<Doctor> getPatientDoctors(String patientId) {
        return getPatientById(patientId).getDoctors();
    }

    public Patient updatePatient(String id, Patient patientDetails) {
        Patient patient = getPatientById(id);
        patient.setFirstName(patientDetails.getFirstName());
        patient.setLastName(patientDetails.getLastName());
        patient.setEmail(patientDetails.getEmail());
        patient.setDateOfBirth(patientDetails.getDateOfBirth());
        patient.setBloodGroup(patientDetails.getBloodGroup());
        patient.setAllergies(patientDetails.getAllergies());
        return patientRepository.save(patient);
    }
}