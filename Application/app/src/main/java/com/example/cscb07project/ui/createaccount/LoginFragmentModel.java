package com.example.cscb07project.ui.createaccount;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cscb07project.MainActivity;
import com.example.cscb07project.ui.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginFragmentModel extends Fragment {
    private DatabaseReference databaseReference;

    public LoginFragmentModel() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void queryDB(LoginFragmentPresenter presenter, User user) {
        DatabaseReference query = databaseReference.child(user.getIdentity()).child(user.getUsername());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                presenter.signInFinalize(dataSnapshot, user);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
