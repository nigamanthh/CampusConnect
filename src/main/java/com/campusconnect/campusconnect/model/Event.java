
package com.campusconnect.campusconnect.model;

import java.time.LocalDateTime;

public class Event {

    private int event_id;
    private int club_id;
    private String event_name;
    private String title;
    private String description_of_event;
    private String category;
    private LocalDateTime event_date;
    private String venue;
    private int capacity;
    private String status_of_event;

    // Default constructor
    public Event() {
    }

    // Parameterized constructor
    public Event(int event_id, int club_id, String event_name,
            String title, String description_of_event, String category,
            LocalDateTime event_date, String venue, int capacity,
            String status_of_event) {

        this.event_id = event_id;
        this.club_id = club_id;
        this.event_name = event_name;
        this.title = title;
        this.description_of_event = description_of_event;
        this.category = category;
        this.event_date = event_date;
        this.venue = venue;
        this.capacity = capacity;
        this.status_of_event = status_of_event;
    }

    // Getters

    public int getEvent_id() {
        return event_id;
    }

    public int getClub_id() {
        return club_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription_of_event() {
        return description_of_event;
    }

    public String getCategory() {
        return category;
    }

    public LocalDateTime getEvent_date() {
        return event_date;
    }

    public String getVenue() {
        return venue;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getStatus_of_event() {
        return status_of_event;
    }

    // Setters

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public void setClub_id(int club_id) {
        this.club_id = club_id;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription_of_event(String description_of_event) {
        this.description_of_event = description_of_event;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setEvent_date(LocalDateTime event_date) {
        this.event_date = event_date;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setStatus_of_event(String status_of_event) {
        this.status_of_event = status_of_event;
    }
}
    