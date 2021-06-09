package com.example.zoom.ui.meetings;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zoom.R;


public class PreviousMeetingsParticipantsDialog extends DialogFragment {
    private View mView;
    private RecyclerView recyclerView;
    private PreviousMeetingParticipantsAdapter adapter;
    private String meetingId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public PreviousMeetingsParticipantsDialog(String id){
        meetingId = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_previous_meeting_participants, container, false);
        recyclerView = (RecyclerView) mView.findViewById(R.id.previous_meeting_participants_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        updateView();


        return mView;

    }

    @Override
    public void onResume() {
        updateView();
        super.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateView() {
        adapter = new PreviousMeetingParticipantsAdapter(getContext(), meetingId);
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}