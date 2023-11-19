package com.example.cscb07project.ui.viewannouncements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cscb07project.MainActivity;
import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentViewAnnouncementsBinding;
import com.example.cscb07project.ui.viewannouncements.ViewAnnouncementsViewModel;
import com.example.cscb07project.ui.Announcement;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// Credit to: https://www.geeksforgeeks.org/how-to-populate-recyclerview-with-firebase-data-using-firebaseui-in-android-studio/

public class ViewAnnouncementsFragment extends Fragment {
    private FragmentViewAnnouncementsBinding binding;
    FirebaseDatabase db;
    private RecyclerView recyclerView;
    private AnnouncementAdaptor adaptor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        ViewAnnouncementsViewModel viewannouncementsViewModel = new ViewModelProvider(this).get(ViewAnnouncementsViewModel.class);

        binding = FragmentViewAnnouncementsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Link to realtime database
        db = MainActivity.db;
        // Recycler view
        recyclerView = root.findViewById(R.id.recycler_announcements);
        // Display the Recycler view linearly
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Class from FirebaseUI to make a query to the database to fetch data
        FirebaseRecyclerOptions<Announcement> options
                = new FirebaseRecyclerOptions.Builder<Announcement>()
                .setQuery(db.getReference().child("announcements"), Announcement.class)
                .build();
        // Adaptor
        adaptor = new AnnouncementAdaptor(options);
        // Connect adaptor with Recycler view
        recyclerView.setAdapter(adaptor);
        // Start listening
        adaptor.startListening();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Stop listening
        adaptor.startListening();
        binding = null;
    }
}
