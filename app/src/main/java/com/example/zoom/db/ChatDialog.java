package com.example.zoom.db;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zoom.MainActivity;
import com.example.zoom.R;
import com.example.zoom.ui.contacts.ContactDataSource;
import com.example.zoom.ui.contacts.Contacts;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.example.zoom.ui.contacts.Contacts;

public class ChatDialog extends DialogFragment {
    private View mView;
    private RecyclerView recyclerView;
    private ChatDialogAdapter chatDialogAdapter;

    private MeetingsDataSource meetingsDataSource;
    private MessageDataSource messageDataSource;
    private ContactDataSource contactsDataSource;

    private String meetingId;

    private List<Contacts> participantsList;
    private Spinner spinner;
    

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

        contactsDataSource = new ContactDataSource(getContext());
        participantsList = contactsDataSource.getParticipantsById(getParticipantsId());

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


        spinner = (Spinner) mView.findViewById(R.id.spinner);
        List<String> usernames = getUserNames(participantsList);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, usernames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    Toast.makeText(getContext(), item.toString(),
                            Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getContext(), "Selected",
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub

            }
        });

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


    private List<Integer> getParticipantsId(){
        Meeting meeting = meetingsDataSource.getMeetingbyId(meetingId);
        String participants = meeting.getParticipants();

        List<Integer> ids = new ArrayList<>();
        ids.add(Integer.parseInt(meeting.getHostId()));

        if(participants != null){
            String[] p = participants.split(";");

            for(String part : p )
                ids.add(Integer.parseInt(part));
        }

        return ids;
    }

    private List<String> getUserNames(List<Contacts> users) {
        List<String> contactNames = new ArrayList<>();
        for (Contacts c : users) {
            contactNames.add(c.getName());
        }
    return  contactNames;

    }


    public void getSelectedContact(View v) {
        Contacts contact = (Contacts) spinner.getSelectedItem();
    }

    private void displayContactData(Contacts contact) {
        String name = contact.getName();
    }

}
