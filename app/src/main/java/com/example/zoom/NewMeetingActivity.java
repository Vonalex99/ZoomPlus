package com.example.zoom;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.zoom.db.ChatDialog;
import com.example.zoom.db.Meeting;
import com.example.zoom.db.MeetingsDataSource;
import com.example.zoom.ui.listMeetingParticipants.ListParticipantsDialog;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;

import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.zoom.databinding.ActivityNewMeetingBinding;

public class NewMeetingActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityNewMeetingBinding binding;
    private boolean muted = false;
    private boolean cameraOff = false;
    private String id;
    private MeetingsDataSource meetingsDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewMeetingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        id = getIntent().getStringExtra("MEETING_ID");
        if(id.equals("-1")) {
            meetingsDataSource = new MeetingsDataSource(this);
            meetingsDataSource.open();
            long i = meetingsDataSource.addMeeting(new Meeting("0", "New Meeting", "Fri, 15/06/2021", "0"));
            id = String.valueOf(i);

            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent intent = new Intent(NewMeetingActivity.this, AskPermissionActivity.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("User", "user6");
                    id = "12";
                    startActivity(intent);
                }

            }, 5000L);
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent intent = new Intent(NewMeetingActivity.this, AskPermissionActivity.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("User", "user7");
                    startActivity(intent);
                }

            }, 13000L);
        }

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_new_meeting);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);



    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_new_meeting);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void muteSound(View view) {
        ImageButton btn = (ImageButton)findViewById(R.id.imageButton5);
        if(!muted){
            btn.setImageResource(R.drawable.ic_action_mute);
            muted = true;
        }else{
            btn.setImageResource(R.drawable.ic_action_mic);
            muted = false;
        }
    }

    public void listParticipants(View view){
        ListParticipantsDialog listParticipantsDialog = new ListParticipantsDialog(id);
        listParticipantsDialog.show(getSupportFragmentManager(), "ListParticipantsDialog");

    }

    public void chat(View view){
        ChatDialog chatDialog = new ChatDialog(id);
        chatDialog.show(getSupportFragmentManager(), "ChatDialog");

    }

    public void stopCamera(View view) {
        ImageButton btn = (ImageButton)findViewById(R.id.imageButton6);
        if(!cameraOff){
            btn.setImageResource(R.drawable.ic_action_video_off);
            cameraOff = true;
        }else{
            btn.setImageResource(R.drawable.ic_action_video);
            cameraOff = false;
        }
    }

    public void exitMeeting(View view) {
        finish();
    }
}