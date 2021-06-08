package com.example.zoom.db;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zoom.R;

import java.sql.SQLException;

public class ChatDialog extends DialogFragment {
    private View mView;
    private RecyclerView recyclerView;
    private ChatDialogAdapter chatDialogAdapter;
    private MeetingsDataSource meetingsDataSource;
    private String meetingId;
    private MessageDataSource messageDataSource;

    private static int id = 7;

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

        messageDataSource = new MessageDataSource(getContext());


        Button send = (Button) mView.findViewById(R.id.sendBtn);

        try {
            messageDataSource.open();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

         String orig  = "JOANa";
           // String meetingId = "JOANa";

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content  = ((EditText)v.findViewById(R.id.message)).getText().toString();
                Message m = new Message((id++) + "", orig, "all", meetingId, content, false, null);
                messageDataSource.addMessage(m);
                updateView();
            } });

        updateView();
        return mView;
    }

    @Override
    public void onResume() {
        updateView();
        super.onResume();
    }

    public ChatDialog(String id){
        meetingId = id;
    }

    private void updateView()  {

        try {
            chatDialogAdapter = new ChatDialogAdapter(getContext(), meetingId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        chatDialogAdapter.setHasStableIds(true);
        recyclerView.setAdapter(chatDialogAdapter);
        chatDialogAdapter.notifyDataSetChanged();
    }

}
