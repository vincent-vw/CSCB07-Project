package com.example.cscb07project.ui.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cscb07project.MainActivity;
import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentScheduleEventsBinding;
import com.example.cscb07project.ui.Date;
import com.example.cscb07project.ui.Event;
import com.example.cscb07project.ui.Time;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class ScheduleEventsFragment extends Fragment {
    private FragmentScheduleEventsBinding binding;
    private Date date;
    private Time time;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentScheduleEventsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.getRoot().findViewById(R.id.button_schedule).setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                setSchedule();
            }
        });

        binding.getRoot().findViewById(R.id.button_date).setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select Date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                        String dateString = dateFormat.format(new java.util.Date(selection));
                        String[] dateArray = dateString.split("/");
                        date = new Date(dateArray[0], dateArray[1], dateArray[2]);
                        TextView selectedDate = (TextView) getView().findViewById(R.id.text_selected_date);
                        selectedDate.setText(dateString);
                    }
                });
                materialDatePicker.show(getActivity().getSupportFragmentManager(), "dateTag");
            }
        });

        binding.getRoot().findViewById(R.id.button_time).setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(12)
                        .setMinute(10)
                        .setTitleText("Select Time")
                        .build();
                materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        String hourString = String.format(Locale.getDefault(),
                                "%02d", materialTimePicker.getHour());
                        String minString = String.format(Locale.getDefault(),
                                "%02d", materialTimePicker.getMinute());
                        time = new Time(hourString, minString);
                        TextView selectedTime = (TextView) getView().findViewById(R.id.text_selected_time);
                        selectedTime.setText(hourString + ":" + minString);
                    }
                });
                materialTimePicker.show(getActivity().getSupportFragmentManager(), "timeTag");
            }
        });
    }

    public void setSchedule() {
        // EditText objects
        EditText titleText = getView().findViewById(R.id.editText_event_title);
        EditText descriptionText = getView().findViewById(R.id.editText_event_description);
        EditText participantLimitText = getView().findViewById(R.id.editText_participant_limit);

        // String objects of EditText
        String title = titleText.getText().toString();
        String description = descriptionText.getText().toString();
        String limit = participantLimitText.getText().toString();

        // create Event object
        if (!title.isEmpty() && !description.isEmpty() && date != null
                && time != null && !limit.isEmpty()) {

            // create event object
            Event event = new Event(title, description, date, time, limit);

            // check if this event is in database
            checkDataExists(event);
        }
        else {
            createAnnouncement("Please don't leave any fields blank!");
        }

        clearAllEditText(titleText, descriptionText, participantLimitText);
        date = null;
        time = null;
        TextView selectedDate = (TextView) getView().findViewById(R.id.text_selected_date);
        selectedDate.setText("Please select a date");
        TextView selectedTime = (TextView) getView().findViewById(R.id.text_selected_time);
        selectedTime.setText("Please select a time");
    }

    public void checkDataExists(Event event) {
        DatabaseReference ref = MainActivity.db.getReference();
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

    public void clearAllEditText(EditText title, EditText description,
                                 EditText limit) {
        title.setText("");
        description.setText("");
        limit.setText("");
    }

    public void createAnnouncement(String text) {
        Toast announcement = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
        announcement.show();
    }
}
