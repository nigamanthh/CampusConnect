
package com.campusconnect.campusconnect.model;

import java.time.LocalDateTime;

public class Registration {

    private int registration_id;
    private int event_id;
    private String student_name;
    private String student_email;
    private LocalDateTime registration_date;
    private String status;

    public Registration() {
    }

    public Registration(int registration_id, int event_id,
            String student_name, String student_email,
            LocalDateTime registration_date, String status) {

        this.registration_id = registration_id;
        this.event_id = event_id;
        this.student_name = student_name;
        this.student_email = student_email;
        this.registration_date = registration_date;
        this.status = status;
    }

    public int getRegistration_id() {
        return registration_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public String getStudent_email() {
        return student_email;
    }

    public LocalDateTime getRegistration_date() {
        return registration_date;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setRegistration_id(int registration_id) {
        this.registration_id = registration_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public void setStudent_email(String student_email) {
        this.student_email = student_email;
    }

    public void setRegistration_date(LocalDateTime registration_date) {
        this.registration_date = registration_date;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
