package com.example.zoom.ui.meetings;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zoom.MainActivity;
import com.example.zoom.NewMeetingActivity;
import com.example.zoom.db.ChatDialog;
import com.example.zoom.db.Meeting;
import com.example.zoom.db.MeetingsDataSource;
import com.example.zoom.R;
import com.example.zoom.ui.listMeetingParticipants.ListParticipantsDialog;
import com.example.zoom.ui.schedule.ScheduleFragment;

import java.util.List;

public class PreviousMeetingsAdapter extends RecyclerView.Adapter<PreviousMeetingsAdapter.ViewHolder> {
    private List<Meeting> meetingList;
    private MeetingsDataSource meetingsDataSource;
    private Bundle mBundle;
    private Context mContext;
    private Fragment mFragment;
    private ViewGroup container;
    private FragmentActivity activity;

    public PreviousMeetingsAdapter(Context context, ViewGroup container, FragmentActivity activity) {
        mContext = context;
        mBundle = new Bundle();
        this.container = container;
        this.activity = activity;

        meetingsDataSource = new MeetingsDataSource(context);
        try {
            int userId = 0;
            meetingsDataSource.open();
            this.meetingList = meetingsDataSource.getPreviousMeetings();
            System.out.println("Meetings list size: " + meetingList.size());
        } catch (Exception e){
        } finally {
           meetingsDataSource.close();
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.previous_meeting_item, parent, false);
        return new PreviousMeetingsAdapter.ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Meeting meeting = meetingList.get(position);
        viewHolder.meetingName.setText(meeting.getName());
        viewHolder.meetingId.setText(meeting.getId());
        viewHolder.meetingDate.setText(meeting.getDate());

        viewHolder.participantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fragmentJump(meeting);
                PreviousMeetingsParticipantsFragment fragment = new PreviousMeetingsParticipantsFragment();
                FragmentManager manager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(container.getId(), fragment, fragment.toString());
                fragmentTransaction.addToBackStack(fragment.toString());
                fragmentTransaction.commit();

                Toast.makeText(mContext, "lololol1", Toast.LENGTH_LONG).show();
              /*  PreviousMeetingsParticipantsFragment fragment = new PreviousMeetingsParticipantsFragment();
                mBundle.putString("id", String.valueOf(meeting.getId()));
                fragment.setArguments(mBundle);

                FragmentManager manager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(container.getId(), fragment, fragment.toString());
                fragmentTransaction.addToBackStack(fragment.toString());
                fragmentTransaction.commit();*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return meetingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView meetingName;
        private TextView meetingId;
        private TextView meetingDate;
        private Button participantsButton;
        private Button chatButton;


        public ViewHolder(View view) {
            super(view);
            meetingName = (TextView)view.findViewById(R.id.prev_meeting_name);
            meetingId = (TextView)view.findViewById(R.id.prev_meeting_id);
            meetingDate = (TextView)view.findViewById(R.id.prev_meeting_date);
            participantsButton = (Button)view.findViewById(R.id.participants);
            chatButton = (Button)view.findViewById(R.id.chat);
        }
    }

    private void fragmentJump(Meeting meeting) {
        mFragment = new PreviousMeetingsParticipantsFragment();
        mBundle.putString("id", String.valueOf(meeting.getId()));
        mFragment.setArguments(mBundle);
        switchContent(R.id.fragment_previous_meeting_participants, mFragment);
    }

    public void switchContent(int id, Fragment fragment) {
        if (mContext == null)
            return;
        if (mContext instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) mContext;
            Fragment frag = fragment;
            mainActivity.switchContent(id, frag);
        }
    }
}
