package com.example.cscb07project.ui.complaint;

import android.icu.text.DateFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Complaint {
    private String username;
    private String status;
    private String complaint;
    private long timeSubmitted;
    private boolean isAdminViewed;

    public Complaint() {
    }

    public Complaint(String username, String status, String complaint) {
        this.username = username;
        this.status = status;
        this.complaint = complaint;

        timeSubmitted = System.currentTimeMillis();
        isAdminViewed = false;
    }

    public static Complaint jsonToClass(String json) {
        try {
            return new ObjectMapper().readValue(json, Complaint.class);
        } catch (JsonProcessingException e) {
            return new Complaint();
        }
    }

    public String classToJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return complaint;
        }
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

    public long getTimeSubmitted() {
        return timeSubmitted;
    }

    public boolean getIsAdminViewed() {
        return isAdminViewed;
    }

    public void setAdminViewed() {
        isAdminViewed = true;
    }

    public String viewComplaintAsString() {
        return "Username: " + username + "\nStatus: " + status + "\nComplaint: " + complaint + "\nTime Submitted: " + DateFormat.getInstance().format(timeSubmitted) + "\nAdmin View Status: " + isAdminViewed;
    }

    public String previewComplaintAsString() {
        if (isAdminViewed) {
            return "Admin reviewed; from: " + username;
        } else {
            return "Admin unreviewed; from: " + username;
        }
    }
}
