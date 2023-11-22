package com.example.cscb07project.ui;

public class Announcement {

    // Firebase requires fields to be public
    private String username;
    private String announcement;

    public Announcement(){}

    public Announcement(String username, String announcement){
        this.username = username;
        this.announcement = announcement;
    }

    public String getUsername() {
        return username;
    }

    public String getAnnouncement() {
        return announcement;
    }
}
