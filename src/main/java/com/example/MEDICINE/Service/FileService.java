package com.example.MEDICINE.Service;

import com.example.MEDICINE.Model.FileEntity;
import com.example.MEDICINE.Repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public FileEntity saveFile(MultipartFile file) throws Exception {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFileType(file.getContentType());
        fileEntity.setFileData(file.getBytes());
        return fileRepository.save(fileEntity);
    }

    public FileEntity getFileById(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found with ID: " + id));
    }
    public void deleteFile(Long id) {
        if (fileRepository.existsById(id)) {
            fileRepository.deleteById(id);
        } else {
            throw new RuntimeException("File not found with ID: " + id);
        }
    }

    // Update a file
    public FileEntity updateFile(Long id, MultipartFile newFile) {
        FileEntity existingFile = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found with ID: " + id));

        try {
            existingFile.setFileName(newFile.getOriginalFilename());
            existingFile.setFileType(newFile.getContentType());
            existingFile.setFileData(newFile.getBytes());
            return fileRepository.save(existingFile);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update file", e);
        }
    }
    
}
