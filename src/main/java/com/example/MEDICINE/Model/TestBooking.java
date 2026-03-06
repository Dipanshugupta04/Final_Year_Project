package com.example.MEDICINE.Model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "test_booking")
public class TestBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patientName;
    private String doctorName;
    private String location;
    private LocalDate bookingDate;
    private String status;
    private String priority;
    private String insurance;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "test_booking_lab_tests",
        joinColumns = @JoinColumn(name = "test_booking_id"),
        inverseJoinColumns = @JoinColumn(name = "lab_test_id")
    )
    private List<labtest> tests;

    // Constructors
    public TestBooking() {
    }

    public TestBooking(String patientName, String doctorName, String location, LocalDate bookingDate, String status, String priority, String insurance, List<labtest> tests) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.location = location;
        this.bookingDate = bookingDate;
        this.status = status;
        this.priority = priority;
        this.insurance = insurance;
        this.tests = tests;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public List<labtest> getTests() {
        return tests;
    }

    public void setTests(List<labtest> tests) {
        this.tests = tests;
    }
}