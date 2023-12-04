package com.example.cscb07project.ui.announcementsandevents;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07project.R;
import com.example.cscb07project.ui.Event;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

// Credit to: https://www.geeksforgeeks.org/how-to-populate-recyclerview-with-firebase-data-using-firebaseui-in-android-studio/

// FirebaseRecyclerAdapter is a class provided by FirebaseUI
public class EventAdaptor extends FirebaseRecyclerAdapter<String, EventAdaptor.EventsViewHolder> {

    public EventAdaptor(@NonNull FirebaseRecyclerOptions<String> options) {
        super(options);
    }

    // Function to bind the view in Card view (here "Event.xml") with data in model class (here "String.java")
    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull EventAdaptor.EventsViewHolder holder, int position, @NonNull String model) {
        Event event = Event.jsonToClass(model);
        holder.title.setText(event.getTitle());
        holder.description.setText(event.getDescription());
        holder.participantLimit.setText("Participant limit: " + event.getParticipantLimit());
        Calendar cal = event.convertScheduledTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        holder.date.setText("Date: " + dateFormat.format(cal.getTime()));
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        holder.time.setText("Time: " + timeFormat.format(cal.getTime()));
    }

    @NonNull
    @Override
    public EventAdaptor.EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event, parent, false);
        return new EventsViewHolder(view);
    }

    // Subclass to create references to the views in "event.xml"
    static class EventsViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, participantLimit, date, time;
        public EventsViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.text_title);
            description = itemView.findViewById(R.id.text_description);
            participantLimit = itemView.findViewById(R.id.text_participant_limit);
            date = itemView.findViewById(R.id.text_date);
            time = itemView.findViewById(R.id.text_time);
        }
    }
}
