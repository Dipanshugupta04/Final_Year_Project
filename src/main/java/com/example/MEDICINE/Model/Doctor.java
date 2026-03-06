package com.example.MEDICINE.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = true)
    private Long id;
    
    private String email;

    @Column(unique = true)
    private String doctorId;

    @Column(nullable = true)
    private String firstName;

    private String lastName;

    @Column(nullable = true)
    private String specialization;

    @Column(nullable = true)
    private String phonenumber;

    @Column(unique = true, nullable = true)
    private String licenseNumber;

    @Column(nullable = true)
    private String experience;

    // ✅ Added location field
    @Column(nullable = true)
    private String location;

    // Profile picture (stored as byte array)
    @Lob
    @Column(name = "profile_picture")
    private byte[] profilePicture;

    // Profile picture file type
    @Column(name = "profile_picture_type")
    private String profilePictureType;

    // Digital stamp (signature or official stamp)
    @Lob
    @Column(name = "doctor_stamp")
    private byte[] doctorStamp;

    // Stamp file type
    @Column(name = "stamp_type")
    private String stampType;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Prescription> prescriptions;

    @ManyToMany(mappedBy = "doctors")
    @JsonIgnore
    private Set<Patient> patients = new HashSet<>();

    // Constructors
    public Doctor() {}

    public Doctor(Long id, String email, String doctorId, String firstName, String lastName,
                  String specialization, String phonenumber, String licenseNumber, String experience,
                  String location, byte[] profilePicture, String profilePictureType,
                  byte[] doctorStamp, String stampType, List<Prescription> prescriptions,
                  Set<Patient> patients) {
        this.id = id;
        this.email = email;
        this.doctorId = doctorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.phonenumber = phonenumber;
        this.licenseNumber = licenseNumber;
        this.experience = experience;
        this.location = location; // ✅ New field
        this.profilePicture = profilePicture;
        this.profilePictureType = profilePictureType;
        this.doctorStamp = doctorStamp;
        this.stampType = stampType;
        this.prescriptions = prescriptions;
        this.patients = patients;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getProfilePictureType() {
        return profilePictureType;
    }

    public void setProfilePictureType(String profilePictureType) {
        this.profilePictureType = profilePictureType;
    }

    public byte[] getDoctorStamp() {
        return doctorStamp;
    }

    public void setDoctorStamp(byte[] doctorStamp) {
        this.doctorStamp = doctorStamp;
    }

    public String getStampType() {
        return stampType;
    }

    public void setStampType(String stampType) {
        this.stampType = stampType;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public Set<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }

    
}
