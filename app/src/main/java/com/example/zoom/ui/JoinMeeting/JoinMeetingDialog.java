package com.example.zoom.ui.JoinMeeting;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.zoom.NewMeetingActivity;
import com.example.zoom.R;
import com.example.zoom.db.Meeting;
import com.example.zoom.db.MeetingsDataSource;
import com.example.zoom.db.MessageDataSource;

import java.sql.SQLException;

public class JoinMeetingDialog extends DialogFragment {

    private View mView;
    private MeetingsDataSource meetingsDataSource;
    private String meetingId;
    private Button joinBtn;
    private EditText idEditText;
    private Bundle mBundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.fragment_join_meeting, container, false);

        idEditText = (EditText) mView.findViewById(R.id.editId);

        meetingsDataSource = new MeetingsDataSource(getContext());
        meetingsDataSource.open();

        joinBtn = (Button) mView.findViewById(R.id.joinBtn);
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idEditText.getText().toString();
                if(!id.isEmpty()) {
                    Meeting meeting = meetingsDataSource.getMeetingbyId(id);
                    if(meeting != null) {
                        meeting.addParticipant("0");
                        long new_id = meetingsDataSource.updateDatabase(meeting);
                        MessageDataSource messageDataSource = new MessageDataSource(getContext());
                        try {
                            messageDataSource.open();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        messageDataSource.updateMessagesMeeting(id , String.valueOf(new_id));
                        Intent intent = new Intent(getContext(), NewMeetingActivity.class);
                        intent.putExtra("MEETING_ID", String.valueOf(new_id));
                        startActivity(intent);
                    } else{
                        Toast.makeText(getContext(), "Invalid Meeting", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(getContext(), "Insert Meeting ID", Toast.LENGTH_SHORT).show();
            }
        });
        idEditText = (EditText) mView.findViewById(R.id.editId);

      //  updateView();
        return mView;
    }


    @Override
    public void onResume() {
        super.onResume();
    }
}
