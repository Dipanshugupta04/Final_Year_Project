package com.example.MEDICINE.Service;

import com.example.MEDICINE.Model.Doctor;
import com.example.MEDICINE.Repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public List<Doctor> searchByName(String name) {
        // remove "Dr." prefix if exists
        name = name.replace("Dr.", "").trim();
    
        // split into words
        String[] parts = name.split("\\s+");
        if (parts.length == 1) {
            // search only in first or last name
            return doctorRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(parts[0], parts[0]);
        } else {
            String first = parts[0];
            String last = parts[parts.length - 1];
            return doctorRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(first, last);
        }
    }
    

    public List<Doctor> searchBySpecialization(String specialization) {
        return doctorRepository.findBySpecializationContainingIgnoreCase(specialization);
    }

    // ✅ New method: search by location
    public List<Doctor> searchByLocation(String location) {
        return doctorRepository.findByLocationContainingIgnoreCase(location);
    }

    // ✅ New method: search by specialization + location
    public List<Doctor> searchBySpecializationAndLocation(String specialization, String location) {
        return doctorRepository.findBySpecializationContainingIgnoreCaseAndLocationContainingIgnoreCase(specialization, location);
    }
}
