package com.example.zoom.db;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.example.zoom.ui.contacts.Contacts;

public class ChatDialog extends DialogFragment{
    private View mView;
    private RecyclerView recyclerView;
    private ChatDialogAdapter chatDialogAdapter;

    private MeetingsDataSource meetingsDataSource;
    private MessageDataSource messageDataSource;
    private ContactDataSource contactsDataSource;

    private String meetingId;

    private List<Contacts> participantsList;
    private Spinner spinner;
    private List<String> participantsName;
    private Meeting meeting;
    private int pos;
    private EditText content;
    private ImageView mImageView;
    private byte[] image;

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

        content  = (EditText)mView.findViewById(R.id.message);
        meetingsDataSource = new MeetingsDataSource(getContext());

        messageDataSource = new MessageDataSource(getContext());

        contactsDataSource = new ContactDataSource(getContext());

        try {
            meetingsDataSource.open();
            messageDataSource.open();
            contactsDataSource.open();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        participantsList = contactsDataSource.getParticipantsById(getParticipantsId());

        Button send = (Button) mView.findViewById(R.id.sendBtn);

        participantsName = new ArrayList<>();

        meeting = meetingsDataSource.getMeetingbyId(meetingId);
        try {
            messageDataSource.open();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(content.getText().toString() == null || content.getText().toString().isEmpty())
                    Toast.makeText(getContext(), "Empty Message",
                            Toast.LENGTH_SHORT).show();
                else if(content.getText().toString().equals("Type Here"))
                    Toast.makeText(getContext(), "Insert Message",
                            Toast.LENGTH_SHORT).show();
                else {
                    Message m;
                    boolean hasImage = false;
                    if(image != null)
                        hasImage = true;

                    if (pos == 0)
                        m = new Message((id++) + "", "0", "all", meetingId, content.getText().toString(), hasImage, image);
                    else
                        m = new Message((id++) + "", "0", String.valueOf(participantsList.get(pos).getId()), meetingId, content.getText().toString(), hasImage, image);
                    messageDataSource.addMessage(m);
                    image = null;
                    mImageView.setImageURI(null);
                    updateView();
                }
            } });


        spinner = (Spinner) mView.findViewById(R.id.spinner);
        List<String> usernames = getUserNames(participantsList);

        ImageButton addBtn = (ImageButton) mView.findViewById(R.id.addBtn);

        mImageView = (ImageView) mView.findViewById(R.id.img);
       /* String uri = "@drawable/clip";
        int imageResource = getContext().getResources().getIdentifier(uri, null, getContext().getPackageName());
        Drawable res = getContext().getResources().getDrawable(imageResource);
        addBtn.setImageDrawable(res);*/
        addBtn.setVisibility(View.VISIBLE);

        setupSpinner();

        addBtn.setClickable(true);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);

                pos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub

            }
        });

        updateView();
        return mView;
    }


    private void setupSpinner()
    {
        List<String> participantsName = new ArrayList<>();
        Contacts participant;
       // boolean foundPosition = false;
        int position = 0;
        participantsName.add("all");
        for (int n = 0; n < participantsList.size() - 1; n++)
        {
            participant = participantsList.get(n);
            participantsName.add(participant.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, participantsName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(position);
        //spinner.setEnabled(false);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_CANCELED) {
            switch (requestCode) {
                case 1:
                    if (resultCode == Activity.RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        mImageView.setImageURI(selectedImage);
                        InputStream imageStream = null;
                        try {
                            imageStream = getContext().getContentResolver().openInputStream(selectedImage);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Bitmap imgBitmap = BitmapFactory.decodeStream(imageStream);
                        image = DbBitmapUtility.getBytes(imgBitmap);
                    }
                    break;
            }
        }
    }

    public void getSelectedContact(View v) {
        Contacts contact = (Contacts) spinner.getSelectedItem();
    }

    private void displayContactData(Contacts contact) {
        String name = contact.getName();
    }

}
