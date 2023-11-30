package com.example.cscb07project.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    private Long timeSubmitted;

    public Feedback() {

    }

    public Feedback(String event, String comment, String username, int numericRating, String additionalComments) {
        this.event = event;
        this.comment = comment;
        this.username = username;
        this.numericRating = numericRating;
        this.additionalComments = additionalComments;

        // Format of time
        timeSubmitted = System.currentTimeMillis();
    }

    public static Feedback JsonToFeedback(String json){
        try {
            return new ObjectMapper().readValue(json, Feedback.class);
        } catch (JsonProcessingException e) {
            return new Feedback();
        }
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

    public Long getTimeSubmitted() {
        return timeSubmitted;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return comment;
        }
    }
}


