package com.example.zoom.ui.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.zoom.R;
import com.example.zoom.db.Meeting;
import com.example.zoom.db.MeetingsDataSource;
import com.example.zoom.ui.home.HomeFragment;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class ScheduleFragment extends Fragment {

    private static int id = 10;

    private View mView;

    private EditText nameEdit,dateEdit,fromEdit,toEdit;
    private Button saveBtn;

    private String name, date, from, to;

    private MeetingsDataSource ds;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_schedule, container, false);

        ds = new MeetingsDataSource(getContext());
        ds.open();


        nameEdit = (EditText) mView.findViewById(R.id.input_name);
        dateEdit = (EditText) mView.findViewById(R.id.input_date);
        fromEdit = (EditText) mView.findViewById(R.id.input_start_time);
        toEdit = (EditText) mView.findViewById(R.id.input_end_time);

        saveBtn = (Button) mView.findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean statusOK = processData();
                if (statusOK) {
                    ds.addMeeting(new Meeting(((id++) + ""), name, date, "0"));
                    jumpFragment(container);
                    Toast.makeText(getContext(), "Meeting Scheduled", Toast.LENGTH_LONG).show();
                }
            } });


        return mView;
        }

    private boolean processData() {
        boolean statusOK = false;
        name = nameEdit.getText().toString();
        date = dateEdit.getText().toString();
        from = dateEdit.getText().toString();
        to = dateEdit.getText().toString();

        LocalDate today = LocalDate.now();
        LocalDate meetingDate = convertDate(date);


        if(name.isEmpty())
           Toast.makeText(getContext(), "Please insert valid meeting name", Toast.LENGTH_SHORT).show();
        else if(date.isEmpty())
            Toast.makeText(getContext(), "Please insert valid meeting date", Toast.LENGTH_SHORT).show();
        else if(meetingDate == null)
            Toast.makeText(getContext(), "Please insert date in dd/MM/yyyy format", Toast.LENGTH_SHORT).show();
        else if(today.isAfter(meetingDate))
            Toast.makeText(getContext(), "Please insert future date", Toast.LENGTH_SHORT).show();
        else if(from.isEmpty())
            Toast.makeText(getContext(), "Please insert valid meeting start time", Toast.LENGTH_SHORT).show();
        else if(to.isEmpty())
            Toast.makeText(getContext(), "Please insert valid meeting end time", Toast.LENGTH_SHORT).show();
        else {
            statusOK = true;
            DayOfWeek dayOfWeek = meetingDate.getDayOfWeek();
            String weekDay = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault());
            date = weekDay + ", " + date;
        }

        return statusOK;
    }

    private LocalDate convertDate(String dateString){
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate ld = null;
        try {
            ld = LocalDate.parse(dateString, formatter);
        }  catch (DateTimeParseException exp) {

        }
        return  ld;
    }


    private void jumpFragment(ViewGroup container) {
        HomeFragment fragment = new HomeFragment();

        FragmentManager manager= getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(container.getId(), fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }

}

