package com.example.MEDICINE.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.example.MEDICINE.Model.medicine;
import com.example.MEDICINE.Service.MedicineService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController

@RequestMapping("/medicines")
@CrossOrigin(origins = {"http://127.0.0.1:5502", "http://localhost:5502"})
public class MedicineController {
    @Autowired
    private MedicineService medicineService;
    @PostMapping("/addmedicine")
    public ResponseEntity<?> AddMedicine(@Validated @RequestBody List<medicine> medicine, BindingResult result) {
        
        // Add medicine to the database
  if (result.hasErrors()) {
            // Return validation errors
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        try {
            List<medicine> add = medicineService.add(medicine);
            return ResponseEntity.status(HttpStatus.CREATED).body(add);
        } catch (IllegalArgumentException e) {
            // Handle duplicate email
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    @Transactional
    @PutMapping("/update/{id}")
    public medicine updateMedicine(@PathVariable Long id, @RequestBody medicine updatedMedicine) {
        return medicineService.updateMedicine(id, updatedMedicine);
    }

    // Delete a medicine
    @DeleteMapping("/delete/{id}")
    public String deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return "Medicine with ID " + id + " deleted successfully.";
    }
  @GetMapping()
    public List<medicine> getAllMedicines() {
        return medicineService.getAllMedicines();
    }

    // Retrieve a specific medicine by ID
    @GetMapping("/getmedicine/{id}")
    public medicine getMedicineById(@PathVariable Long id) {
        return medicineService.getMedicineById(id);
    }
    @GetMapping("/getmedicinebyname/{name}")
    public medicine getMedicineByName(@PathVariable String name) {
        return medicineService.getMedicineByName(name);
    }
    }
    
    
