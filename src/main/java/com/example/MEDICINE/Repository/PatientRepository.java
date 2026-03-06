package com.example.MEDICINE.Repository;

import com.example.MEDICINE.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
    Optional<Patient> findByEmail(String email);
    
    @Query("SELECT MAX(CAST(SUBSTRING(p.patientId, 4) AS long)) FROM Patient p")
    Optional<Long> findMaxPatientIdNumber();
    
    @Query("SELECT DISTINCT p FROM Patient p WHERE LOWER(p.firstName) LIKE LOWER(concat('%', :name,'%'))")
List<Patient> findByFirstNameContainingIgnoreCase(@Param("name") String name);
    
    List<Patient> findByLastNameContainingIgnoreCase(String lastName);

    Patient findByFirstName(String name);

   Patient findByPatientId(String patientId);
   
}