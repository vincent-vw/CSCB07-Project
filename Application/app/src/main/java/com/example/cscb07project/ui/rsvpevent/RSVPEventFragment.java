package com.example.cscb07project.ui.rsvpevent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.cscb07project.ui.Event;

import java.util.List;
import java.util.ArrayList;

import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentRequireBinding;

public class RSVPEventFragment extends Fragment{
    private FragmentRequireBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRequireBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Spinner spinner = root.findViewById(R.id.spinner);

        //listing the events
        List<Event> eventList = null; //add the list of event titles

        List<String> eventTitles = new ArrayList<>();
        for (Event event : eventList) {
            eventTitles.add(event.getTitle());
        }

        String[] titlesArray = eventTitles.toArray(new String[0]);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, titlesArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        /*
        create functionality that shows the event details when an event is selected in spinner
        pressing rsvp button should create a rsvp
        */

        Button buttonrsvp = root.findViewById(R.id.button_rsvp);

        buttonrsvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*when they click rsvp, it makes the rsvp for the event */
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
