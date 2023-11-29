package com.example.cscb07project.ui.viewcomplaints;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cscb07project.ui.Complaint;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComplaintManager {
    private static ComplaintManager complaintManager;
    private List<Complaint> complaintsList;

    private ComplaintManager() {
        refreshComplaints();
    }

    public static ComplaintManager getInstance() {
        if (complaintManager == null) {
            complaintManager = new ComplaintManager();
        }
        return complaintManager;
    }

    public void refreshComplaints() {
        complaintsList = new ArrayList<>();
        //TODO need better way of getting database instance
        FirebaseDatabase.getInstance("https://cscb07project-c6a1c-default-rtdb.firebaseio.com/").getReference("complaints").orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                //add complaint to map
                complaintsList.add(Complaint.JsonToComplaint(dataSnapshot.getValue().toString()));
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

    public List<Complaint> getAllComplaintsSortedBySubmitTime() {
        Collections.sort(complaintsList, new Comparator<Complaint>() {
            @Override
            public int compare(Complaint o1, Complaint o2) {
                if (o1.getTimeSubmitted() > o2.getTimeSubmitted()) {
                    return 1;
                } else if (o1.getTimeSubmitted() == o2.getTimeSubmitted()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        return complaintsList;
    }

    public Complaint getComplaint(int position) {
        return complaintsList.get(position);
    }
}
