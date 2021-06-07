package com.example.zoom.ui.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.zoom.R;
import com.example.zoom.db.Meeting;
import com.example.zoom.db.MeetingsDataSource;


public class ScheduleFragment extends Fragment {

    private static int id = 10;

    private View mView;
    private Button saveBtn;
    private EditText name,date,from,to;
    private MeetingsDataSource ds;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_schedule, container, false);

        ds = new MeetingsDataSource(getContext());
        ds.open();

        saveBtn = (Button) mView.findViewById(R.id.saveBtn);
        name = (EditText) mView.findViewById(R.id.input_name);
        date = (EditText) mView.findViewById(R.id.input_date);
        from = (EditText) mView.findViewById(R.id.input_start_time);
        to = (EditText) mView.findViewById(R.id.input_end_time);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ds.addMeeting(new Meeting(((id++) + ""), name.getText().toString(), date.getText().toString(), "0"));
                Toast.makeText(getContext(), "Meeting Scheduled", Toast.LENGTH_LONG).show();
            } });


        return mView;
        }

}

