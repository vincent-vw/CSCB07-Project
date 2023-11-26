package com.example.cscb07project.ui.createannouncement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cscb07project.MainActivity;
import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentCreateAnnouncementBinding;
import com.example.cscb07project.ui.Announcement;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAnnouncementFragment extends Fragment {
    private DatabaseReference databaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_announcement, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("announcements");

        view.findViewById(R.id.button_new_announcement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postAnnouncement();
            }
        });

        return view;
    }

    public void postAnnouncement() {
        EditText userText = (EditText) getView().findViewById(R.id.editText_new_announcement);
        String announcementText = userText.getText().toString().trim();

        if (!announcementText.isEmpty()) {
            Announcement announcement = new Announcement("PlaceholderUsername", announcementText);
            String key = databaseReference.push().getKey();

            databaseReference.child(key).setValue(announcement);
            userText.setText("");
        } else {
            userText.setError("Please enter your announcement.");
        }
    }
}
