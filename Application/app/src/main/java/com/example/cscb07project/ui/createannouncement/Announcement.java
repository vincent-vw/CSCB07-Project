package com.example.cscb07project.ui.createannouncement;

public class Announcement {

    // Firebase requires fields to be public
    public String username;
    public String announcement;

    public Announcement(){}

    public Announcement(String username, String announcement){
        this.username = username;
        this.announcement = announcement;
    }

}
