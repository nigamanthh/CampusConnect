
package com.campusconnect.campusconnect.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campusconnect.campusconnect.model.Event;
import com.campusconnect.campusconnect.repository.EventRepository;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // Get all events
    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepository.getAllEvents();
    }

    // Get event by ID
    @GetMapping("/{id}")
    public Event getEventById(@PathVariable int id) {
        return eventRepository.getEventById(id);
    }

    // Addd an eventt
    @PostMapping
        public String addEvent(@RequestBody Event event) {
        eventRepository.addEvent(event);
        return "Event added successfully";
    }

    // for update
    @PutMapping("/{id}")
    public String updateEvent(@PathVariable int id, @RequestBody Event event) {
        event.setEvent_id(id);
        eventRepository.updateEvent(event);
        return "Event updated successfully";
    }

    // delting an event
    @DeleteMapping("/{id}")
    public String deleteEvent(@PathVariable int id) {
        eventRepository.deleteEvent(id);
        return "Event deleted successfully";
    }
}
