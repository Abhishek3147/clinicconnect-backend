package com.clinicconnect.service;

import com.clinicconnect.entity.Doctor;
import com.clinicconnect.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Integer id) {
        return doctorRepository.findById(id).orElse(null);
    }

    public Doctor updateDoctor(Integer id, Doctor doctor) {
        Doctor existingDoctor = doctorRepository.findById(id).orElse(null);

        if (existingDoctor != null) {
            existingDoctor.setUserId(doctor.getUserId());
            existingDoctor.setSpecialization(doctor.getSpecialization());
            existingDoctor.setExperience(doctor.getExperience());
            existingDoctor.setPhone(doctor.getPhone());

            return doctorRepository.save(existingDoctor);
        }

        return null;
    }

    public void deleteDoctor(Integer id) {
        doctorRepository.deleteById(id);
    }
}