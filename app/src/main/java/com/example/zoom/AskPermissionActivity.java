package com.example.zoom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zoom.db.Meeting;
import com.example.zoom.db.MeetingsDataSource;
import com.example.zoom.ui.contacts.ContactDataSource;
import com.example.zoom.ui.contacts.Contacts;

import java.sql.SQLException;

public class AskPermissionActivity extends Activity {

    private Button acceptButton;
    private Button rejectButton;
    private TextView textBox;
    private MeetingsDataSource meetingsDataSource;
    private ContactDataSource contactsDS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_permission);

        acceptButton = (Button) findViewById(R.id.acceptButton);
        rejectButton = (Button) findViewById(R.id.rejectButton);
        textBox = (TextView) findViewById(R.id.textBox);
        textBox.setGravity(Gravity.CENTER_HORIZONTAL);

        //setup db access
        meetingsDataSource = new MeetingsDataSource(getApplicationContext());
        contactsDS = new ContactDataSource(getApplicationContext());
        meetingsDataSource.open();
        try {
            contactsDS.open();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Meeting meeting = meetingsDataSource.getMeetingbyId(getIntent().getStringExtra("ID"));

        //Add dummy contact to the database
        Contacts contact = new Contacts("user6@gmail.com", "user6");
        contactsDS.addContact(contact);


        textBox.append("user6 requesting entry!");

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meeting.addParticipant("5");
                meetingsDataSource.updateDatabase(meeting);
                finish();
            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.3));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);
    }
}