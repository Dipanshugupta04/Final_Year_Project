package com.example.MEDICINE.Repository;

import com.example.MEDICINE.Model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByDoctorId(String doctorId);
    Optional<Doctor> findByEmail(String email);

    List<Doctor> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

    List<Doctor> findBySpecializationContainingIgnoreCase(String specialization);

    // ✅ New method
    List<Doctor> findByLocationContainingIgnoreCase(String location);

    // ✅ Combined search specialization + location
    List<Doctor> findBySpecializationContainingIgnoreCaseAndLocationContainingIgnoreCase(String specialization, String location);
}
