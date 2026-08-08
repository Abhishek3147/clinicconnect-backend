package com.clinicconnect.controller;

import com.clinicconnect.dto.AvailabilityRequest;
import com.clinicconnect.entity.Availability;
import com.clinicconnect.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    // Doctor saves today's availability
    @PostMapping
    public Availability saveAvailability(@RequestBody AvailabilityRequest request) {
        return availabilityService.saveAvailability(request);
    }

    // Public API - anyone can check today's availability
    @GetMapping("/today")
    public Availability getTodayAvailability() {
        return availabilityService.getTodayAvailability();
    }
}