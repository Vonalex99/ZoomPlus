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
import com.example.zoom.db.Meeting;
import com.example.zoom.db.MeetingsDataSource;
import com.example.zoom.R;
import com.example.zoom.db.Message;
import com.example.zoom.db.MessageDataSource;

import java.util.List;

public class PreviousMeetingsAdapter extends RecyclerView.Adapter<PreviousMeetingsAdapter.ViewHolder> {
    private List<Meeting> meetingList;

    private MeetingsDataSource meetingsDataSource;
    private MessageDataSource messagesDataSource;

    private Bundle mBundle;
    private Context mContext;
    private Fragment mFragment;
    private ViewGroup container;
    private FragmentActivity activity;

    private boolean hasMessages;

    public PreviousMeetingsAdapter(Context context, ViewGroup container, FragmentActivity activity) {
        mContext = context;
        mBundle = new Bundle();
        this.container = container;
        this.activity = activity;

        meetingsDataSource = new MeetingsDataSource(context);
        messagesDataSource = new MessageDataSource(context);

        try {
            int userId = 0;
            meetingsDataSource.open();
            messagesDataSource.open();
            this.meetingList = meetingsDataSource.getPreviousMeetings();
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

                listParticipants(meeting.getId());
            }
        });

        viewHolder.chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hasMessages(meeting.getId()))
                    getMessages(meeting.getId());
                else
                    Toast.makeText(mContext, "No exchanged messages", Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return meetingList.size();
    }

    private boolean hasMessages(String meetingId) {
        List<Message> messages = messagesDataSource.getMessagesById(meetingId);
        return messages.size() > 0;
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


    private void listParticipants(String id) {
        MainActivity mainActivity = (MainActivity) mContext;
        PreviousMeetingsParticipantsDialog participantsDialog = new PreviousMeetingsParticipantsDialog(id);
        participantsDialog.show(mainActivity.getSupportFragmentManager(), "PreviousMeetingsParticipantsDialog");
    }

    private void getMessages(String id) {
        MainActivity mainActivity = (MainActivity) mContext;
        PreviousMeetingChatDialog chatDialog = new PreviousMeetingChatDialog(id);
        chatDialog.show(mainActivity.getSupportFragmentManager(), "PreviousMeetingsParticipantsDialog");
    }
}
