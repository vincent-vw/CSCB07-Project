package com.example.cscb07project.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Complaint {
    private String username;
    private String status;
    private String text;
    private String timeSubmitted; // Modified to store formatted time

    public Complaint() {

    }

    public Complaint(String username, String status, String text) {
        this.username = username;
        this.status = status;
        this.text = text;

        // Format the timestamp in the desired format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMMM-dd-HH:mm", Locale.getDefault());
        this.timeSubmitted = dateFormat.format(new Date());
    }

    public String getUsername() {
        return username;
    }

    public String getStatus() {
        return status;
    }

    public String getText() {
        return text;
    }

    public String getTimeSubmitted() {
        return timeSubmitted;
    }

    // Method to convert Complaint object to HashMap
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> complaintMap = new HashMap<>();
        complaintMap.put("username", username);
        complaintMap.put("status", status);
        complaintMap.put("text", text);
        complaintMap.put("timeSubmitted", timeSubmitted);
        return complaintMap;
    }
}
