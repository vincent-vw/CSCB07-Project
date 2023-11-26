package com.example.cscb07project.ui.complaintForm;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import com.example.cscb07project.R;
public class ComplaintFormFragment extends Fragment{
    private EditText editTextComplaint;
    private EditText editTextUTORid;
    private CheckBox checkBoxAnonymous;


    // Firebase
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complaint_form, container, false);

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("complaints");

        // Initialize UI components
        editTextComplaint = view.findViewById(R.id.editTextComplaint);
        editTextUTORid = view.findViewById(R.id.editTextStudentId);
        checkBoxAnonymous = view.findViewById(R.id.checkBoxAnonymous);
        Button buttonSubmit = view.findViewById(R.id.buttonSubmit);

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
        String UTORid = editTextUTORid.getText().toString().trim();
        boolean isAnonymous = checkBoxAnonymous.isChecked();

        if (!complaintText.isEmpty()) {
            // Clear the UTORid if anonymous is checked
            if (isAnonymous) {
                UTORid = "unavailable";
            } else {
                // Show error message if UTORid is empty and not anonymous
                if (UTORid.isEmpty()) {
                    editTextUTORid.setError("Please enter your UTORid.");
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


            // create an instance of Complaint
            Complaint complaint = new Complaint(UTORid, status, complaintText);

            // Create a HashMap to define the order in Firebase
            HashMap<String, Object> complaintMap = new HashMap<>();
            complaintMap.put("UTORid", complaint.getUTORid());
            complaintMap.put("status", complaint.getStatus());
            complaintMap.put("text", complaint.getText());
            complaintMap.put("timeSubmitted", complaint.getTimeSubmitted());

            // Send the complaint to Firebase
            databaseReference.child(key).setValue(complaintMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    if (error == null) {
                        // Success
                        editTextComplaint.setText("");
                        editTextUTORid.setText("");
                        checkBoxAnonymous.setChecked(false);
                        // Disable the UTORid EditText if anonymous is checked
                        editTextUTORid.setEnabled(!isAnonymous);
                        Toast.makeText(requireContext(), "Complaint submitted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle the error
                        Toast.makeText(requireContext(), "Failed to submit complaint. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            // when the user types no message
            editTextComplaint.setError("Please enter your complaint.");
        }
    }

}
