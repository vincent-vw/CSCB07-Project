package com.example.cscb07project.ui.createaccount;

import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.firebase.database.DataSnapshot;

import java.util.Objects;

public class LoginFragmentPresenter extends Fragment {
    LoginFragmentView view;
    LoginFragmentModel model;

    public LoginFragmentPresenter(LoginFragmentView view, LoginFragmentModel model) {
        this.view = view;
        this.model = model;
    }

    public void checkStudentsDB(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            createAnnouncement("Don't leave username or password blank!");
        }
        else {
            model.queryStudentsDB(this, username, password);
        }
    }

    public void checkAdminsDB(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            createAnnouncement("Don't leave username or password blank!");
        }
        else {
            model.queryAdminsDB(this, username, password);
        }
    }

    public void setStudentsOutput(DataSnapshot dataSnapshot, String username, String password) {
        if (dataSnapshot.exists()) {
            if (password.equals(dataSnapshot.getValue())) {
                createAnnouncement("Successfully Login! Welcome, Student " + username + "!");

                NavDirections action = LoginFragmentViewDirections.actionNavLoginToNavHome();
                Navigation.findNavController(view.getView()).navigate(action);
            }
            else {
                createAnnouncement("Incorrect password! Please try again!");
            }
        }
        else {
            createAnnouncement("No username found! Please sign up!");
        }
    }

    public void setAdminsOutput(DataSnapshot dataSnapshot, String username, String password) {
        if (dataSnapshot.exists()) {
            if (password.equals(dataSnapshot.getValue())) {
                createAnnouncement("Successfully Login! Welcome, Administrator " + username + "!");

                // need revise later
                NavDirections action = LoginFragmentViewDirections.actionNavLoginToNavHome();
                Navigation.findNavController(view.getView()).navigate(action);
            }
            else {
                createAnnouncement("Incorrect password! Please try again!");
            }
        }
        else {
            createAnnouncement("No username found! Please sign up!");
        }
    }

    public void createAnnouncement(String text) {
        Toast announcement = Toast.makeText(view.getActivity(), text, Toast.LENGTH_SHORT);
        announcement.show();
    }
}
