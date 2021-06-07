package com.example.zoom.ui.meetings;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zoom.db.Meeting;
import com.example.zoom.db.MeetingsDataSource;
import com.example.zoom.R;
import java.util.List;

public class PreviousMeetingsAdapter extends RecyclerView.Adapter<PreviousMeetingsAdapter.ViewHolder> {
    private List<Meeting> meetingList;
    private MeetingsDataSource meetingsDataSource;
    private Bundle mBundle;
    private Context mContext;
    private Fragment mFragment;

    public PreviousMeetingsAdapter(Context context) {
        mContext = context;
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


    }

    @Override
    public int getItemCount() {
        return meetingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView meetingName;
        private TextView meetingId;
        private TextView meetingDate;

        public ViewHolder(View view) {
            super(view);
            meetingName = (TextView)view.findViewById(R.id.prev_meeting_name);
            meetingId = (TextView)view.findViewById(R.id.prev_meeting_id);
            meetingDate = (TextView)view.findViewById(R.id.prev_meeting_date);
        }
    }
}
