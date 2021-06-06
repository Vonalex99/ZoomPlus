package com.example.zoom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.zoom.contacts.Contacts;
import com.example.zoom.contacts.ContactDataSource;
import com.example.zoom.databinding.ActivityMain3Binding;
import com.example.zoom.db.Meeting;
import com.example.zoom.db.MeetingsDataSource;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    private ActivityMain3Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            setUpDummyDatabase();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_meetings, R.id.navigation_notifications, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main3);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }

    public void newMeetingClick(View view) {
        Intent intent = new Intent(this, NewMeetingActivity.class);
        startActivity(intent);
    }


    private void setUpDummyDatabase() throws SQLException {
        ContactDataSource contactsDS = new ContactDataSource(this);
        contactsDS.open();
        contactsDS.fix();
        contactsDS.addContact(new Contacts("user2@gmail.com", "user2"));
        contactsDS.addContact(new Contacts("user3@gmail.com", "user3"));
        contactsDS.addContact(new Contacts("user4@gmail.com", "user4"));
        contactsDS.addContact(new Contacts("user5@gmail.com", "user5"));

        MeetingsDataSource meetingsDS = new MeetingsDataSource(this);
        meetingsDS.open();
        contactsDS.fix();
        meetingsDS.addMeeting(new Meeting("0", "New Meeting", "01/06/2021", "0"));
        meetingsDS.addMeeting(new Meeting("1", "New Meeting1", "02/06/2021", "0"));
        meetingsDS.addMeeting(new Meeting("2", "New Meeting2", "03/06/2021", "0"));
        meetingsDS.addMeeting(new Meeting("3", "New Meeting3", "04/06/2021", "0"));

    }
}

