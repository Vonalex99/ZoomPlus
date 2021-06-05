package com.example.zoom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.zoom.contacts.Contact;
import com.example.zoom.contacts.ContactDataSource;
import com.example.zoom.databinding.ActivityMain3Binding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    private ActivityMain3Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ContactDataSource contactsDS = new ContactDataSource(this);
        try {
            contactsDS.open();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        contactsDS.fix();


        contactsDS.addContact(new Contact("user2@gmail.com", "user2"));
        contactsDS.addContact(new Contact("user3@gmail.com", "user3"));
        contactsDS.addContact(new Contact("user4@gmail.com", "user4"));
        contactsDS.addContact(new Contact("user5@gmail.com", "user5"));
       // contactsDS.addContact(new Contact("user6@gmail.com", "user6"));

        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main3);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }

    public void newMeetingClick(View view) {
        Intent intent = new Intent(this, NewMeetingActivity.class);
        startActivity(intent);
    }
}