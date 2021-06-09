package com.example.zoom.ui.meetings;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zoom.R;

import java.sql.SQLException;


public class PreviousMeetingChatDialog extends DialogFragment {

    private RecyclerView recyclerView;
    private View mView;
    private PreviousMeetingChatAdapter adapter;
    private String meetingId;
    private boolean hasChat;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public PreviousMeetingChatDialog(String id){
        meetingId = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_previous_meeting_chat, container, false);
        recyclerView = (RecyclerView) mView.findViewById(R.id.previous_meeting_chat_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        try {
            updateView();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return mView;
    }

    @Override
    public void onResume() {
        try {
            updateView();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        super.onResume();
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateView() throws SQLException {
        adapter = new PreviousMeetingChatAdapter(getContext(), meetingId);
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}