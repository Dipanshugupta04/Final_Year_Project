package com.example.MEDICINE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import com.example.MEDICINE.Model.medicine;
import com.example.MEDICINE.Repository.MedicineRepo;

@Service
public class MedicineService {
    @Autowired
    private MedicineRepo medicineRepo;
    public List<medicine> add(List<medicine> medicine) {
      
       
        // Save the user
        return medicineRepo.saveAll(medicine);
    }


    // Update a medicine
   
    public medicine updateMedicine(Long id, medicine updatedMedicine) {
        try {
            Optional<medicine> existingMedicine = medicineRepo.findById(id);
            if (existingMedicine.isPresent()) {
                medicine med = existingMedicine.get();
                med.setName(updatedMedicine.getName());
                med.setUses(updatedMedicine.getUses());
                med.setDosage(updatedMedicine.getDosage());
                med.setSideEffects(updatedMedicine.getSideEffects());
                med.setHowItWorks(updatedMedicine.getHowItWorks());

                // Save the updated medicine
                return medicineRepo.save(med);
            } else {
                throw new RuntimeException("Medicine not found with ID: " + id);
            }
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new RuntimeException("The record was updated by another transaction. Please reload and try again.");
        }
    }
    


    // Delete a medicine by ID
    public void deleteMedicine(Long id) {
        if (medicineRepo.existsById(id)) {
            medicineRepo.deleteById(id);
        } else {
            throw new RuntimeException("Medicine not found with ID: " + id);
        }
    }
    public List<medicine> getAllMedicines() {
        return medicineRepo.findAll();
    }

    // Retrieve a specific medicine by ID
    public medicine getMedicineById(Long id) {
        return medicineRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine with ID " + id + " not found."));
    }


    public medicine getMedicineByName(String name) {
        return medicineRepo.findByName(name).orElseThrow(()-> new RuntimeException("Medicine With Name"+name+"not found"));
    
    }
}