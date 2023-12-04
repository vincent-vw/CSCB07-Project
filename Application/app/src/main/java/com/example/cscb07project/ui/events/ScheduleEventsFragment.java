package com.example.cscb07project.ui.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cscb07project.R;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class ScheduleEventsFragment extends Fragment {
    private DatabaseReference databaseReference;
    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextLimit;
    private Date date;
    private Time time;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_events, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("events");

        editTextTitle = view.findViewById(R.id.editText_event_title);
        editTextDescription = view.findViewById(R.id.editText_event_description);
        editTextLimit = view.findViewById(R.id.editText_participant_limit);

        view.findViewById(R.id.button_schedule).setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                try {
                    setSchedule();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        view.findViewById(R.id.button_date).setOnClickListener(new View.OnClickListener(){
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

        view.findViewById(R.id.button_time).setOnClickListener(new View.OnClickListener(){
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

        return view;
    }

    private void setSchedule() throws ParseException {
        // String objects of EditText
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String limit = editTextLimit.getText().toString().trim();

        if (!title.isEmpty() && !description.isEmpty() && !limit.isEmpty() && date != null
                && time != null) {

            // Create Event object
            Event event = new Event(title, description, date, time, Integer.parseInt(limit));

            // Check if this event is in database, then add event
            checkEventExistsAdd(event);
        } else {
            createAnnouncement("Please fill in all required fields.");
        }

        // Clear form
        clearForm();
    }

    private void checkEventExistsAdd(Event event) {
        DatabaseReference query = databaseReference.child(event.getTitle());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Check if there exists an event with the same title
                if (dataSnapshot.exists()) {
                    createAnnouncement("Event already exists.");
                }
                else {
                    databaseReference.child(event.getTitle()).setValue(event.classToJson(), new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error == null) {
                                // Success
                                createAnnouncement("Event set up successfully.");
                            } else {
                                // Handle the error
                                createAnnouncement("Failed to create event. Please try again.");
                            }

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void clearForm() {
        editTextTitle.setText("");
        editTextDescription.setText("");
        editTextLimit.setText("");
        date = null;
        time = null;
        TextView selectedDate = (TextView) getView().findViewById(R.id.text_selected_date);
        selectedDate.setText("Please select a date");
        TextView selectedTime = (TextView) getView().findViewById(R.id.text_selected_time);
        selectedTime.setText("Please select a time");
    }

    private void createAnnouncement(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}
