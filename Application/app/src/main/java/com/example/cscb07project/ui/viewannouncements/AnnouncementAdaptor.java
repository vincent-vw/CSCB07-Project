package com.example.cscb07project.ui.viewannouncements;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07project.R;
import com.example.cscb07project.ui.Announcement;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

// Credit to: https://www.geeksforgeeks.org/how-to-populate-recyclerview-with-firebase-data-using-firebaseui-in-android-studio/

// FirebaseRecyclerAdapter is a class provided by FirebaseUI
public class AnnouncementAdaptor extends FirebaseRecyclerAdapter<Announcement, AnnouncementAdaptor.AnnouncementsViewHolder> {

    public AnnouncementAdaptor(@NonNull FirebaseRecyclerOptions<Announcement> options) {
        super(options);
    }

    // Function to bind the view in Card view (here "Announcement.xml") with data in model class (here "Announcement.java")
    @Override
    protected void onBindViewHolder(@NonNull AnnouncementAdaptor.AnnouncementsViewHolder holder, int position, @NonNull Announcement model) {
        holder.username.setText(model.getUsername());
        holder.announcement.setText(model.getAnnouncement());
    }

    @NonNull
    @Override
    public AnnouncementAdaptor.AnnouncementsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement, parent, false);
        return new AnnouncementsViewHolder(view);
    }

    // Subclass to create references to the views in "announcement.xml"
    static class AnnouncementsViewHolder extends RecyclerView.ViewHolder {
        TextView username, announcement;
        public AnnouncementsViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.text_username);
            announcement = itemView.findViewById(R.id.text_announcement);
        }
    }
}
