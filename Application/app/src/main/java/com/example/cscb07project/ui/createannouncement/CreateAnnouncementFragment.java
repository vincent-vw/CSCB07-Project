package com.example.cscb07project.ui.createannouncement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentCreateAnnouncementBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAnnouncementFragment extends Fragment {
    private FragmentCreateAnnouncementBinding binding;
    FirebaseDatabase db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CreateAnnouncementViewModel createannouncementViewModel =
                new ViewModelProvider(this).get(CreateAnnouncementViewModel.class);

        binding = FragmentCreateAnnouncementBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        root.findViewById(R.id.button_new_announcement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickPostAnnouncement();
            }
        });

        // Link to realtime database
        db = FirebaseDatabase.getInstance("https://cscb07project-c6a1c-default-rtdb.firebaseio.com/");

        return root;
    }

    public void onClickPostAnnouncement(){
        DatabaseReference ref = db.getReference(); // Reference to the root node
        // https://stackoverflow.com/questions/48654071/android-studio-how-can-i-put-this-activity-in-extends-fragment
        EditText userText = (EditText) getView().findViewById(R.id.editText_new_announcement); // Get the EditText widget (used for entering text)
        String announcement = userText.getText().toString(); // Get the announcement typed
        Announcement announcementObj = new Announcement("PlaceholderUsername", announcement); // New announcement object
        userText.setText(""); // Clear userText
        String key = db.getReference(announcement).push().getKey(); // Get a unique key
        ref.child("announcements").child(key).setValue(announcementObj); // Set announcement with the unique key as key, announcement object as value
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
