package com.example.cscb07project.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Feedback {
    private String event;
    private String comment;
    private String username;
    private int numericRating;
    private String additionalComments;
    private String timeSubmitted;

    public Feedback() {
    }

    public Feedback(String event, String comment, String username, int numericRating, String additionalComments) {
        this.event = event;
        this.comment = comment;
        this.username = username;
        this.numericRating = numericRating;
        this.additionalComments = additionalComments;

        // Format of time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMMM-dd-HH:mm", Locale.getDefault());
        this.timeSubmitted = dateFormat.format(new Date());
    }

    public String getEvent() {
        return event;
    }

    public String getComment() {
        return comment;
    }

    public String getUsername() {
        return username;
    }

    public int getNumericRating() {
        return numericRating;
    }

    public String getAdditionalComments() {
        return additionalComments;
    }

    public String getTimeSubmitted() {
        return timeSubmitted;
    }

    // Method to convert Feedback object to HashMap
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> feedbackMap = new HashMap<>();
        feedbackMap.put("event", event);
        feedbackMap.put("comment", comment);
        feedbackMap.put("username", username);
        feedbackMap.put("numericRating", numericRating);
        feedbackMap.put("additionalComments", additionalComments);
        feedbackMap.put("timeSubmitted", timeSubmitted);
        return feedbackMap;
    }
}


