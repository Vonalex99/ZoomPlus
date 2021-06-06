package com.example.zoom;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewMeetingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

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

    private void listParticipants(){


    }


}