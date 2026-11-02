
package com.campusconnect.campusconnect.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.campusconnect.campusconnect.model.Registration;

@Repository
public class RegistrationRepository {

    private final JdbcTemplate jdbcTemplate;

    public RegistrationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Get all registrations
    public List<Registration> getAllRegistrations() {

        String sql = "SELECT * FROM registrations";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            return new Registration(
                rs.getInt("registration_id"),
                rs.getInt("event_id"),
                rs.getString("student_name"),
                rs.getString("student_email"),
                rs.getTimestamp("registration_date").toLocalDateTime(),
                rs.getString("status")
            );

        });
    }

    // Get registrations for a particular event
    public List<Registration> getRegistrationsByEventId(int event_id) {

        String sql = "SELECT * FROM registrations WHERE event_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            return new Registration(
                rs.getInt("registration_id"),
                rs.getInt("event_id"),
                rs.getString("student_name"),
                rs.getString("student_email"),
                rs.getTimestamp("registration_date").toLocalDateTime(),
                rs.getString("status")
            );

        }, event_id);
    }

    // Add a registration
    public int addRegistration(Registration registration) {

        String sql = "INSERT INTO registrations (registration_id, event_id, student_name, student_email, status) VALUES (?, ?, ?, ?, ?)";

        return jdbcTemplate.update(
            sql,
            registration.getRegistration_id(),
            registration.getEvent_id(),
            registration.getStudent_name(),
            registration.getStudent_email(),
            registration.getStatus()
        );
    }

    // Cancel a registration
    public int cancelRegistration(int registration_id) {

       String sql = "UPDATE registrations SET status = 'CANCELLED' WHERE registration_id = ? AND status = 'REGISTERED'";
        return jdbcTemplate.update(sql, registration_id);
    }

    
    // Check whether a student has already registered
    public boolean isAlreadyRegistered(int event_id, String student_email) {

        String sql = "SELECT COUNT(*) FROM registrations WHERE event_id = ? AND student_email = ? AND status = 'REGISTERED'";

        Integer count = jdbcTemplate.queryForObject(
            sql,
            Integer.class,
            event_id,
            student_email
        );

        return count != null && count > 0;
    }

    // Count active registrations for an event
    public int getRegistrationCount(int event_id) {

        String sql = "SELECT COUNT(*) FROM registrations WHERE event_id = ? AND status = 'REGISTERED'";

        return jdbcTemplate.queryForObject(
            sql,
            Integer.class,
            event_id
        );
    }

}
