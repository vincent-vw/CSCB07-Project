package com.example.cscb07project.ui.rsvpevent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cscb07project.MainActivity;
import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentEventRsvpBinding;
import com.example.cscb07project.ui.event.Event;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RSVPEventFragment extends Fragment {
    private RSVPEventViewModel rsvpEventViewModel;
    private FragmentEventRsvpBinding binding;
    private View root;
    private Button buttonEventLoad;
    private Spinner spinner;
    private TextView eventDescription;
    private Button buttonRSVP;
    private Event currentEvent = null;
    private DatabaseReference databaseReference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rsvpEventViewModel = new ViewModelProvider(this).get(RSVPEventViewModel.class);
        binding = FragmentEventRsvpBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        databaseReference = FirebaseDatabase.getInstance().getReference("events");

        buttonEventLoad = root.findViewById(R.id.event_load);
        spinner = root.findViewById(R.id.spinner);
        eventDescription = root.findViewById(R.id.event_description);
        buttonRSVP = root.findViewById(R.id.button_rsvp);

        List<String> eventsPreviewList = new ArrayList<>();
        ArrayAdapter<String> eventAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, eventsPreviewList);

        eventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(eventAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                currentEvent = rsvpEventViewModel.getEventManager().getEvent(Integer.parseInt(eventsPreviewList.get(position).split(":")[0]) - 1);
                eventDescription.setText(currentEvent.viewEventAsString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                eventDescription.setText("Please select an event!");
            }
        });

        buttonEventLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.VISIBLE);
                buttonRSVP.setVisibility(View.VISIBLE);
                eventsPreviewList.clear();
                List<Event> eventList = rsvpEventViewModel.getEventManager().getAllEventsSortedByTime();
                int eventCount = 1;
                for (Event event : eventList) {
                    eventsPreviewList.add((eventCount++) + ": " + event.previewEventAsString());
                }
                eventAdapter.notifyDataSetChanged();
            }
        });

        buttonRSVP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentEvent == null) {
                    Toast.makeText(getActivity(), "Event not selected.", Toast.LENGTH_SHORT).show();
                } else if (currentEvent.getScheduledTime() < System.currentTimeMillis()) {
                    Toast.makeText(getActivity(), "This event has already past.", Toast.LENGTH_SHORT).show();
                } else if (currentEvent.maxParticipantsReached()) {
                    Toast.makeText(getActivity(), "Max number of participants has been reached.", Toast.LENGTH_SHORT).show();
                } else if (currentEvent.isUserEnrolled(MainActivity.user.getUsername())) {
                    Toast.makeText(getActivity(), "You have already enrolled in this event.", Toast.LENGTH_SHORT).show();
                } else {
                    currentEvent.addParticipant(MainActivity.user.getUsername());
                    databaseReference.child(currentEvent.getTitle()).setValue(currentEvent.classToJson(), new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error == null) {
                                Toast.makeText(getActivity(), "Successfully RSVPed to the event.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Failed to RSVP to the event. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
