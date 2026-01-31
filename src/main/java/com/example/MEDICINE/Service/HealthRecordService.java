package com.example.MEDICINE.Service;

import com.example.MEDICINE.Model.FileEntity;
import com.example.MEDICINE.Model.HealthRecord;
import com.example.MEDICINE.Repository.FileRepository;
import com.example.MEDICINE.Repository.HealthRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HealthRecordService {
  

    @Autowired
    private HealthRecordRepository healthRecordRepository;

    @Autowired
    private FileRepository fileRepository;

    public List<HealthRecord> getAllRecords() {
        return healthRecordRepository.findAll();
    }

    public HealthRecord addRecord(String patientName, String condition, String description, MultipartFile file) throws Exception {
        HealthRecord record = new HealthRecord();
        record.setPatientName(patientName);
        record.setCondition(condition);
        record.setDescription(description);
        record.setCreatedAt(LocalDateTime.now());

        if (file != null && !file.isEmpty()) {
            FileEntity fileEntity =new FileEntity();
            fileEntity.setFileName(file.getOriginalFilename());
            fileEntity.setFileType(file.getContentType());
            fileEntity.setFileData(file.getBytes());
            fileEntity.setFileSize(file.getSize());
            fileEntity.setUploadDate(LocalDateTime.now());
            record.setFile(fileRepository.save(fileEntity));
        }

        return healthRecordRepository.save(record);
    }

    public void deleteRecord(Long id) {
        healthRecordRepository.deleteById(id);
    }

    public HealthRecord getRecord(Long id) {
        return healthRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
    }
}
