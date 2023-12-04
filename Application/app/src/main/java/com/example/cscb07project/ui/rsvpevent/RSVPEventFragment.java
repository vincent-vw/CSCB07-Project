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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentEventRsvpBinding;
import com.example.cscb07project.ui.event.Event;

import java.util.ArrayList;
import java.util.List;

public class RSVPEventFragment extends Fragment {
    private RSVPEventViewModel rsvpEventViewModel;
    private FragmentEventRsvpBinding binding;
    private View root;
    private Button eventLoadButton;
    private Spinner spinner;
    private TextView eventDescription;
    private Event currentEvent = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //initialize
        rsvpEventViewModel = new ViewModelProvider(this).get(RSVPEventViewModel.class);
        binding = FragmentEventRsvpBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        eventLoadButton = root.findViewById(R.id.event_load);
        spinner = root.findViewById(R.id.spinner);
        eventDescription = root.findViewById(R.id.event_description);

        List<String> eventsPreviewList = new ArrayList<>();
        ArrayAdapter<String> eventAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, eventsPreviewList);

        eventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(eventAdapter);
        //TODO spinner drop down

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

        //load button
        eventLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventsPreviewList.clear();
                List<Event> eventList = rsvpEventViewModel.getEventManager().getAllEventsSortedByTime();
                int eventCount = 1;
                for (Event event : eventList) {
                    eventsPreviewList.add((eventCount++) + ": " + event.previewEventAsString());
                }
                eventAdapter.notifyDataSetChanged();
            }
        });

        /*
        create functionality that shows the event details when an event is selected in spinner
        pressing rsvp button should create a rsvp
        */

        Button buttonrsvp = root.findViewById(R.id.button_rsvp);

        buttonrsvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO get current event, check if enrolled/max participants reached, add the participant, update event, display toast
                if (currentEvent == null) {
                    //reject
                } else if (currentEvent.maxParticipantsReached()) {
                    //reject
                } else if (currentEvent.isUserEnrolled(null)) {//TODO get current user
                    //reject
                } else {
                    //update the event, toast "success"
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
