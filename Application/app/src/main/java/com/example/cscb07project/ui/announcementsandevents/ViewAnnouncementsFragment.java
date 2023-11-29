package com.example.cscb07project.ui.announcementsandevents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07project.R;
import com.example.cscb07project.ui.Announcement;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// Credit to: https://www.geeksforgeeks.org/how-to-populate-recyclerview-with-firebase-data-using-firebaseui-in-android-studio/

public class ViewAnnouncementsFragment extends Fragment {
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private AnnouncementAdaptor adaptor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_announcements, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("announcements");

        // Recycler view
        recyclerView = view.findViewById(R.id.recycler_announcements);
        // Display the Recycler view linearly
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Class from FirebaseUI to make a query to the database to fetch data
        FirebaseRecyclerOptions<Announcement> options
                = new FirebaseRecyclerOptions.Builder<Announcement>()
                .setQuery(databaseReference, Announcement.class)
                .build();
        // Adaptor
        adaptor = new AnnouncementAdaptor(options);
        // Connect adaptor with Recycler view
        recyclerView.setAdapter(adaptor);
        // Start listening
        adaptor.startListening();

        return view;
    }
}
