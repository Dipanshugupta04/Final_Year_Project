package com.example.MEDICINE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.MEDICINE.Model.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, String> {}
