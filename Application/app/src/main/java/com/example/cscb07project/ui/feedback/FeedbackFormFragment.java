package com.example.cscb07project.ui.feedback;

import android.os.Bundle;
import android.text.TextUtils;
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


public class FeedbackFormFragment extends Fragment {
    private EditText editTextEvent;
    private EditText editTextComment;
    private EditText editTextUsername;
    private EditText editTextNumericRating;
    private EditText editTextAdditionalComments;
    private Button buttonSubmitFeedback;

    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback_form, container, false);

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("feedback");

        // Initialize UI components
        editTextEvent = view.findViewById(R.id.editTextEvent);
        editTextComment = view.findViewById(R.id.editTextComment);
        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextNumericRating = view.findViewById(R.id.editTextNumericRating);
        editTextAdditionalComments = view.findViewById(R.id.editTextAdditionalComments);
        buttonSubmitFeedback = view.findViewById(R.id.buttonSubmitFeedback);

        // onClickListener for the button
        buttonSubmitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
            }
        });

        return view;
    }

    private void submitFeedback() {
        // Get feedback details from EditText
        String event = editTextEvent.getText().toString().trim();
        String comment = editTextComment.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();
        String numericRatingStr = editTextNumericRating.getText().toString().trim();
        int numericRating = 0;

        try {
            numericRating = Integer.parseInt(numericRatingStr);

            if (numericRating < 1 || numericRating > 10) {
                Toast.makeText(requireContext(), "Please enter a numeric rating between 1 and 10", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            // When the user types an invalid numeric rating
            Toast.makeText(requireContext(), "Please enter a valid numeric rating", Toast.LENGTH_SHORT).show();
            return;
        }

        String additionalComments = editTextAdditionalComments.getText().toString().trim();

        // Check for empty fields
        if (!TextUtils.isEmpty(event) && !TextUtils.isEmpty(comment) && !TextUtils.isEmpty(username)) {
            // Generate a unique key for the feedback
            String key = databaseReference.push().getKey();

            // Create an instance of Feedback
            Feedback feedback = new Feedback(event, comment, username, numericRating, additionalComments);

            // Send the feedback to Firebase
            databaseReference.child(key).setValue(feedback.toHashMap(), new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    if (error == null) {
                        // no errors
                        clearForm();
                        Toast.makeText(requireContext(), "Feedback submitted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle the error
                        Toast.makeText(requireContext(), "Failed to submit feedback. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            // prompting the user to fill in all required fields
            Toast.makeText(requireContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show();
        }
    }


    private void clearForm() {
        editTextEvent.setText("");
        editTextComment.setText("");
        editTextUsername.setText("");
        editTextNumericRating.setText("");
        editTextAdditionalComments.setText("");
    }
}


