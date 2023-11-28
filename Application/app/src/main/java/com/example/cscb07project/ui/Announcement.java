package com.example.cscb07project.ui;

public class Announcement {

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
