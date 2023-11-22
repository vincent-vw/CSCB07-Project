package com.example.cscb07project.ui.complaintForm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import com.example.cscb07project.R;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class complaintFormFragment extends Fragment {

    private EditText editTextComplaint;
    private Button buttonSubmit;

    private DatabaseReference databaseReference;

    public complaintFormFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complaint_form, container, false);

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("complaints");

        // Initialize UI components
        editTextComplaint = view.findViewById(R.id.editTextComplaint);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);

        // onClickListener for the "Submit" button
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitComplaint();
            }
        });

        return view;
    }

    private void submitComplaint() {
        // Get complaint text from EditText (user interaction)
        String complaintText = editTextComplaint.getText().toString().trim();

        if (!complaintText.isEmpty()) {
            String username = "AnonymousHuman";

            // Generate a unique key for the complaint
            String key = databaseReference.push().getKey();

            // Store the complaint into the instance of Complaint
            Complaint complaint = new Complaint(complaintText, username);

            // Send the complaint to Firebase
            databaseReference.child(key).setValue(complaint, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    if (error == null) {
                        // Success
                        editTextComplaint.setText("");
                        Toast.makeText(requireContext(), "Complaint submitted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle the error
                        Toast.makeText(requireContext(), "Failed to submit complaint. Please try again.", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        } else {
            // An error message shows up if the user types no message
            editTextComplaint.setError("Please enter your complaint.");
        }
    }

}
