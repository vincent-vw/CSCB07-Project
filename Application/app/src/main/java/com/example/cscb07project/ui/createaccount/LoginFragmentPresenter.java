package com.example.cscb07project.ui.createaccount;

import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.cscb07project.ui.User;
import com.google.firebase.database.DataSnapshot;

public class LoginFragmentPresenter extends Fragment {
    LoginFragmentView view;
    LoginFragmentModel model;

    public LoginFragmentPresenter(LoginFragmentView view, LoginFragmentModel model) {
        this.view = view;
        this.model = model;
    }

    public void checkStudentsDB(User user) {
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            createAnnouncement("Don't leave username or password blank!");
        }
        else {
            model.queryStudentsDB(this, user);
        }
    }

    public void checkAdminsDB(User user) {
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            createAnnouncement("Don't leave username or password blank!");
        }
        else {
            model.queryAdminsDB(this, user);
        }
    }

    public void setStudentsOutput(DataSnapshot dataSnapshot, User user) {
        if (dataSnapshot.exists()) {
            if (user.getPassword().equals(dataSnapshot.getValue())) {
                createAnnouncement("Successfully Login! Welcome, Student " + user.getUsername() + "!");

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

    public void setAdminsOutput(DataSnapshot dataSnapshot, User user) {
        if (dataSnapshot.exists()) {
            if (user.getPassword().equals(dataSnapshot.getValue())) {
                createAnnouncement("Successfully Login! Welcome, Administrator " + user.getUsername() + "!");

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
