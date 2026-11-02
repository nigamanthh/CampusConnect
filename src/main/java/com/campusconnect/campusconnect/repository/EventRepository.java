
package com.campusconnect.campusconnect.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.campusconnect.campusconnect.model.Event;

@Repository
public class EventRepository {

    private final JdbcTemplate jdbcTemplate;

    public EventRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Get all events
    public List<Event> getAllEvents() {

        String sql = "SELECT * FROM events";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            return new Event(
                rs.getInt("event_id"),
                rs.getInt("club_id"),
                rs.getString("event_name"),
                rs.getString("title"),
                rs.getString("description_of_event"),
                rs.getString("category"),
                rs.getTimestamp("event_date").toLocalDateTime(),
                rs.getString("venue"),
                rs.getInt("capacity"),
                rs.getString("status_of_event")
            );

        });
    }

    // Get event by ID
    public Event getEventById(int id) {

        String sql = "SELECT * FROM events WHERE event_id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {

            return new Event(
                rs.getInt("event_id"),
                rs.getInt("club_id"),
                rs.getString("event_name"),
                rs.getString("title"),
                rs.getString("description_of_event"),
                rs.getString("category"),
                rs.getTimestamp("event_date").toLocalDateTime(),
                rs.getString("venue"),
                rs.getInt("capacity"),
                rs.getString("status_of_event")
            );

        }, id);
    }

    // Add new event
    public int addEvent(Event event) {

        String sql = "INSERT INTO events VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(
            sql,
            event.getEvent_id(),
            event.getClub_id(),
            event.getEvent_name(),
            event.getTitle(),
            event.getDescription_of_event(),
            event.getCategory(),
            java.sql.Timestamp.valueOf(event.getEvent_date()),
            event.getVenue(),
            event.getCapacity(),
            event.getStatus_of_event()
        );
    }

    // Update event
    public int updateEvent(Event event) {

        String sql = "UPDATE events SET club_id=?, event_name=?, title=?, description_of_event=?, category=?, event_date=?, venue=?, capacity=?, status_of_event=? WHERE event_id=?";

        return jdbcTemplate.update(
            sql,
            event.getClub_id(),
            event.getEvent_name(),
            event.getTitle(),
            event.getDescription_of_event(),
            event.getCategory(),
            java.sql.Timestamp.valueOf(event.getEvent_date()),
            event.getVenue(),
            event.getCapacity(),
            event.getStatus_of_event(),
            event.getEvent_id()
        );
    }

    // Delete event
    public int deleteEvent(int id) {

        String sql = "DELETE FROM events WHERE event_id = ?";

        return jdbcTemplate.update(sql, id);
    }
}
