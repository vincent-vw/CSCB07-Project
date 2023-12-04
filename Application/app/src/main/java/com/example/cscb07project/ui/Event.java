package com.example.cscb07project.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

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

    public Event(String title, String description, Date date, Time time, int participantLimit) throws ParseException {
        this.title = title;
        this.description = description;
        this.participantLimit = participantLimit;

        // Convert date and time to UNIX timestamp
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
        java.util.Date d = format.parse(date.getYear() + "/" + date.getMonth() + "/" +
                date.getDay() + " " + time.getHour() + ":" + time.getMinute());
        assert d != null;
        this.scheduledTime = d.getTime();
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

    public Calendar getScheduledTimeConverted() {
        java.util.Date date = new java.util.Date(scheduledTime);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("EST"));
        cal.setTime(date);
        return cal;
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
