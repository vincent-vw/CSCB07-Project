package com.example.cscb07project.ui.complaintform;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.cscb07project.MainActivity;
import com.example.cscb07project.R;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ComplaintFormFragment extends Fragment {
    private EditText editTextComplaint;
    private EditText editTextUsername;
    private CheckBox checkBoxAnonymous;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complaint_form, container, false);

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("complaints");

        // Initialize UI components
        editTextComplaint = view.findViewById(R.id.editText_complaint);
        editTextUsername = view.findViewById(R.id.editTextStudentId);
        checkBoxAnonymous = view.findViewById(R.id.checkBoxAnonymous);
        Button buttonSubmit = view.findViewById(R.id.button_new_complaint);


        // onClickListener for Submit
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitComplaint();
            }
        });

        return view;
    }


    private void submitComplaint() {
        // Get complaint text from EditText
        String complaintText = editTextComplaint.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();
        boolean isAnonymous = checkBoxAnonymous.isChecked();

        if (!complaintText.isEmpty()) {
            // Clear the username if anonymous is checked
            if (isAnonymous) {
                username = "unavailable";
            } else {
                // Show error message if username is empty and not anonymous
                if (username.isEmpty()) {
                    editTextUsername.setError("Please enter your username.");
                    return;
                }
            }


            // Generate a unique key for the complaint
            String key = databaseReference.push().getKey();

            // Determine the status based on the anonymity
            String status;
            if (isAnonymous) {
                status = "Anonymous";
            } else {
                status = "Identified";
            }

            // Create an instance of Complaint
            Complaint complaint = new Complaint(username, status, complaintText);

            HashMap<String, Object> complaintMap = new HashMap<>();
            complaintMap.put("username", complaint.getUsername());
            complaintMap.put("status", complaint.getStatus());
            complaintMap.put("text", complaint.getText());
            complaintMap.put("timeSubmitted", complaint.getTimeSubmitted());

            // Send the complaint to Firebase
            databaseReference.child(key).setValue(complaintMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    if (error == null) {
                        // No error
                        editTextComplaint.setText("");
                        editTextUsername.setText("");
                        checkBoxAnonymous.setChecked(false);
                        // Disable the username EditText if anonymous is checked
                        editTextUsername.setEnabled(!isAnonymous);
                        Toast.makeText(requireContext(), "Complaint submitted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle the error
                        Toast.makeText(requireContext(), "Failed to submit complaint. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}

