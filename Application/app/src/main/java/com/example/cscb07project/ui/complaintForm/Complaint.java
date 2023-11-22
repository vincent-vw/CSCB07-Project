package com.example.cscb07project.ui.complaintForm;

public class Complaint {
    private String text;
    private String username;

    public Complaint() {
    }

    public Complaint(String text, String username) {
        this.text = text;
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public String getUsername(){
        return username;
    }
}
