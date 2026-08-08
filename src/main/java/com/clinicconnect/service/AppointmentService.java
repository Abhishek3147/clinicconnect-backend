package com.clinicconnect.service;

import com.clinicconnect.entity.Appointment;
import com.clinicconnect.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Integer id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    public Appointment updateAppointment(Integer id, Appointment appointment) {

        Appointment existingAppointment =
                appointmentRepository.findById(id).orElse(null);

        if (existingAppointment != null) {

            existingAppointment.setDoctorId(appointment.getDoctorId());
            existingAppointment.setPatientId(appointment.getPatientId());
            existingAppointment.setAppointmentDate(appointment.getAppointmentDate());
            existingAppointment.setAppointmentTime(appointment.getAppointmentTime());
            existingAppointment.setStatus(appointment.getStatus());

            return appointmentRepository.save(existingAppointment);
        }

        return null;
    }

    public void deleteAppointment(Integer id) {
        appointmentRepository.deleteById(id);
    }
}