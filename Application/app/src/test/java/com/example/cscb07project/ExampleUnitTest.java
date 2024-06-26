package com.example.cscb07project;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;




import com.example.cscb07project.ui.User;
import com.example.cscb07project.ui.account.LoginFragmentModel;
import com.example.cscb07project.ui.account.LoginFragmentPresenter;
import com.example.cscb07project.ui.account.LoginFragmentView;
import com.google.firebase.database.DataSnapshot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

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
        snapshot = Mockito.mock(DataSnapshot.class);

    }

    // Null checks
    @Test
    public void testSignInWithNullUsername() {
        presenter.signIn(null, "password", R.id.radioButton_student_login);
        verify(view).outputToast("Don't leave username, password, or selection blank.");
    }

    @Test
    public void testSignInWithNullPassword() {
        presenter.signIn("username", null, R.id.radioButton_student_login);
        verify(view).outputToast("Don't leave username, password, or selection blank.");
    }

    @Test
    public void testSignInWithBothNull() {
        presenter.signIn(null, null, R.id.radioButton_student_login);
        verify(view).outputToast("Don't leave username, password, or selection blank.");
    }


    @Test
    public void testSignInFinalizeWithNullUser() {
        presenter.signInFinalize(snapshot, null);
        verify(view).outputToast("Don't leave username, password, or selection blank.");
    }

    @Test
    public void testSignInFinalizeWithNullSnapshot() {
        presenter.signInFinalize(null, new User("test", "test123", "student"));
        verify(view).outputToast("No username found. Please sign up.");
        verify(view).navigate(R.id.action_nav_login_to_nav_sign_up);
    }

    @Test
    public void testSignInFinalizeWithAllNull() {
        presenter.signInFinalize(null, null);
        verify(view).outputToast("Don't leave username, password, or selection blank.");
    }

    // ------------ General SignIn testing starts here----------------


    // Every field is unfilled
    @Test
    public void testEmptySignIn () {
        presenter.signIn("", "", -1);
        verify(view).outputToast("Don't leave username, password, or selection blank.");
    }

    // Username is the only unfilled thing
    @Test
    public void testSignInWithEmptyUsername() {
        presenter.signIn("", "password", R.id.radioButton_student_login);
        verify(view).outputToast("Don't leave username, password, or selection blank.");
    }

    // Password is the only unfilled thing
    @Test
    public void testSignInWithEmptyPassword() {
        presenter.signIn("username", "", R.id.radioButton_student_login);
        verify(view).outputToast("Don't leave username, password, or selection blank.");
    }

    // Empty radio button, but username and password are filled in
    @Test
    public void testSignInWithEmptyRadioButton() {
        presenter.signIn("username", "password", -1);
        verify(view).outputToast("Don't leave username, password, or selection blank.");
    }

    @Test
    public void testStudentSignIn () {
        presenter.signIn("test", "test123", R.id.radioButton_student_login);
        verify(model).queryDB(any(LoginFragmentPresenter.class), any(User.class));
    }

    @Test
    public void testAdminSignIn() {
        presenter.signIn("adminName", "adminPassword", R.id.radioButton_admin_login);
        verify(model).queryDB(any(LoginFragmentPresenter.class), any(User.class));
    }

    //------------ general tests for SignInFinalize method ------------

    @Test
    public void testNoSnapShotExists () {
        when(snapshot.exists()).thenReturn(false);
        User user = new User("test", "test123", "student");
        presenter.signInFinalize(snapshot, user);
        verify(view).outputToast("No username found. Please sign up.");
        verify(view).navigate(R.id.action_nav_login_to_nav_sign_up);
    }

    @Test
    public void testSignInFinalizeWithEmptyPassword() {
        when(snapshot.exists()).thenReturn(true);
        when(snapshot.getValue(User.class)).thenReturn(new User("test", "", "student"));
        User user = new User("test", "test123", "student");
        presenter.signInFinalize(snapshot, user);
        verify(view).outputToast("Incorrect password. Please try again.");
    }

    // Testing signInFinalize with the correct student credentials
    @Test
    public void testSignInFinalizeWithCorrectStudentCredentials () {
        when(snapshot.exists()).thenReturn(true);
        when(snapshot.getValue(User.class)).thenReturn(new User("test", "test123", "student"));
        User user = new User("test", "test123", "student");
        presenter.signInFinalize(snapshot, user);
        assertEquals(MainActivity.user, user);
        verify(view).signInSuccessful(user);
        verify(view).outputToast("Successfully logged in! Welcome, " + user.getUsername() + "!");
        verify(view).navigate(R.id.action_nav_login_to_nav_home);
    }

    // Testing signInFinalize with the wrong student password
    @Test
    public void testSignInFinalizeWithWrongStudentCredentials () {
        when(snapshot.exists()).thenReturn(true);
        when(snapshot.getValue(User.class)).thenReturn(new User("test", "test12", "student"));
        User user = new User("test", "test123", "student");
        presenter.signInFinalize(snapshot, user);
        verify(view).outputToast("Incorrect password. Please try again.");
    }


    // Testing signInFinalize with the wrong student username
    @Test
    public void testSignInFinalizeWithWrongStudentCredentials2 () {
        when(snapshot.exists()).thenReturn(true);
        when(snapshot.getValue(User.class)).thenReturn(new User("t", "test123", "student"));
        User user = new User("test", "test123", "student");
        presenter.signInFinalize(snapshot, user);
        verify(view).outputToast("Incorrect username or no username found. Please try again or sign up.");
    }


    // Testing signInFinalize with the correct admin credentials
    @Test
    public void testSignInFinalizeWithCorrectAdminCredentials() {
        when(snapshot.exists()).thenReturn(true);
        when(snapshot.getValue(User.class)).thenReturn(new User("adminUser", "adminPassword", "admin"));
        User user = new User("adminUser", "adminPassword", "admin");
        presenter.signInFinalize(snapshot, user);
        assertEquals(MainActivity.user, user);
        verify(view).signInSuccessful(user);
        verify(view).outputToast("Successfully logged in! Welcome, " + user.getUsername() + "!");
        verify(view).navigate(R.id.action_nav_login_to_nav_home);
    }

    // Testing signInFinalize with the wrong admin password
    @Test
    public void testSignInFinalizeWithWrongAdminCredentials() {
        when(snapshot.exists()).thenReturn(true);
        when(snapshot.getValue(User.class)).thenReturn(new User("adminUser", "correctPassword", "admin"));
        User user = new User("adminUser", "incorrectPassword", "admin");
        presenter.signInFinalize(snapshot, user);
        verify(view).outputToast("Incorrect password. Please try again.");
    }

    // Testing signInFinalize with the wrong admin username
    @Test
    public void testSignInFinalizeWithWrongAdminCredentials2() {
        when(snapshot.exists()).thenReturn(true);
        when(snapshot.getValue(User.class)).thenReturn(new User("adminUs", "correctPassword", "admin"));
        User user = new User("adminUser", "correctPassword", "admin");
        presenter.signInFinalize(snapshot, user);
        verify(view).outputToast("Incorrect username or no username found. Please try again or sign up.");
    }

}