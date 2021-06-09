package com.example.zoom.ui.meetings;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zoom.R;


public class PreviousMeetingsParticipantsFragment extends Fragment {

    RecyclerView recyclerView;
    View mView;
    PreviousMeetingParticipantsAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String id = bundle.getString("id");

        Toast.makeText(getContext(), id, Toast.LENGTH_LONG).show();
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_previous_meeting_participants, container, false);
        recyclerView = (RecyclerView) mView.findViewById(R.id.previous_meeting_participants_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        updateView(id);


        return mView;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateView(String id) {
        adapter = new PreviousMeetingParticipantsAdapter(getContext(), id);
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}