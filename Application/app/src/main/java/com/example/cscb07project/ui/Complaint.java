package com.example.cscb07project.ui;

public class Complaint {
    private String username;
    private String complaint;

    public Complaint() {
    }

    public Complaint(String username, String complaint) {
        this.username = username;
        this.complaint = complaint;
    }

    public String getText() {
        return complaint;
    }

    public String getUsername(){
        return username;
    }
}
