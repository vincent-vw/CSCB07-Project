package com.example.cscb07project.ui;

import android.icu.text.DateFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Feedback {
    private String event;
    private String comment;
    private String username;
    private int numericRating;
    private String additionalComments;
    private Long timeSubmitted;

    private boolean isAdminViewed;

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

    public static Feedback jsonToClass(String json) {
        try {
            return new ObjectMapper().readValue(json, Feedback.class);
        } catch (JsonProcessingException e) {
            return new Feedback();
        }
    }

    public String classToJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return comment;
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

    public boolean getIsAdminViewed() {
        return isAdminViewed;
    }

    public String viewFeedbackAsString() {
        return "Username: " + username + "\nEvent: " + event + "\nRating: " + numericRating + "\nComment: " + comment + "\nAdditional Comment: " + additionalComments + "\nTime Submitted: " + DateFormat.getInstance().format(timeSubmitted) + "\nAdmin View Status:" + isAdminViewed;
    }

    public String previewFeedbackAsString() {
        if (isAdminViewed) {
            return "Admin Reviewed, from username: " + username + ", event: " + event;
        } else {
            return "Admin Unreviewed, from username: " + username + ", event: " + event;
        }
    }
}


