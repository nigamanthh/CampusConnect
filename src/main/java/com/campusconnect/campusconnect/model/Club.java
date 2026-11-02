package com.campusconnect.campusconnect.model;

public class Club {
    private int club_id;
    private String club_name;
    private String description;
    private String email;

    public Club (int club_id,
     String club_name,
     String description,
     String email) {
        this.club_id=club_id;
        this.club_name=club_name;
        this.description=description;
        this.email=email;
     }

    public int getClubId() {
        return club_id;
    }

    public String getClubName() {
        return club_name;
    }

    public String getDescription() {
        return description;
    }

    public String getContactEmail() {
        return email;
    }
}
