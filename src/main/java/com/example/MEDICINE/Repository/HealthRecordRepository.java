package com.example.MEDICINE.Repository;

import com.example.MEDICINE.Model.HealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthRecordRepository extends JpaRepository<HealthRecord, Long> {
}
