package com.example.zoom.ui.meetings;



import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zoom.R;
import com.example.zoom.ui.contacts.ContactDataSource;
import com.example.zoom.ui.contacts.Contacts;
import com.example.zoom.db.Meeting;
import com.example.zoom.db.MeetingsDataSource;

import java.util.ArrayList;
import java.util.List;

public class PreviousMeetingParticipantsAdapter extends RecyclerView.Adapter<PreviousMeetingParticipantsAdapter.ViewHolder>{

    private List<Contacts> participantsList;
    private String participantsId;
    private MeetingsDataSource meetingsDataSource;
    private ContactDataSource contactsDataSource;
    private Context mContext;
    private Meeting meeting;
    private boolean muted = false;

    public PreviousMeetingParticipantsAdapter(Context context, String meetingId) {
        mContext = context;
        meetingsDataSource = new MeetingsDataSource(context);
        contactsDataSource = new ContactDataSource(context);
        try {
            meetingsDataSource.open();
            contactsDataSource.open();
            this.meeting = meetingsDataSource.getMeetingbyId(meetingId);
            participantsList = contactsDataSource.getParticipantsById(getParticipantsId());
        } catch (Exception e){
        } finally {
            meetingsDataSource.close();
            contactsDataSource.close();
        }
    }

    @NonNull
    @Override
    public  ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.previous_meeting_participant_item, parent, false);
        return new PreviousMeetingParticipantsAdapter.ViewHolder(view);
    }

    private List<Integer> getParticipantsId(){
        String participants = meeting.getParticipants();

          List<Integer> ids = new ArrayList<>();

        if(participants != null){
            String[] p = participants.split(";");

            for(String part : p )
                ids.add(Integer.parseInt(part));
        }
        return ids;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contacts participant = participantsList.get(position);
        String name = participant.getName();
        if(position == 0)
            name = name + " (Host)";

        holder.participantName.setText(name);

        //set default image
        String uri = "@drawable/google_contacts";
        int imageResource = mContext.getResources().getIdentifier(uri, null, mContext.getPackageName());
        Drawable res = mContext.getResources().getDrawable(imageResource);
        holder.participantImage.setImageDrawable(res);

    }

    @Override
    public int getItemCount() {
        return participantsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView participantImage;
        private TextView participantName;

        public ViewHolder(View view) {
            super(view);
            participantImage = (ImageView)view.findViewById(R.id.participant_image);
            participantName = (TextView)view.findViewById(R.id.participant_name);

        }
    }
}
