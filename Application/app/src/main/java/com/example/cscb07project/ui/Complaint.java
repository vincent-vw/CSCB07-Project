package com.example.cscb07project.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Complaint {
    private String username;
    private String status;
    private String complaint;
    private long timeSubmitted;
    private boolean isAdminViewed;

    public Complaint(){

    }
    public Complaint(String username, String status, String complaint) {
        this.username = username;
        this.status = status;
        this.complaint = complaint;

        timeSubmitted = System.currentTimeMillis();
        isAdminViewed = false;
    }
    //TODO try catch
    public static Complaint JsonToComplaint(String json){
        try {
            return new ObjectMapper().readValue(json, Complaint.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
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

    public boolean getIsAdminViewed(){
        return isAdminViewed;
    }

    //TODO use to string to show a complaint text?
    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
