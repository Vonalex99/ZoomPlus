package com.example.zoom.ui.listMeetingParticipants;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zoom.R;
import com.example.zoom.contacts.ContactDataSource;
import com.example.zoom.contacts.Contacts;
import com.example.zoom.db.Meeting;
import com.example.zoom.db.MeetingsDataSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListParticipantsAdapter extends RecyclerView.Adapter<ListParticipantsAdapter.ViewHolder>{

    private List<Contacts> participantsList;
    private String participantsId;
    private MeetingsDataSource meetingsDataSource;
    private ContactDataSource contactsDataSource;
    private Context mContext;
    private Meeting meeting;
    private boolean muted = false;

    public ListParticipantsAdapter(Context context) {
        mContext = context;
        meetingsDataSource = new MeetingsDataSource(context);
       // selectedFolders = new ArrayList<>();
        try {
            String meetingId = "0";
            meetingsDataSource.open();
            this.meeting = meetingsDataSource.getMeetingbyId(meetingId);
        } catch (Exception e){
        } finally {
            meetingsDataSource.close();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_participants_row, parent, false);
        return new ListParticipantsAdapter.ViewHolder(view);
    }

    private List<Integer> getParticipantsId(){
        String participants = meeting.getParticipants();
        String[] p = participants.split(";");
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(Integer.getInteger(meeting.getHostId()));
        for(String part : p ){
            ids.add(Integer.getInteger(part));
        }
        return ids;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      /*  Contacts contact = folderList.get(position);
        holder.contactName.setText(contact.getName());

        holder.microButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!muted){
                    holder.microButton.setImageResource(R.drawable.ic_action_mute);
                    muted = true;
                }else{
                    holder.microButton.setImageResource(R.drawable.ic_action_mic);
                    muted = false;
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return participantsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView contactImage;
        private ImageButton cameraButton;
        private ImageButton microButton;
        private TextView contactName;

        public ViewHolder(View view) {
            super(view);
            contactImage = (ImageView)view.findViewById(R.id.contactImage);
            cameraButton = (ImageButton)view.findViewById(R.id.cam);
            microButton = (ImageButton)view.findViewById(R.id.micro);
            contactName = (TextView)view.findViewById(R.id.contactName);
        }
    }
}