package com.example.MEDICINE.Controller;

import com.example.MEDICINE.DTO.TestBookingRequestDTO;
import com.example.MEDICINE.Model.TestBooking;
import com.example.MEDICINE.Service.TestBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/test-bookings")
@CrossOrigin(origins = "http://localhost:3000")
public class TestBookingController {

    @Autowired
    private TestBookingService testBookingService;

    // Get all test bookings
    @GetMapping
    public ResponseEntity<List<TestBooking>> getAllTestBookings() {
        try {
            List<TestBooking> testBookings = testBookingService.getAllTestBookings();
            if (testBookings.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(testBookings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get test booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<TestBooking> getTestBookingById(@PathVariable("id") Long id) {
        Optional<TestBooking> testBookingData = testBookingService.getTestBookingById(id);
        
        if (testBookingData.isPresent()) {
            return new ResponseEntity<>(testBookingData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Create a new test booking (using DTO to avoid deserialization issues)
    @PostMapping
    public ResponseEntity<TestBooking> createTestBooking(@RequestBody TestBookingRequestDTO bookingRequest) {
        
        try {
            TestBooking newTestBooking = testBookingService.createTestBooking(bookingRequest);
            return new ResponseEntity<>(newTestBooking, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update an existing test booking
    @PutMapping("/{id}")
    public ResponseEntity<TestBooking> updateTestBooking(@PathVariable("id") Long id, @RequestBody TestBooking testBooking) {
        try {
            TestBooking updatedTestBooking = testBookingService.updateTestBooking(id, testBooking);
            if (updatedTestBooking != null) {
                return new ResponseEntity<>(updatedTestBooking, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a test booking
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTestBooking(@PathVariable("id") Long id) {
        try {
            testBookingService.deleteTestBooking(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get test bookings by patient name
    @GetMapping("/patient/{patientName}")
    public ResponseEntity<List<TestBooking>> getTestBookingsByPatientName(@PathVariable("patientName") String patientName) {
        try {
            List<TestBooking> testBookings = testBookingService.getTestBookingsByPatientName(patientName);
            if (testBookings.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(testBookings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get test bookings by doctor name
    @GetMapping("/doctor/{doctorName}")
    public ResponseEntity<List<TestBooking>> getTestBookingsByDoctorName(@PathVariable("doctorName") String doctorName) {
        try {
            List<TestBooking> testBookings = testBookingService.getTestBookingsByDoctorName(doctorName);
            if (testBookings.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(testBookings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get test bookings by date range
    @GetMapping("/date-range")
    public ResponseEntity<List<TestBooking>> getTestBookingsByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            List<TestBooking> testBookings = testBookingService.getTestBookingsByDateRange(startDate, endDate);
            if (testBookings.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(testBookings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get test bookings by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TestBooking>> getTestBookingsByStatus(@PathVariable("status") String status) {
        try {
            List<TestBooking> testBookings = testBookingService.getTestBookingsByStatus(status);
            if (testBookings.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(testBookings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update test booking status
    @PatchMapping("/{id}/status")
    public ResponseEntity<TestBooking> updateTestBookingStatus(@PathVariable("id") Long id, @RequestParam("status") String status) {
        try {
            TestBooking updatedTestBooking = testBookingService.updateTestBookingStatus(id, status);
            if (updatedTestBooking != null) {
                return new ResponseEntity<>(updatedTestBooking, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get test bookings by priority
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TestBooking>> getTestBookingsByPriority(@PathVariable("priority") String priority) {
        try {
            List<TestBooking> testBookings = testBookingService.getTestBookingsByPriority(priority);
            if (testBookings.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(testBookings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Add tests to an existing booking
    @PostMapping("/{id}/tests")
    public ResponseEntity<TestBooking> addTestsToBooking(@PathVariable("id") Long id, @RequestBody List<Long> testIds) {
        try {
            TestBooking updatedTestBooking = testBookingService.addTestsToBooking(id, testIds);
            if (updatedTestBooking != null) {
                return new ResponseEntity<>(updatedTestBooking, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Remove tests from an existing booking
    @DeleteMapping("/{id}/tests")
    public ResponseEntity<TestBooking> removeTestsFromBooking(@PathVariable("id") Long id, @RequestBody List<Long> testIds) {
        try {
            TestBooking updatedTestBooking = testBookingService.removeTestsFromBooking(id, testIds);
            if (updatedTestBooking != null) {
                return new ResponseEntity<>(updatedTestBooking, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get upcoming test bookings (from today onwards)
    @GetMapping("/upcoming")
    public ResponseEntity<List<TestBooking>> getUpcomingTestBookings() {
        try {
            List<TestBooking> testBookings = testBookingService.getUpcomingTestBookings();
            if (testBookings.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(testBookings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Search test bookings by multiple criteria
    @GetMapping("/search")
    public ResponseEntity<List<TestBooking>> searchTestBookings(
            @RequestParam(value = "patientName", required = false) String patientName,
            @RequestParam(value = "doctorName", required = false) String doctorName,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "priority", required = false) String priority) {
        try {
            List<TestBooking> testBookings = testBookingService.searchTestBookings(patientName, doctorName, status, priority);
            if (testBookings.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(testBookings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}