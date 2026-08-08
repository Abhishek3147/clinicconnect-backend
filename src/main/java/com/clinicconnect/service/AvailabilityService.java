package com.clinicconnect.service;

import com.clinicconnect.dto.AvailabilityRequest;
import com.clinicconnect.entity.Availability;
import com.clinicconnect.repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    // Save or Update Today's Availability
    public Availability saveAvailability(AvailabilityRequest request) {

        Optional<Availability> existing =
                availabilityRepository.findByDate(request.getDate());

        Availability availability;

        if (existing.isPresent()) {
            availability = existing.get();
        } else {
            availability = new Availability();
        }

        availability.setDate(request.getDate());
        availability.setStartTime(request.getStartTime());
        availability.setEndTime(request.getEndTime());
        availability.setStatus(request.getStatus());
        availability.setNotice(request.getNotice());

        return availabilityRepository.save(availability);
    }

    // Get Today's Availability
    public Availability getTodayAvailability() {

        return availabilityRepository
                .findByDate(LocalDate.now())
                .orElse(null);
    }
}