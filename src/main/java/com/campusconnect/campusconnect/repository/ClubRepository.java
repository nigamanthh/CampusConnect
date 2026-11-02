package com.campusconnect.campusconnect.repository;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.campusconnect.campusconnect.model.Club;

@Repository //db operations
public class ClubRepository {

    private final JdbcTemplate jdbcTemplate; 

    public ClubRepository(JdbcTemplate jdbcTemplate) { //dependency injection(constructor)
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Club> getAllClubs() {

        String sql = "SELECT * FROM clubs";

        return jdbcTemplate.query(sql, (rs, rowNum) -> { //lambda method , rs-:current row, rownum-rows pos
            return new Club( 
                rs.getInt("club_id"), //new obj created
                rs.getString("club_name"),
                rs.getString("description_of_club"),
                rs.getString("contact_email")
            );
        });
    }
}