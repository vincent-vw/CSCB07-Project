package com.example.cscb07project.ui.events;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cscb07project.ui.Complaint;
import com.example.cscb07project.ui.Event;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EventManager {
    private static EventManager eventManager;
    private List<Event> eventsList;

    private EventManager() {
        refreshEvents();
    }

    public static EventManager getInstance() {
        if (eventManager == null) {
            eventManager = new EventManager();
        }
        return eventManager;
    }

    public void refreshEvents() {
        eventsList = new ArrayList<>();
        //TODO need better way of getting database instance
        FirebaseDatabase.getInstance("https://cscb07project-c6a1c-default-rtdb.firebaseio.com/").getReference("events").orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                //add complaint to map
                eventsList.add(Event.jsonToClass(dataSnapshot.getValue().toString()));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public List<Event> getAllComplaintsSortedBySubmitTime() {
        Collections.sort(eventsList, new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                if (o1.getScheduledTime() > o2.getScheduledTime()) {
                    return 1;
                } else if (o1.getScheduledTime() == o2.getScheduledTime()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        return eventsList;
    }

    //TODO check event exists functino, list add the scheduled events function
}
