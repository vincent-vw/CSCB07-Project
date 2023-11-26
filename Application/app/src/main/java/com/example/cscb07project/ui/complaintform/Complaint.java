package com.example.cscb07project.ui.complaintform;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Complaint {
    private String username;
    private String status;
    private String complaint;
    private String timeSubmitted;

    public Complaint() {
    }

    public Complaint(String username, String status, String complaint) {
        this.username = username;
        this.status = status;
        this.complaint = complaint;

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

    public String getComplaint() {
        return complaint;
    }

    public String getTimeSubmitted() {
        return timeSubmitted;
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> complaintMap = new HashMap<>();
        complaintMap.put("username", username);
        complaintMap.put("status", status);
        complaintMap.put("text", complaint);
        complaintMap.put("timeSubmitted", timeSubmitted);
        return complaintMap;
    }
}
