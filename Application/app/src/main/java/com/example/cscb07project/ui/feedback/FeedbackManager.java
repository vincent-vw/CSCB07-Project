package com.example.cscb07project.ui.feedback;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FeedbackManager {
    private static FeedbackManager feedbackManager;
    private List<Feedback> feedbackList;
    private Map<String, List<Integer>> eventRatings;

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
        eventRatings = new TreeMap<>();
        FirebaseDatabase.getInstance("https://cscb07project-c6a1c-default-rtdb.firebaseio.com/").getReference("feedback").orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Feedback feedback = Feedback.jsonToClass(dataSnapshot.getValue().toString());
                feedbackList.add(feedback);
                if (!eventRatings.containsKey(feedback.getEvent())) {
                    eventRatings.put(feedback.getEvent(), new ArrayList<>());
                }
                eventRatings.get(feedback.getEvent()).add(feedback.getNumericRating());
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

    public List<String> getAllEventsSummaryAsList() {
        List<String> list = new ArrayList<>();
        for (String event : eventRatings.keySet()) {
            List<Integer> ratings = eventRatings.get(event);
            list.add(event + ": " + ratings.size() + " feedback with an average rating of " + computeRatingAverage(ratings));
        }
        return list;
    }

    private double computeRatingAverage(List<Integer> ratings) {
        double n = 0.0;
        for (int r : ratings) {
            n += r;
        }
        n /= ratings.size();
        return n;
    }
}
