package com.example.MEDICINE.Repository;


import com.example.MEDICINE.Model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}