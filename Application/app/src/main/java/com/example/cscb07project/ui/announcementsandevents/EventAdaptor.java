package com.example.cscb07project.ui.announcementsandevents;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07project.R;
import com.example.cscb07project.ui.Announcement;
import com.example.cscb07project.ui.Event;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

// Credit to: https://www.geeksforgeeks.org/how-to-populate-recyclerview-with-firebase-data-using-firebaseui-in-android-studio/

// FirebaseRecyclerAdapter is a class provided by FirebaseUI
public class EventAdaptor extends FirebaseRecyclerAdapter<Event, EventAdaptor.EventsViewHolder> {

    public EventAdaptor(@NonNull FirebaseRecyclerOptions<Event> options) {
        super(options);
    }

    // Function to bind the view in Card view (here "Event.xml") with data in model class (here "Event.java")
    @Override
    protected void onBindViewHolder(@NonNull EventAdaptor.EventsViewHolder holder, int position, @NonNull Event model) {
        holder.title.setText(model.getTitle());
        holder.description.setText(model.getDescription());
        holder.participantLimit.setText("Participant limit: " + model.getParticipantLimit());
        holder.date.setText("Date: " + model.getDate().getDay()
                + "/" + model.getDate().getMonth()
                + "/" + model.getDate().getYear());
        holder.time.setText("Time: " + model.getTime().getHour() + ":"
                + model.getTime().getMinute());
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
