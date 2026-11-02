package com.campusconnect.campusconnect.controller;

import java.util.List;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campusconnect.campusconnect.model.Club;
import com.campusconnect.campusconnect.repository.ClubRepository;
 

@RestController  //handles HTTP requests and returns data.
@RequestMapping ("/api/clubs") 

public class ClubController{
    private final ClubRepository clubRepository;

    public ClubController(ClubRepository clubRepository){
        this.clubRepository=clubRepository;
    }

    @GetMapping()
    public List<Club>getAllClubs(){
        return clubRepository.getAllClubs();
    }
}