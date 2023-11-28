package com.example.cscb07project.ui.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.cscb07project.MainActivity;
import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentScheduleEventsBinding;
import com.example.cscb07project.databinding.FragmentSignUpBinding;
import com.example.cscb07project.ui.Date;
import com.example.cscb07project.ui.Event;
import com.example.cscb07project.ui.Time;
import com.example.cscb07project.ui.createaccount.SignUpFragmentDirections;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class ScheduleEventsFragment extends Fragment {
    private FragmentScheduleEventsBinding binding;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;

    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentScheduleEventsBinding.inflate(inflater, container, false);
        dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        timeFormat = new SimpleDateFormat("HH:mm");
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.getRoot().findViewById(R.id.schedule_button).setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                setSchedule();
            }
        });
    }

    public void setSchedule() {
        // EditText objects
        EditText titleText = getView().findViewById(R.id.event_title_edit_text);
        EditText descriptionText = getView().findViewById(R.id.event_discription_edit_text);
        EditText dateText = getView().findViewById(R.id.event_date_edit_text);
        EditText timeText = getView().findViewById(R.id.event_time_edit_text);
        EditText participantLimitText = getView().findViewById(R.id.participantlimit_edit_text);

        // String objects of EditText
        String title = titleText.getText().toString();
        String description = descriptionText.getText().toString();
        String date = dateText.getText().toString();
        String time = timeText.getText().toString();
        String limit = participantLimitText.getText().toString();

        clearAllEditText(titleText, descriptionText, dateText, timeText, participantLimitText);

        // create Event object
        if (!title.isEmpty() && !description.isEmpty() && !date.isEmpty() && !time.isEmpty() && !limit.isEmpty()) {
            Date d = null;
            Time t = null;

            // check if date is in correct format
            // if so, assign it to Date object d
            try {
                dateFormat.parse(date);
                String[] dateArray = date.split("/");
                d = new Date(dateArray[0], dateArray[1], dateArray[2]);
            }
            catch (ParseException e) {
                createAnnouncement("Input an incorrect format of date!");
                return;
            }

            // check if time is in correct format
            // if so, assign it to Time object t
            try {
                timeFormat.parse(time);
                String[] timeArray = time.split(":");
                t = new Time(timeArray[0], timeArray[1]);
            }
            catch (ParseException e) {
                createAnnouncement("Input an incorrect format of time!");
                return;
            }

            // create event object
            Event event = new Event(title, description, d, t, limit);

            // check if this event is in database
            checkDataExists(event);
        }
        else {
            // if any string in EditText is empty
            createAnnouncement("Please don't leave any block blank!");
        }
    }

    public void checkDataExists(Event event) {
        DatabaseReference ref= MainActivity.db.getReference();
        DatabaseReference query = ref.child("events").child(event.getTitle());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // check if there exits an event with the same title
                if (dataSnapshot.exists()) {
                    // get the value of date, time, description, and limit under the event in database
                    Date date = dataSnapshot.child("date").getValue(Date.class);
                    Time time = dataSnapshot.child("time").getValue(Time.class);
                    String description = dataSnapshot.child("description").getValue(String.class);
                    String limit = dataSnapshot.child("participantLimit").getValue(String.class);

                    // check if these values are equal to that in the event in the parameter
                    if (event.getDate().equals(date) && event.getTime().equals(time)
                            && event.getDescription().equals(description) && event.getParticipantLimit().equals(limit)) {
                        createAnnouncement("You entered a same event before!");
                    }
                    else {
                        // if not same, set the event to the database
                        ref.child("events").child(event.getTitle()).setValue(event);
                        createAnnouncement("Successfully setup an event!");
                    }
                }
                else {
                    // if there is no such a dataSnapshot, set the event to the database
                    ref.child("events").child(event.getTitle()).setValue(event);
                    createAnnouncement("Successfully setup an event!");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void clearAllEditText(EditText title, EditText description, EditText date,
                                 EditText time, EditText limit) {
        title.setText("");
        description.setText("");
        date.setText("");
        time.setText("");
        limit.setText("");
    }

    public void createAnnouncement(String text) {
        Toast announcement = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
        announcement.show();
    }
}
