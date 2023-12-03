package com.example.cscb07project.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Event {
    private String title;
    private String description;
    private Long scheduledTime;
    private int participantLimit;
    private int currentParticipants;
  
    public Event() {}

    public Event(String title, String description, Long scheduledTime, int participantLimit) {
        this.title = title;
        this.description = description;
        this.scheduledTime = scheduledTime;
        this.participantLimit = participantLimit;
    }

    public static Event jsonToClass(String json) {
        try {
            return new ObjectMapper().readValue(json, Event.class);
        } catch (JsonProcessingException e) {
            return new Event();
        }
    }

    public String classToJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getScheduledTime() {
        return scheduledTime;
    }

    public int getParticipantLimit() {
        return participantLimit;
    }

    public void setParticipantLimit(int participantLimit) {
        this.participantLimit = participantLimit;
    }

    public int getCurrentParticipants() {
        return currentParticipants;
    }

    public void setCurrentParticipants(int currentParticipants) {
        this.currentParticipants = currentParticipants;
    }
}
