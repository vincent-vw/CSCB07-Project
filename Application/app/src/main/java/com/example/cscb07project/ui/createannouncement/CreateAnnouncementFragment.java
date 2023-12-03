package com.example.cscb07project.ui.createannouncement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cscb07project.MainActivity;
import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentCreateAnnouncementBinding;
import com.example.cscb07project.ui.Announcement;
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
        EditText editTextAnnouncement = (EditText) getView().findViewById(R.id.editText_announcement);
        String announcementText = editTextAnnouncement.getText().toString().trim();
        String username = MainActivity.user.getUsername();

        if (!announcementText.isEmpty()) {
            String key = databaseReference.push().getKey();

            Announcement announcement = new Announcement(username, announcementText);

            databaseReference.child(key).setValue(announcement);
            editTextAnnouncement.setText("");
            Toast.makeText(requireContext(), "Announcement posted successfully", Toast.LENGTH_SHORT).show();
        } else {
            // Prompting the user to fill in all required fields
            Toast.makeText(requireContext(), "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
        }
    }
}
