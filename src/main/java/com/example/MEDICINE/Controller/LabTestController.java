package com.example.MEDICINE.Controller;

import com.example.MEDICINE.Model.labtest;
import com.example.MEDICINE.Service.LabTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lab-tests")
@CrossOrigin(origins = "http://localhost:3000")
public class LabTestController {

    @Autowired
    private LabTestService labTestService;

    // Get all lab tests
    @GetMapping
    public ResponseEntity<List<labtest>> getAllLabTests() {
        try {
            List<labtest> labTests = labTestService.getAllLabTests();
            if (labTests.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(labTests, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get lab test by ID
    @GetMapping("/{id}")
    public ResponseEntity<labtest> getLabTestById(@PathVariable("id") Long id) {
        Optional<labtest> labTestData = labTestService.getLabTestById(id);
        
        if (labTestData.isPresent()) {
            return new ResponseEntity<>(labTestData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Create a new lab test
    @PostMapping
    public ResponseEntity<labtest> createLabTest(@RequestBody labtest labTest) {
        try {
            labtest newLabTest = labTestService.createLabTest(labTest);
            return new ResponseEntity<>(newLabTest, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update an existing lab test
    @PutMapping("/{id}")
    public ResponseEntity<labtest> updateLabTest(@PathVariable("id") Long id, @RequestBody labtest labTest) {
        try {
            labtest updatedLabTest = labTestService.updateLabTest(id, labTest);
            if (updatedLabTest != null) {
                return new ResponseEntity<>(updatedLabTest, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a lab test
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLabTest(@PathVariable("id") Long id) {
        try {
            labTestService.deleteLabTest(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get lab tests by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<labtest>> getLabTestsByCategory(@PathVariable("category") String category) {
        try {
            List<labtest> labTests = labTestService.getLabTestsByCategory(category);
            if (labTests.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(labTests, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get lab tests by sample type
    @GetMapping("/sample-type/{sampleType}")
    public ResponseEntity<List<labtest>> getLabTestsBySampleType(@PathVariable("sampleType") String sampleType) {
        try {
            List<labtest> labTests = labTestService.getLabTestsBySampleType(sampleType);
            if (labTests.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(labTests, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Search lab tests by name
    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<labtest>> searchLabTestsByName(@PathVariable("name") String name) {
        try {
            List<labtest> labTests = labTestService.searchLabTestsByName(name);
            if (labTests.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(labTests, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get lab tests by price range
    @GetMapping("/price-range")
    public ResponseEntity<List<labtest>> getLabTestsByPriceRange(
            @RequestParam("minPrice") Double minPrice,
            @RequestParam("maxPrice") Double maxPrice) {
        try {
            List<labtest> labTests = labTestService.getLabTestsByPriceRange(minPrice, maxPrice);
            if (labTests.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(labTests, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all distinct categories
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        try {
            List<String> categories = labTestService.getAllCategories();
            if (categories.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all distinct sample types
    @GetMapping("/sample-types")
    public ResponseEntity<List<String>> getAllSampleTypes() {
        try {
            List<String> sampleTypes = labTestService.getAllSampleTypes();
            if (sampleTypes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(sampleTypes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get popular lab tests (by some criteria, e.g., most frequently ordered)
    @GetMapping("/popular")
    public ResponseEntity<List<labtest>> getPopularLabTests() {
        try {
            List<labtest> labTests = labTestService.getPopularLabTests();
            if (labTests.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(labTests, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Bulk create lab tests
    @PostMapping("/bulk")
    public ResponseEntity<List<labtest>> createBulkLabTests(@RequestBody List<labtest> labTests) {
        try {
            List<labtest> createdLabTests = labTestService.createBulkLabTests(labTests);
            return new ResponseEntity<>(createdLabTests, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get tests that require fasting
    @GetMapping("/fasting-required")
    public ResponseEntity<List<labtest>> getTestsRequiringFasting() {
        try {
            List<labtest> labTests = labTestService.getTestsRequiringFasting();
            if (labTests.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(labTests, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get tests by result time
    @GetMapping("/result-time/{resultTime}")
    public ResponseEntity<List<labtest>> getTestsByResultTime(@PathVariable("resultTime") String resultTime) {
        try {
            List<labtest> labTests = labTestService.getTestsByResultTime(resultTime);
            if (labTests.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(labTests, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}