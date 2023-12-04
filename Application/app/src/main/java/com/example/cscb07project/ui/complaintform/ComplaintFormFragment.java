package com.example.cscb07project.ui.complaintform;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cscb07project.MainActivity;
import com.example.cscb07project.R;

import com.example.cscb07project.ui.complaint.Complaint;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ComplaintFormFragment extends Fragment {
    private EditText editTextComplaint;
    private TextView textUsername;
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
        checkBoxAnonymous = view.findViewById(R.id.checkBox_anonymous);
        textUsername = view.findViewById(R.id.text_username);
        Button buttonSubmit = view.findViewById(R.id.button_new_complaint);

        textUsername.setText("Posting as " + MainActivity.user.getUsername());

        // onClickListener for the button
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitComplaint();
            }
        });

        checkBoxAnonymous.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    textUsername.setText("Posting anonymously");
                } else {
                    textUsername.setText("Posting as " + MainActivity.user.getUsername());
                }
            }
        });

        return view;
    }

    private void submitComplaint() {
        // Get complaint details from EditText
        String complaintText = editTextComplaint.getText().toString().trim();
        String username;
        boolean isAnonymous = checkBoxAnonymous.isChecked();

        if (!complaintText.isEmpty()) {
            // Clear the username if anonymous is checked
            if (isAnonymous) {
                username = "Anonymous";
            } else {
                username = MainActivity.user.getUsername();
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

            // Send the complaint to Firebase
            databaseReference.child(key).setValue(complaint.classToJson(), new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    if (error == null) {
                        // Success
                        editTextComplaint.setText("");
                        checkBoxAnonymous.setChecked(false);
                        Toast.makeText(requireContext(), "Complaint submitted successfully.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle the error
                        Toast.makeText(requireContext(), "Failed to submit complaint. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            // Prompting the user to fill in all required fields
            Toast.makeText(requireContext(), "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
        }
    }
}
