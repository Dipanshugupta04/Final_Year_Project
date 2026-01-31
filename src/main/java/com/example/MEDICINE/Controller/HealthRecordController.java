package com.example.MEDICINE.Controller;

import com.example.MEDICINE.Model.FileEntity;
import com.example.MEDICINE.Model.HealthRecord;
import com.example.MEDICINE.Service.FileService;
import com.example.MEDICINE.Service.HealthRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://127.0.0.1:5503", "http://localhost:5503"}) // allow frontend HTML to call
public class HealthRecordController {

    @Autowired
    private HealthRecordService healthRecordService;

    @Autowired
    private FileService fileService;

    // ✅ Get Summary (simple demo: total count)
    @GetMapping("/health-summary")
    public ResponseEntity<String> getHealthSummary() {
        int total = healthRecordService.getAllRecords().size();
        return ResponseEntity.ok("Total Health Records: " + total);
    }

    // ✅ Get all records
    @GetMapping("/health-records")
    public List<HealthRecord> getAllRecords() {
        return healthRecordService.getAllRecords();
    }

    // ✅ Add new record
    @PostMapping("/health-records")
  public ResponseEntity<HealthRecord> addRecord(
            @RequestParam String patientname,
            @RequestParam String condition,
            @RequestParam String description,
            @RequestParam( value="file",required = false) MultipartFile file) throws Exception {
                System.out.println(patientname);
        return ResponseEntity.ok(
            healthRecordService.addRecord(patientname, condition, description, file)
        );
    }


    // ✅ Delete record
    @DeleteMapping("/health-records/{id}")
    public ResponseEntity<String> deleteRecord(@PathVariable Long id) {
        healthRecordService.deleteRecord(id);
        return ResponseEntity.ok("Record deleted successfully.");
    }

    // ✅ Download/View file of record
    @GetMapping("/health-records/{id}/file")
    public ResponseEntity<byte[]> getRecordFile(@PathVariable Long id) {
        HealthRecord record = healthRecordService.getRecord(id);
        FileEntity file = record.getFile();
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(file.getFileData());
    }
}
