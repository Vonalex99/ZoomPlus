package com.example.zoom.ui.listMeetingParticipants;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zoom.R;
import com.example.zoom.ui.contacts.ContactDataSource;
import com.example.zoom.ui.contacts.Contacts;
import com.example.zoom.db.Meeting;
import com.example.zoom.db.MeetingsDataSource;

import java.util.ArrayList;
import java.util.List;

public class ListParticipantsAdapter extends RecyclerView.Adapter<ListParticipantsAdapter.ViewHolder>{

    private List<Contacts> participantsList;
    private String participantsId;
    private MeetingsDataSource meetingsDataSource;
    private ContactDataSource contactsDataSource;
    private Context mContext;
    private Meeting meeting;
    private boolean muted = false;

    public ListParticipantsAdapter(Context context, String meetingId) {
        mContext = context;
        meetingsDataSource = new MeetingsDataSource(context);
        contactsDataSource = new ContactDataSource(context);
       // selectedFolders = new ArrayList<>();
        try {
          //  String meetingId = "1";
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_participants_row, parent, false);
        return new ListParticipantsAdapter.ViewHolder(view);
    }

    private List<Integer> getParticipantsId(){
        String participants = meeting.getParticipants();

        List<Integer> ids = new ArrayList<>();
        ids.add(Integer.parseInt(meeting.getHostId()));

        if(participants != null){
            String[] p = participants.split(";");

            for(String part : p )
                ids.add(Integer.parseInt(part));
        }

        return ids;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contacts contact = participantsList.get(position);
        String name = contact.getName();

        if(position == 0)
            name = contact.getName() + " (Host)";

        holder.contactName.setText(name);

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
        });

        holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress == 0)
                    holder.microButton.setImageResource(R.drawable.ic_action_mute);
                else
                    holder.microButton.setImageResource(R.drawable.ic_action_mic);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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
        private SeekBar seekBar;

        public ViewHolder(View view) {
            super(view);
            contactImage = (ImageView)view.findViewById(R.id.contactImage);
            cameraButton = (ImageButton)view.findViewById(R.id.cam);
            microButton = (ImageButton)view.findViewById(R.id.micro);
            contactName = (TextView)view.findViewById(R.id.contactName);
            seekBar = (SeekBar)view.findViewById(R.id.seekBar);


            String uri = "@drawable/google_contacts";
            int imageResource = mContext.getResources().getIdentifier(uri, null, mContext.getPackageName());
            Drawable res = mContext.getResources().getDrawable(imageResource);
            contactImage.setImageDrawable(res);
        }
    }
}
