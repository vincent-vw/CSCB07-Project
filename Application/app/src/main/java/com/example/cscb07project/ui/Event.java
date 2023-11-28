package com.example.cscb07project.ui;

public class Event {
    private String title;
    private String description;
    private Date date;
    private Time time;
    private String participantLimit;

    public Event() {
    }

    public Event(String title, String description, Date date, Time time, String participantLimit) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.participantLimit = participantLimit;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getParticipantLimit() {
        return participantLimit;
    }

    public void setParticipantLimit(String participantLimit) {
        this.participantLimit = participantLimit;
    }
}
