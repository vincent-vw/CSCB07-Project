package com.example.cscb07project.ui.event;

import android.icu.text.DateFormat;

import androidx.annotation.NonNull;

import com.example.cscb07project.ui.Date;
import com.example.cscb07project.ui.Time;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Event {
    private String title;
    private String description;
    private Long scheduledTime;
    private int participantLimit;
    private int currentParticipantCount;
    private ArrayList<String> participants;

    public Event() {
    }

    public Event(String title, String description, Long scheduledTime, int participantLimit) {
        this.title = title;
        this.description = description;
        this.scheduledTime = scheduledTime;
        this.participantLimit = participantLimit;
        participants = new ArrayList<>();
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
        participants = new ArrayList<>();
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

    public List<String> getParticipants() {
        return participants;
    }

    public void addParticipant(String username) {
        if (participants == null) {
            participants = new ArrayList<>();
        }
        currentParticipantCount++;
        participants.add(username);
    }

    public Calendar convertScheduledTime() {
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

    public int getCurrentParticipantCount() {
        return currentParticipantCount;
    }

    public void setCurrentParticipantCount(int currentParticipantCount) {
        this.currentParticipantCount = currentParticipantCount;
    }

    public String previewEventAsString() {
        return title + " at " + DateFormat.getInstance().format(scheduledTime);
    }

    public String viewEventAsString() {
        return title + "\nDescription: " + description + "\nTime: " + DateFormat.getInstance().format(scheduledTime) + "\nNumber of participants: " + currentParticipantCount + "/" + participantLimit;
    }

    public boolean maxParticipantsReached() {
        return currentParticipantCount >= participantLimit;
    }

    public boolean isUserEnrolled(String username) {
        return participants.contains(username);
    }
}
