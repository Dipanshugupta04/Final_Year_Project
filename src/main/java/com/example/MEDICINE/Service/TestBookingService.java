package com.example.MEDICINE.Service;

import com.example.MEDICINE.DTO.TestBookingRequestDTO;
import com.example.MEDICINE.Model.TestBooking;
import com.example.MEDICINE.Model.labtest;
import com.example.MEDICINE.Repository.TestBookingRepository;
import com.example.MEDICINE.Repository.LabTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TestBookingService {

    @Autowired
    private TestBookingRepository testBookingRepository;

    @Autowired
    private LabTestRepository labTestRepository;

    public List<TestBooking> getAllTestBookings() {
        return testBookingRepository.findAll();
    }

    public Optional<TestBooking> getTestBookingById(Long id) {
        return testBookingRepository.findById(id);
    }

    @Transactional
    public TestBooking createTestBooking(TestBookingRequestDTO bookingRequest) {
            System.out.println("Booking data:"+bookingRequest.getTestIds());

        TestBooking testBooking = new TestBooking();
    
        // Set basic fields
        testBooking.setPatientName(bookingRequest.getPatientName());
        testBooking.setDoctorName(bookingRequest.getDoctorName());
        testBooking.setLocation(bookingRequest.getLocation());
        testBooking.setBookingDate(bookingRequest.getBookingDate());
        testBooking.setStatus(
            bookingRequest.getStatus() != null ? bookingRequest.getStatus() : "Scheduled"
        );
        testBooking.setPriority(bookingRequest.getPriority());
        testBooking.setInsurance(bookingRequest.getInsurance());
    
        // Fetch existing tests from DB
        if (bookingRequest.getTestIds() != null && !bookingRequest.getTestIds().isEmpty()) {
            List<labtest> tests = labTestRepository.findAllById(bookingRequest.getTestIds());
    
            if (tests.size() != bookingRequest.getTestIds().size()) {
                throw new IllegalArgumentException("Some test IDs do not exist");
            }
    
            testBooking.setTests(tests);
        }
    
        // save booking → this will insert into join table
        return testBookingRepository.save(testBooking);
    }
    

    // For backward compatibility
    @Transactional
    public TestBooking createTestBooking(TestBooking testBooking) {
        // Set default status if not provided
        if (testBooking.getStatus() == null || testBooking.getStatus().isEmpty()) {
            testBooking.setStatus("Scheduled");
        }
        return testBookingRepository.save(testBooking);
    }

    @Transactional
    public TestBooking updateTestBooking(Long id, TestBooking testBookingDetails) {
        Optional<TestBooking> testBookingOptional = testBookingRepository.findById(id);
        
        if (testBookingOptional.isPresent()) {
            TestBooking testBooking = testBookingOptional.get();
            
            // Update fields if they are provided in the request
            if (testBookingDetails.getPatientName() != null) {
                testBooking.setPatientName(testBookingDetails.getPatientName());
            }
            if (testBookingDetails.getDoctorName() != null) {
                testBooking.setDoctorName(testBookingDetails.getDoctorName());
            }
            if (testBookingDetails.getLocation() != null) {
                testBooking.setLocation(testBookingDetails.getLocation());
            }
            if (testBookingDetails.getBookingDate() != null) {
                testBooking.setBookingDate(testBookingDetails.getBookingDate());
            }
            if (testBookingDetails.getStatus() != null) {
                testBooking.setStatus(testBookingDetails.getStatus());
            }
            if (testBookingDetails.getPriority() != null) {
                testBooking.setPriority(testBookingDetails.getPriority());
            }
            if (testBookingDetails.getInsurance() != null) {
                testBooking.setInsurance(testBookingDetails.getInsurance());
            }
            if (testBookingDetails.getTests() != null) {
                testBooking.setTests(testBookingDetails.getTests());
            }
            
            return testBookingRepository.save(testBooking);
        }
        return null;
    }

    public void deleteTestBooking(Long id) {
        testBookingRepository.deleteById(id);
    }

    public List<TestBooking> getTestBookingsByPatientName(String patientName) {
        return testBookingRepository.findByPatientNameContainingIgnoreCase(patientName);
    }

    public List<TestBooking> getTestBookingsByDoctorName(String doctorName) {
        return testBookingRepository.findByDoctorNameContainingIgnoreCase(doctorName);
    }

    public List<TestBooking> getTestBookingsByDateRange(LocalDate startDate, LocalDate endDate) {
        return testBookingRepository.findByBookingDateBetween(startDate, endDate);
    }

    public List<TestBooking> getTestBookingsByStatus(String status) {
        return testBookingRepository.findByStatus(status);
    }

    @Transactional
    public TestBooking updateTestBookingStatus(Long id, String status) {
        Optional<TestBooking> testBookingOptional = testBookingRepository.findById(id);
        
        if (testBookingOptional.isPresent()) {
            TestBooking testBooking = testBookingOptional.get();
            testBooking.setStatus(status);
            return testBookingRepository.save(testBooking);
        }
        return null;
    }

    public List<TestBooking> getTestBookingsByPriority(String priority) {
        return testBookingRepository.findByPriority(priority);
    }

    @Transactional
    public TestBooking addTestsToBooking(Long id, List<Long> testIds) {
        Optional<TestBooking> testBookingOptional = testBookingRepository.findById(id);
        
        if (testBookingOptional.isPresent()) {
            TestBooking testBooking = testBookingOptional.get();
            List<labtest> tests = labTestRepository.findAllById(testIds);
            testBooking.getTests().addAll(tests);
            return testBookingRepository.save(testBooking);
        }
        return null;
    }

    @Transactional
    public TestBooking removeTestsFromBooking(Long id, List<Long> testIds) {
        Optional<TestBooking> testBookingOptional = testBookingRepository.findById(id);
        
        if (testBookingOptional.isPresent()) {
            TestBooking testBooking = testBookingOptional.get();
            testBooking.getTests().removeIf(test -> testIds.contains(test.getId()));
            return testBookingRepository.save(testBooking);
        }
        return null;
    }

    public List<TestBooking> getUpcomingTestBookings() {
        return testBookingRepository.findByBookingDateGreaterThanEqual(LocalDate.now());
    }

    public List<TestBooking> searchTestBookings(String patientName, String doctorName, String status, String priority) {
        if (patientName != null && doctorName != null && status != null && priority != null) {
            return testBookingRepository.findByPatientNameContainingIgnoreCaseAndDoctorNameContainingIgnoreCaseAndStatusAndPriority(
                patientName, doctorName, status, priority);
        } else if (patientName != null && doctorName != null) {
            return testBookingRepository.findByPatientNameContainingIgnoreCaseAndDoctorNameContainingIgnoreCase(
                patientName, doctorName);
        } else if (patientName != null) {
            return testBookingRepository.findByPatientNameContainingIgnoreCase(patientName);
        } else if (doctorName != null) {
            return testBookingRepository.findByDoctorNameContainingIgnoreCase(doctorName);
        } else if (status != null) {
            return testBookingRepository.findByStatus(status);
        } else if (priority != null) {
            return testBookingRepository.findByPriority(priority);
        } else {
            return testBookingRepository.findAll();
        }
    }
}