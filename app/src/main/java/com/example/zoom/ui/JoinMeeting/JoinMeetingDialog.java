package com.example.zoom.ui.JoinMeeting;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zoom.NewMeetingActivity;
import com.example.zoom.R;
import com.example.zoom.db.Meeting;
import com.example.zoom.db.MeetingsDataSource;
import com.example.zoom.ui.listMeetingParticipants.ListParticipantsAdapter;

import java.util.List;

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

        joinBtn = (Button) mView.findViewById(R.id.joinBtn);
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
                    meeting.addParticipant("0");
                    meetingsDataSource.updateDatabase(meeting);
                    Intent intent = new Intent(getContext(), NewMeetingActivity.class);
                    intent.putExtra("MEETING_ID", id);
                    startActivity(intent);
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
