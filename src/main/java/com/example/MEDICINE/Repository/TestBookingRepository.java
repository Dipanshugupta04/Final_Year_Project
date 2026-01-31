package com.example.MEDICINE.Repository;

import com.example.MEDICINE.Model.TestBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TestBookingRepository extends JpaRepository<TestBooking, Long> {
    
    List<TestBooking> findByPatientNameContainingIgnoreCase(String patientName);
    
    List<TestBooking> findByDoctorNameContainingIgnoreCase(String doctorName);
    
    List<TestBooking> findByBookingDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<TestBooking> findByStatus(String status);
    
    List<TestBooking> findByPriority(String priority);
    
    List<TestBooking> findByBookingDateGreaterThanEqual(LocalDate date);
    
    @Query("SELECT tb FROM TestBooking tb WHERE LOWER(tb.patientName) LIKE LOWER(CONCAT('%', :patientName, '%')) AND LOWER(tb.doctorName) LIKE LOWER(CONCAT('%', :doctorName, '%'))")
    List<TestBooking> findByPatientNameContainingIgnoreCaseAndDoctorNameContainingIgnoreCase(
            @Param("patientName") String patientName, 
            @Param("doctorName") String doctorName);
    
    @Query("SELECT tb FROM TestBooking tb WHERE LOWER(tb.patientName) LIKE LOWER(CONCAT('%', :patientName, '%')) AND LOWER(tb.doctorName) LIKE LOWER(CONCAT('%', :doctorName, '%')) AND tb.status = :status AND tb.priority = :priority")
    List<TestBooking> findByPatientNameContainingIgnoreCaseAndDoctorNameContainingIgnoreCaseAndStatusAndPriority(
            @Param("patientName") String patientName, 
            @Param("doctorName") String doctorName,
            @Param("status") String status,
            @Param("priority") String priority);
}