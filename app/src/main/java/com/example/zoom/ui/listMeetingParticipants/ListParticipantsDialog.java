package com.example.zoom.ui.listMeetingParticipants;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zoom.R;
import com.example.zoom.db.MeetingsDataSource;

public class ListParticipantsDialog extends DialogFragment {
    private View mView;
    private RecyclerView recyclerView;
    private ListParticipantsAdapter participantsAdapter;
    private MeetingsDataSource meetingsDataSource;
    private String meetingId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ListParticipantsDialog(String id){
        meetingId = id;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.list_meeting_participants, container, false);
        recyclerView = (RecyclerView) mView.findViewById(R.id.participants_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        meetingsDataSource = new MeetingsDataSource(getContext());
        meetingsDataSource.open();

        updateView();
        return mView;
    }

    @Override
    public void onResume() {
        updateView();
        super.onResume();
    }

    private void updateView() {
        participantsAdapter = new ListParticipantsAdapter(getContext(), meetingId);
        participantsAdapter.setHasStableIds(true);
        recyclerView.setAdapter(participantsAdapter);
        participantsAdapter.notifyDataSetChanged();
    }

}
