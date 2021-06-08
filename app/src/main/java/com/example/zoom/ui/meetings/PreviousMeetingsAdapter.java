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
import androidx.recyclerview.widget.RecyclerView;

import com.example.zoom.NewMeetingActivity;
import com.example.zoom.db.ChatDialog;
import com.example.zoom.db.Meeting;
import com.example.zoom.db.MeetingsDataSource;
import com.example.zoom.R;
import com.example.zoom.ui.listMeetingParticipants.ListParticipantsDialog;

import java.util.List;

public class PreviousMeetingsAdapter extends RecyclerView.Adapter<PreviousMeetingsAdapter.ViewHolder> {
    private List<Meeting> meetingList;
    private MeetingsDataSource meetingsDataSource;
    private Bundle mBundle;
    private Context mContext;
    private Fragment mFragment;

    public PreviousMeetingsAdapter(Context context, Fragment mFragment) {
        mContext = context;
        this.mFragment = mFragment;

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

     /*   viewHolder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatDialog chatDialog = new ChatDialog();
                chatDialog.show(mFragment, "ChatDialog");

            }
        });

        viewHolder.participants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ListParticipantsDialog listParticipantsDialog = new ListParticipantsDialog(meeting.getId());
                listParticipantsDialog.show(mFragment, "ListParticipantsDialog");
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return meetingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView meetingName;
        private TextView meetingId;
        private TextView meetingDate;
        private Button participants;
        private Button chat;


        public ViewHolder(View view) {
            super(view);
            meetingName = (TextView)view.findViewById(R.id.prev_meeting_name);
            meetingId = (TextView)view.findViewById(R.id.prev_meeting_id);
            meetingDate = (TextView)view.findViewById(R.id.prev_meeting_date);
            participants = (Button)view.findViewById(R.id.participants);
            chat = (Button)view.findViewById(R.id.chat);
        }
    }
}
