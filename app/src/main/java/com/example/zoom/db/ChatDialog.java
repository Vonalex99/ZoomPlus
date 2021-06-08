package com.example.zoom.db;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zoom.R;

public class ChatDialog extends DialogFragment {
    private View mView;
    private RecyclerView recyclerView;
    private ChatDialogAdapter chatDialogAdapter;
    private MeetingsDataSource meetingsDataSource;
    private String meetingId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = (RecyclerView) mView.findViewById(R.id.recycler_msg);
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
        /*chatDialogAdapter = new ChatDialogAdapter(getContext());
        chatDialogAdapter.setHasStableIds(true);
        recyclerView.setAdapter(chatDialogAdapter);
        chatDialogAdapter.notifyDataSetChanged();*/
    }

}
