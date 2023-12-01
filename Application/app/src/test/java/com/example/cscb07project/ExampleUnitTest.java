package com.example.cscb07project;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.navigation.NavController;

import com.example.cscb07project.ui.User;
import com.example.cscb07project.ui.account.LoginFragmentModel;
import com.example.cscb07project.ui.account.LoginFragmentPresenter;
import com.example.cscb07project.ui.account.LoginFragmentView;
import com.google.firebase.database.DataSnapshot;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {
    @Mock
    private LoginFragmentView view;

    @Mock
    private LoginFragmentModel model;

    @Mock
    private DataSnapshot snapshot;

    private LoginFragmentPresenter presenter;

    @Before
    public void setUp () {
        presenter = new LoginFragmentPresenter(view, model);
        snapshot = mock(DataSnapshot.class);
    }

    @Test
    public void testEmptySignIn () {
        presenter.signIn("", "", -1);
        verify(view).outputToast("Don't leave username, password, or selection blank.");
    }

    @Test
    public void testStudentSignIn () {
        presenter.signIn("test", "test123", R.id.radioButton_student_login);
        verify(model).queryDB(any(LoginFragmentPresenter.class), any(User.class));
    }

    @Test
    public void testAdminSignIn() {
        presenter.signIn("test", "test123", R.id.radioButton_admin_login);
        verify(model).queryDB(any(LoginFragmentPresenter.class), any(User.class));
    }


    @Test
    public void testNoSnapShotExists () {
        when(snapshot.exists()).thenReturn(false);
        User user = new User("test", "test123", "student");
        presenter.signInFinalize(snapshot, user);
        verify(view).outputToast("No username found. Please sign up.");
        verify(view).navigate(R.id.action_nav_login_to_nav_sign_up);
    }

    @Test
    public void testSnapShotExistsWithCorrectPass () {
        when(snapshot.exists()).thenReturn(true);
        when(snapshot.getValue(User.class)).thenReturn(new User("test", "test123", "student"));
        User user = new User("test", "test123", "student");
        presenter.signInFinalize(snapshot, user);
        assertEquals(MainActivity.user, user);
        verify(view).signInSuccessful(user);
        verify(view).outputToast("Successfully logged in! Welcome, " + user.getUsername() + "!");
        verify(view).navigate(R.id.action_nav_login_to_nav_home);
    }

    @Test
    public void testSnapShotExistsWithWrongPass () {
        when(snapshot.exists()).thenReturn(true);
        when(snapshot.getValue(User.class)).thenReturn(new User("test", "test12", "student"));
        User user = new User("test", "test123", "student");
        presenter.signInFinalize(snapshot, user);
        verify(view).outputToast("Incorrect password. Please try again.");
    }
}