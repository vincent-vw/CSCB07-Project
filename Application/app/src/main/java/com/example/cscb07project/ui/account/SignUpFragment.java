package com.example.cscb07project.ui.account;

        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.EditText;
        import android.widget.RadioGroup;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.Fragment;
        import androidx.navigation.Navigation;

        import com.example.cscb07project.R;
        import com.example.cscb07project.ui.User;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;


public class SignUpFragment extends Fragment {
    private DatabaseReference databaseReference;

    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }

    public void signUp() {
        EditText userText = (EditText) getView().findViewById(R.id.editText_username_sign_up);
        EditText passText = (EditText) getView().findViewById(R.id.editText_password_sign_up);
        String username = userText.getText().toString().trim();
        String password = passText.getText().toString().trim();
        userText.setText("");
        passText.setText("");

        RadioGroup radioGroup = (RadioGroup) getView().findViewById(R.id.radioGroup_sign_up);
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();

        if (!username.isEmpty() && !password.isEmpty() && checkedRadioButtonId != -1) {
            String identity = "";
            if (checkedRadioButtonId == R.id.radioButton_student_sign_up) {
                identity = "student";
            } else {
                identity = "admin";
            }
            User user = new User(username, password, identity);
            checkDataExists(user);
        } else {
            outputToast("Don't leave username, password, or selection blank.");
        }
    }

    public void checkDataExists(User user) {
        DatabaseReference query = databaseReference.child(user.getIdentity()).child(user.getUsername());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    outputToast("There exists an account of this username. Please login.");

                    // Navigate to login fragment
                    Navigation.findNavController(getView()).navigate(R.id.action_nav_sign_up_to_nav_login);
                }
                else {
                    databaseReference.child(user.getIdentity()).child(user.getUsername()).setValue(user);
                    checkDataChange(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void checkDataChange(User user) {
        DatabaseReference query = databaseReference.child(user.getIdentity()).child(user.getUsername());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    outputToast("Successfully signed up.");

                    // Navigate to login fragment
                    Navigation.findNavController(getView()).navigate(R.id.action_nav_sign_up_to_nav_login);
                } else {
                    outputToast("Failed to sign up. Please try again.");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    public void outputToast(String text) {
        Toast announcement = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
        announcement.show();
    }
}