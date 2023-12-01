package com.example.cscb07project.ui.account;


import androidx.fragment.app.Fragment;

import com.example.cscb07project.MainActivity;
import com.example.cscb07project.R;
import com.example.cscb07project.ui.User;
import com.google.firebase.database.DataSnapshot;

public class LoginFragmentPresenter extends Fragment {
    LoginFragmentView view;
    LoginFragmentModel model;

    public LoginFragmentPresenter(LoginFragmentView view, LoginFragmentModel model) {
        this.view = view;
        this.model = model;
    }

    public void signIn(String username, String password, int checkedRadioButtonId) {
        if (!username.isEmpty() && !password.isEmpty() && checkedRadioButtonId != -1) {
            String identity = "";
            if (checkedRadioButtonId == R.id.radioButton_student_login) {
                identity = "student";
            } else {
                identity = "admin";
            }
            User user = new User(username, password, identity);
            model.queryDB(this, user);
        } else {
            view.outputToast("Don't leave username, password, or selection blank.");
        }
    }

    public void signInFinalize(DataSnapshot dataSnapshot, User user) {
        if (dataSnapshot.exists()) {
            User userFromDB = dataSnapshot.getValue(User.class);
            assert userFromDB != null;

            if (user.getPassword().equals(userFromDB.getPassword())) {
                MainActivity.user = user;
                view.signInSuccessful(user);
                view.outputToast("Successfully logged in! Welcome, " + user.getUsername() + "!");
                view.navigate(R.id.action_nav_login_to_nav_home);
            } else {
                view.outputToast("Incorrect password. Please try again.");
            }
        } else {
            view.outputToast("No username found. Please sign up.");
            view.navigate(R.id.action_nav_login_to_nav_sign_up);
        }
    }
}
