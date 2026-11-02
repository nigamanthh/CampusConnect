
package com.campusconnect.campusconnect.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campusconnect.campusconnect.model.Event;
import com.campusconnect.campusconnect.model.Registration;
import com.campusconnect.campusconnect.repository.RegistrationRepository;
import com.campusconnect.campusconnect.repository.EventRepository;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    private final RegistrationRepository registrationRepository;
    private final EventRepository eventRepository;

    public RegistrationController(
            RegistrationRepository registrationRepository,
            EventRepository eventRepository) {

        this.registrationRepository = registrationRepository;
        this.eventRepository = eventRepository;
    }

    // Get all registrations
    @GetMapping
    public List<Registration> getAllRegistrations() {
        return registrationRepository.getAllRegistrations();
    }

    // Get participants of an event
    @GetMapping("/event/{event_id}")
    public List<Registration> getRegistrationsByEventId(
            @PathVariable int event_id) {

        return registrationRepository.getRegistrationsByEventId(event_id);
    }

    // Register a student
    @PostMapping
    public ResponseEntity<String> addRegistration(
            @RequestBody Registration registration) {

        int event_id = registration.getEvent_id();

        // Check if event exists
        Event event;

        try {
            event = eventRepository.getEventById(event_id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Event does not exist");
        }

        // Check student details
        if (registration.getStudent_name() == null ||
                registration.getStudent_email() == null ||
                registration.getStudent_name().isBlank() ||
                registration.getStudent_email().isBlank()) {

            return ResponseEntity.badRequest()
                    .body("Student name and email are required");
        }

        // Check duplicate registration
        if (registrationRepository.isAlreadyRegistered(
                event_id, registration.getStudent_email())) {

            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Student already registered for this event");
        }

        // Check event capacity
        int count = registrationRepository.getRegistrationCount(event_id);

        if (count >= event.getCapacity()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Event is full");
        }

        // Set default status
        registration.setStatus("REGISTERED");

        registrationRepository.addRegistration(registration);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Registration successful");
    }

    // Cancel registration
    @PutMapping("/cancel/{registration_id}")
    public ResponseEntity<String> cancelRegistration(
            @PathVariable int registration_id) {

        int result = registrationRepository.cancelRegistration(registration_id);

        if (result == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Registration does not exist");
        }

        return ResponseEntity.ok("Registration cancelled successfully");
    }
}
