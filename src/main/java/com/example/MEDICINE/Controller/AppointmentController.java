package com.example.MEDICINE.Controller;

import com.example.MEDICINE.Model.Appointment;
import com.example.MEDICINE.Repository.AppointmentRepo;
import com.example.MEDICINE.Service.AppointmentService;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = {"http://127.0.0.1:5502", "http://localhost:5502"})// Allow React frontend to access
public class AppointmentController {

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private AppointmentService appointmentService;

       private final AtomicLong counter = new AtomicLong(1000);

    // Get all appointments
  

    // Create a new appointment
    @PostConstruct
    public void initCounter() {
        int maxId = appointmentRepo.findMaxAppointmentIdNumber().orElse(1000);
        counter.set(maxId);
    }

    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        String appointmentID = "APT-" + counter.incrementAndGet();
        // appointment.setDoctorId(appointm);
        appointment.setAppointmentID(appointmentID);
        return appointmentService.saveAppointment(appointment);
    }
    // Get appointment by ID

    @GetMapping
public ResponseEntity<List<Appointment>> getAllappoint() {
    List<Appointment> appointments = appointmentRepo.findAll();
    return ResponseEntity.ok(appointments);
}

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update appointment
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable String id, @RequestBody Appointment appointment) {
        return appointmentService.updateAppointment(id, appointment)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete appointment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
public ResponseEntity<Appointment> updatePartialAppointment(
        @PathVariable String id,
        @RequestBody Map<String, Object> updates) {
    return appointmentService.updateAppointmentFields(id, updates)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}

}