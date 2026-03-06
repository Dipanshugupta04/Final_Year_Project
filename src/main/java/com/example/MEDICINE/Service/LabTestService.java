package com.example.MEDICINE.Service;

import com.example.MEDICINE.Model.labtest;
import com.example.MEDICINE.Repository.LabTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabTestService {

    @Autowired
    private LabTestRepository labTestRepository;

    public List<labtest> getAllLabTests() {
        return labTestRepository.findAll();
    }

    public Optional<labtest> getLabTestById(Long id) {
        return labTestRepository.findById(id);
    }

    public labtest createLabTest(labtest labTest) {
        return labTestRepository.save(labTest);
    }

    public labtest updateLabTest(Long id, labtest labTestDetails) {
        Optional<labtest> labTestOptional = labTestRepository.findById(id);
        
        if (labTestOptional.isPresent()) {
            labtest labTest = labTestOptional.get();
            
            // Update fields if they are provided in the request
            if (labTestDetails.getTestName() != null) {
                labTest.setTestName(labTestDetails.getTestName());
            }
            if (labTestDetails.getDescription() != null) {
                labTest.setDescription(labTestDetails.getDescription());
            }
            if (labTestDetails.getPrice() != null) {
                labTest.setPrice(labTestDetails.getPrice());
            }
            if (labTestDetails.getCategory() != null) {
                labTest.setCategory(labTestDetails.getCategory());
            }
            if (labTestDetails.getSampleType() != null) {
                labTest.setSampleType(labTestDetails.getSampleType());
            }
            if (labTestDetails.getPreparation() != null) {
                labTest.setPreparation(labTestDetails.getPreparation());
            }
            if (labTestDetails.getResultTime() != null) {
                labTest.setResultTime(labTestDetails.getResultTime());
            }
            
            return labTestRepository.save(labTest);
        }
        return null;
    }

    public void deleteLabTest(Long id) {
        labTestRepository.deleteById(id);
    }

    public List<labtest> getLabTestsByCategory(String category) {
        return labTestRepository.findByCategoryIgnoreCase(category);
    }

    public List<labtest> getLabTestsBySampleType(String sampleType) {
        return labTestRepository.findBySampleTypeIgnoreCase(sampleType);
    }

    public List<labtest> searchLabTestsByName(String name) {
        return labTestRepository.findByTestNameContainingIgnoreCase(name);
    }

    public List<labtest> getLabTestsByPriceRange(Double minPrice, Double maxPrice) {
        return labTestRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public List<String> getAllCategories() {
        return labTestRepository.findDistinctCategories();
    }

    public List<String> getAllSampleTypes() {
        return labTestRepository.findDistinctSampleTypes();
    }

    public List<labtest> getPopularLabTests() {
        return labTestRepository.findTop5ByOrderByIdDesc();
    }

    public List<labtest> createBulkLabTests(List<labtest> labTests) {
        return labTestRepository.saveAll(labTests);
    }

    public List<labtest> getTestsRequiringFasting() {
        return labTestRepository.findByPreparationContainingIgnoreCase("fasting");
    }

    public List<labtest> getTestsByResultTime(String resultTime) {
        return labTestRepository.findByResultTimeContainingIgnoreCase(resultTime);
    }
}