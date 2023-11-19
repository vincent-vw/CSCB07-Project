package com.example.cscb07project.ui.viewannouncements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentViewAnnouncementsBinding;
import com.example.cscb07project.ui.viewannouncements.ViewAnnouncementsViewModel;
import com.example.cscb07project.ui.createannouncement.Announcement;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewAnnouncementsFragment extends Fragment {
    private FragmentViewAnnouncementsBinding binding;
    FirebaseDatabase db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        ViewAnnouncementsViewModel viewannouncementsViewModel = new ViewModelProvider(this).get(ViewAnnouncementsViewModel.class);

        binding = FragmentViewAnnouncementsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Link to realtime database
        db = FirebaseDatabase.getInstance("https://cscb07project-c6a1c-default-rtdb.firebaseio.com/");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
