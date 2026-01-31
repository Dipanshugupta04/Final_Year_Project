package com.example.MEDICINE.Repository;

import com.example.MEDICINE.Model.labtest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabTestRepository extends JpaRepository<labtest, Long> {
    List<labtest> findByCategoryIgnoreCase(String category);
    List<labtest> findBySampleTypeIgnoreCase(String sampleType);
    List<labtest> findByTestNameContainingIgnoreCase(String name);
    List<labtest> findByPriceBetween(Double minPrice, Double maxPrice);
    
    @Query("SELECT DISTINCT l.category FROM labtest l")
    List<String> findDistinctCategories();
    
    @Query("SELECT DISTINCT l.sampleType FROM labtest l")
    List<String> findDistinctSampleTypes();
    
    List<labtest> findTop5ByOrderByIdDesc();
    List<labtest> findByPreparationContainingIgnoreCase(String preparation);
    List<labtest> findByResultTimeContainingIgnoreCase(String resultTime);
}