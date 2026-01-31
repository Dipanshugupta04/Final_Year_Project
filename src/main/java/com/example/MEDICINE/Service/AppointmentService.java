package com.example.MEDICINE.Service;

import com.example.MEDICINE.Model.Appointment;
import com.example.MEDICINE.Model.Appointment.Status;
import com.example.MEDICINE.Repository.AppointmentRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepo appointmentRepository;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public Optional<Appointment> updateAppointment(String id, Appointment updatedAppointment) {
        return appointmentRepository.findByAppointmentID(id)
                .map(existingAppointment -> {
                    updatedAppointment.setAppointmentID(id);
                    return appointmentRepository.save(updatedAppointment);
                });
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }


    public Optional<Appointment> updateAppointmentFields(String id, Map<String, Object> updates) {
    return appointmentRepository.findByAppointmentID(id)
            .map(appointment -> {
                updates.forEach((key, value) -> {
                    switch (key) {
                        case "appointmentStatus" -> {
                            try {
                                Status status = Status.valueOf(value.toString().toUpperCase());
                                appointment.setAppointmentStatus(status);
                            } catch (IllegalArgumentException e) {
                                throw new RuntimeException("Invalid status value: " + value);
                            }
                        }
                        case "appointmentDate" -> appointment.setAppointmentDate(LocalDate.parse(value.toString()));
                        case "appointmentTime" -> appointment.setAppointmentTime(LocalTime.parse(value.toString()));
                        case "location" -> appointment.setLocation(value.toString());
                        case "updatedAt" -> appointment.setUpdatedAt(
        OffsetDateTime.parse(value.toString()).toLocalDateTime()
    );
                        // Add more fields if needed
                    }
                });
                return appointmentRepository.save(appointment);
            });
}

}