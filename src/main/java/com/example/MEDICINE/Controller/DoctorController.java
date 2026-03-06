package com.example.MEDICINE.Controller;

import com.example.MEDICINE.Model.Doctor;
import com.example.MEDICINE.Service.DoctorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // ✅ Save a doctor
    @PostMapping
    public Doctor saveDoctor(@RequestBody Doctor doctor) {
        return doctorService.saveDoctor(doctor);
    }

    // ✅ Get all doctors
    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    // ✅ Get doctor by ID
    @GetMapping("/{id}")
    public Optional<Doctor> getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    // ✅ Search by name
    @GetMapping("/search/name/{name}")
    public List<Doctor> searchByName(@PathVariable String name) {
        return doctorService.searchByName(name);
    }

    // ✅ Search by specialization
    @GetMapping("/search/specialization/{specialization}")
    public List<Doctor> searchBySpecialization(@PathVariable String specialization) {
        return doctorService.searchBySpecialization(specialization);
    }

    // ✅ Search by location
    @GetMapping("/search/location/{location}")
    public List<Doctor> searchByLocation(@PathVariable String location) {
        return doctorService.searchByLocation(location);
    }

    // ✅ Search by specialization + location
    @GetMapping("/search/{specialization}/{location}")
    public List<Doctor> searchBySpecializationAndLocation(
            @PathVariable String specialization,
            @PathVariable String location) {
        return doctorService.searchBySpecializationAndLocation(specialization, location);
    }
}
