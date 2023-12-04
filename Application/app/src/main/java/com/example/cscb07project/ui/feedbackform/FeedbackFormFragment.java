package com.example.cscb07project.ui.feedbackform;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cscb07project.MainActivity;
import com.example.cscb07project.R;

import com.example.cscb07project.ui.feedback.Feedback;
import com.google.android.material.slider.Slider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FeedbackFormFragment extends Fragment {
    private EditText editTextEvent;
    private EditText editTextComment;
    private Slider sliderNumericRating;
    private EditText editTextAdditionalComments;
    private TextView textUsername;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback_form, container, false);

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("feedback");

        // Initialize UI components
        editTextEvent = view.findViewById(R.id.editText_event);
        editTextComment = view.findViewById(R.id.editText_comment);
        sliderNumericRating = view.findViewById(R.id.slider_numeric_rating);
        editTextAdditionalComments = view.findViewById(R.id.editText_additional_comments);
        textUsername = view.findViewById(R.id.text_username);
        Button buttonSubmitFeedback = view.findViewById(R.id.button_submit_feedback);

        textUsername.setText("Posting as " + MainActivity.user.getUsername());

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
        int numericRating = (int)sliderNumericRating.getValue();
        String additionalComments = editTextAdditionalComments.getText().toString().trim();

        // Get username
        String username = MainActivity.user.getUsername();

        // Check for empty fields (except for additional comments)
        if (!TextUtils.isEmpty(event) && !TextUtils.isEmpty(comment)) {

            // Create an instance of Feedback
            Feedback feedback = new Feedback(event, comment, username, numericRating,
                    additionalComments);

            checkEventExistsAddFeedback(feedback);
        } else {
            // Prompting the user to fill in all required fields
            Toast.makeText(requireContext(), "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkEventExistsAddFeedback(Feedback feedback) {
        DatabaseReference query = FirebaseDatabase.getInstance().getReference("events").child(feedback.getEvent());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Generate a unique key for the feedback
                    String key = databaseReference.push().getKey();
                    // Send the feedback to Firebase
                    databaseReference.child(key).setValue(feedback.classToJson(), new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error == null) {
                                // Success
                                clearForm();
                                Toast.makeText(requireContext(), "Feedback submitted successfully.", Toast.LENGTH_SHORT).show();
                            } else {
                                // Handle the error
                                Toast.makeText(requireContext(), "Failed to submit feedback. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(requireContext(), "Event does not exist.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void clearForm() {
        editTextEvent.setText("");
        editTextComment.setText("");
        editTextAdditionalComments.setText("");
    }
}
