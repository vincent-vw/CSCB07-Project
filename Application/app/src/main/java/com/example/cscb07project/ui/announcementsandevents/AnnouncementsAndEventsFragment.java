package com.example.cscb07project.ui.announcementsandevents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentAnnouncementsAndEventsBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AnnouncementsAndEventsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_announcements_and_events, container, false);

        view.findViewById(R.id.button_view_announcements).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(
                        R.id.action_nav_announcements_and_events_to_nav_view_announcements);
            }
        });

        view.findViewById(R.id.button_view_events).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(
                        R.id.action_nav_announcements_and_events_to_nav_view_events);
            }
        });

        return view;
    }
}
