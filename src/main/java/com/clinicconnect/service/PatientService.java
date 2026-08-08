package com.clinicconnect.service;

import com.clinicconnect.entity.Patient;
import com.clinicconnect.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Integer id) {
        return patientRepository.findById(id).orElse(null);
    }

    public Patient updatePatient(Integer id, Patient patient) {
        Patient existingPatient = patientRepository.findById(id).orElse(null);

        if (existingPatient != null) {
    existingPatient.setUserId(patient.getUserId());
    existingPatient.setFullName(patient.getFullName());
    existingPatient.setAge(patient.getAge());
    existingPatient.setGender(patient.getGender());
    existingPatient.setPhone(patient.getPhone());
    existingPatient.setEmail(patient.getEmail());
    existingPatient.setAddress(patient.getAddress());

    return patientRepository.save(existingPatient);
}

        return null;
    }

    public void deletePatient(Integer id) {
        patientRepository.deleteById(id);
    }
}