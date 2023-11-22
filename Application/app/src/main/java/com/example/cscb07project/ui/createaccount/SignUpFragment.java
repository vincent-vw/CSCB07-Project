package com.example.cscb07project.ui.createaccount;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.cscb07project.R;
import com.example.cscb07project.databinding.FragmentSignUpBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.example.cscb07project.MainActivity;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// problem: sign up same username

public class SignUpFragment extends Fragment {
    private FragmentSignUpBinding binding;

    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // sign_up_button
        view.findViewById(R.id.sign_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignUp();
            }
        });

        // student_check & admin_check
        CheckBox studentCheck = view.findViewById(R.id.student_check);
        CheckBox adminCheck = view.findViewById(R.id.admin_check);
        studentCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    adminCheck.setChecked(false);
                    adminCheck.setEnabled(false);
                }
                else {
                    adminCheck.setEnabled(true);
                }
            }
        });
        adminCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    studentCheck.setChecked(false);
                    studentCheck.setEnabled(false);
                }
                else {
                    studentCheck.setEnabled(true);
                }
            }
        });
    }

    // sign_up_button
    public void onClickSignUp() {
        // get the database reference
        DatabaseReference ref = MainActivity.db.getReference();

        // get user input
        EditText userText = (EditText) getView().findViewById(R.id.sign_up_username_edit_text);
        EditText passText = (EditText) getView().findViewById(R.id.sign_up_password_edit_text);
        String username = userText.getText().toString();
        String password = passText.getText().toString();
        userText.setText("");
        passText.setText("");

        // modify student or admin in database
        if (!username.isEmpty() && !password.isEmpty()) {
            CheckBox studentCheck = getView().findViewById(R.id.student_check);
            CheckBox adminCheck = getView().findViewById(R.id.admin_check);
            if (studentCheck.isChecked()) {
                ref.child("students").child(username).setValue(password);
                checkDataChange("students", username);
            }
            else if (adminCheck.isChecked()) {
                ref.child("admins").child(username).setValue(password);
                checkDataChange("admins", username);
            }
            else {
                Toast announcement = Toast.makeText(getActivity(),
                        "Please select students or admin!", Toast.LENGTH_SHORT);
                announcement.show();
            }
        }
        else {
            Toast announcement = Toast.makeText(getActivity(),
                    "Don't leave username or password blank!", Toast.LENGTH_SHORT);
            announcement.show();
        }
    }

    public void checkDataChange(String path, String username) {
        DatabaseReference ref= MainActivity.db.getReference();
        DatabaseReference query = ref.child(path).child(username);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean isFound = snapshot.exists();
                if (isFound) {
                    // successful message
                    Toast announcement = Toast.makeText(getActivity(),
                            "Successfully sign up an account!", Toast.LENGTH_SHORT);
                    announcement.show();

                    // navigate to login fragment
                    NavDirections action = SignUpFragmentDirections.actionNavSignUpToNavLogin();
                    Navigation.findNavController(getView()).navigate(action);
                }
                else {
                    // fail message
                    Toast announcement = Toast.makeText(getActivity(),
                            "Fail to sign up an account! Please try again!",
                            Toast.LENGTH_SHORT);
                    announcement.show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    public void onDestroyView () {
        super.onDestroyView();
        binding = null;
    }
}
