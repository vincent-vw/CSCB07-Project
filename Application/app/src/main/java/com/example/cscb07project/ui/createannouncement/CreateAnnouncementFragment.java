package com.example.cscb07project.ui.createannouncement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cscb07project.MainActivity;
import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentCreateAnnouncementBinding;
import com.example.cscb07project.ui.createannouncement.CreateAnnouncementViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        String announcement = userText.getText().toString();
        userText.setText(""); // Clear userText
        String key = db.getReference(announcement).push().getKey(); // Get a unique key
        ref.child("announcements").child(key).setValue(announcement); // Set announcement with the unique key as key, announcement as value
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
