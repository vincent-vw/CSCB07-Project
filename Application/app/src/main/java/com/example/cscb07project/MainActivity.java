package com.example.cscb07project;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cscb07project.ui.Announcement;
import com.example.cscb07project.ui.User;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cscb07project.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    public static FirebaseDatabase db;
    public static User user;
    boolean isCalled = false; // To prevent initial firing of event listener

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_create_announcement,
                R.id.nav_sign_up, R.id.nav_login,
                R.id.nav_complaint_form, R.id.nav_feedback, R.id.nav_view_complaints, R.id.nav_view_feedback,
                R.id.nav_require, R.id.nav_announcements_and_events, R.id.nav_schedule_events)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        user = new User("Unknown User", "", "");

        // Link to realtime database
        db = FirebaseDatabase.getInstance("https://cscb07project-c6a1c-default-rtdb.firebaseio.com/");

        // Notification for new announcements
        createNotificationChannelAnnouncement();
        DatabaseReference query = db.getReference().child("announcements");
        isCalled = false;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(isCalled) {
                    if (snapshot.exists()) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "announcement");
                        builder.setContentTitle("New Announcement Posted");
                        builder.setSmallIcon(R.drawable.baseline_notifications_24);
                        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        managerCompat.notify(1, builder.build());
                    }
                } else {
                    isCalled = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    // Credit to: https://developer.android.com/develop/ui/views/notifications/build-notification
    private void createNotificationChannelAnnouncement() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "announcementChannel";
            String description = "Channel for announcements";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("announcement", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d("Menu", "onPrepareOptionsMenu called");

        MenuItem viewComplaintsMenuItem = menu.findItem(R.id.nav_view_complaints);
        MenuItem viewFeedbackMenuItem = menu.findItem(R.id.nav_view_feedback);
        MenuItem scheduleEventsMenuItem = menu.findItem(R.id.nav_schedule_events);
        MenuItem ComplaintFormMenuItem = menu.findItem(R.id.nav_complaint_form);
        MenuItem FeedbackFormMenuItem = menu.findItem(R.id.nav_feedback);
        // Check user's identity and hide/show menu items accordingly
        if (user != null) {
            if ("student".equals(user.getIdentity())) {
                viewComplaintsMenuItem.setVisible(false);
                viewFeedbackMenuItem.setVisible(false);
                scheduleEventsMenuItem.setVisible(false);
            } else if ("admin".equals(user.getIdentity())) {
                ComplaintFormMenuItem.setVisible(false);
                FeedbackFormMenuItem.setVisible(false);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }


    /*private void setMenuItemVisibility(MenuItem item, boolean isVisible) {
        if (item != null) {
            item.setVisible(isVisible);
        }
    }*/

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}