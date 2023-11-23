package com.example.cscb07project.ui.createaccount;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cscb07project.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginFragmentModel extends Fragment {
    private FirebaseDatabase db;

    public LoginFragmentModel() {
        this.db = FirebaseDatabase.getInstance("https://cscb07project-c6a1c-default-rtdb.firebaseio.com/");
    }

    public void queryStudentsDB(LoginFragmentPresenter presenter, User user) {
        DatabaseReference ref = MainActivity.db.getReference();
        DatabaseReference query = ref.child(user.getIdentity()).child(user.getUsername());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                presenter.setStudentsOutput(dataSnapshot, user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void queryAdminsDB(LoginFragmentPresenter presenter, User user) {
        DatabaseReference ref = MainActivity.db.getReference();
        DatabaseReference query = ref.child(user.getIdentity()).child(user.getUsername());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                presenter.setAdminsOutput(dataSnapshot, user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
