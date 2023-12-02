package com.example.cscb07project.ui.viewfeedback;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cscb07project.ui.Feedback;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FeedbackManager {
    private static FeedbackManager feedbackManager;
    private List<Feedback> feedbackList;

    private FeedbackManager() {
        refreshFeedback();
    }

    public static FeedbackManager getInstance() {
        if (feedbackManager == null) {
            feedbackManager = new FeedbackManager();
        }
        return feedbackManager;
    }

    public void refreshFeedback() {
        feedbackList = new ArrayList<>();
        //TODO need better way of getting database instance
        FirebaseDatabase.getInstance("https://cscb07project-c6a1c-default-rtdb.firebaseio.com/").getReference("feedback").orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                //add complaint to map
                feedbackList.add(Feedback.jsonToClass(dataSnapshot.getValue().toString()));
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

    public List<Feedback> getAllFeedbackSortedBySubmitTime() {
        Collections.sort(feedbackList, new Comparator<Feedback>() {
            @Override
            public int compare(Feedback o1, Feedback o2) {
                if (o1.getTimeSubmitted() > o2.getTimeSubmitted()) {
                    return 1;
                } else if (o1.getTimeSubmitted() == o2.getTimeSubmitted()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        return feedbackList;
    }

    public Feedback getFeedback(int position) {
        return feedbackList.get(position);
    }
}
