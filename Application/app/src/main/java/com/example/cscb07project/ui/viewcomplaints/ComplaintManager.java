package com.example.cscb07project.ui.viewcomplaints;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cscb07project.ui.Complaint;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;
import java.util.TreeMap;

public class ComplaintManager {
    private static ComplaintManager complaintManager;
    private Map<Integer, Complaint> complaintsMap;
    private ComplaintManager(){
        refreshComplaints();
    }

    public static ComplaintManager getInstance(){
        if(complaintManager == null){
            complaintManager = new ComplaintManager();
        }
        return complaintManager;
    }

    public void refreshComplaints(){
        complaintsMap = new TreeMap<>();
        //TODO need better way of getting database instance
        FirebaseDatabase.getInstance("https://cscb07project-c6a1c-default-rtdb.firebaseio.com/").getReference("complaints").orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                //add complaint to map
                complaintsMap.put(Integer.parseInt(dataSnapshot.getKey()), new Complaint(dataSnapshot.getValue().toString()));
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

    public Map<Integer, Complaint> getAllComplaints(){
        return complaintsMap;
    }

    public Complaint getComplaint(int key){
        return complaintsMap.get(key);
    }
}
