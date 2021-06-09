package com.example.zoom.ui.meetings;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zoom.R;
import com.example.zoom.db.ChatDialogAdapter;
import com.example.zoom.db.DbBitmapUtility;
import com.example.zoom.db.Meeting;
import com.example.zoom.db.MeetingsDataSource;
import com.example.zoom.db.Message;
import com.example.zoom.db.MessageDataSource;
import com.example.zoom.ui.contacts.ContactDataSource;
import com.example.zoom.ui.contacts.Contacts;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PreviousMeetingChatAdapter extends RecyclerView.Adapter<PreviousMeetingChatAdapter.ViewHolder>{

    private Context mContext;
    private List<Message> messages;
    private MessageDataSource messagesDataSource;
    private String meetingId;
    private ContactDataSource contactsDataSource;

    public PreviousMeetingChatAdapter(Context context,String meetingId) throws SQLException {
        mContext = context;

        this.meetingId = meetingId;

        contactsDataSource = new ContactDataSource(mContext);
        messagesDataSource = new MessageDataSource(mContext);

        try {
            messagesDataSource.open();
            contactsDataSource.open();
            this.messages = messagesDataSource.getMessagesById(meetingId);
        } catch (Exception e){
            messagesDataSource.close();
            contactsDataSource.close();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.message_item, parent, false);
        return new PreviousMeetingChatAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = messages.get(position);

        if(!message.isHasImage())
            holder.msgImage.setVisibility(View.GONE);
        else {
            holder.msgImage.setVisibility(View.VISIBLE);
            holder.msgImage.setImageBitmap(DbBitmapUtility.getImage(message.getImage()));
        }

        List<Integer> contacts = new ArrayList<>();
        contacts.add(Integer.parseInt(message.getOrig()));
        List<Contacts> contactList = contactsDataSource.getParticipantsById(contacts);
        String name = contactList.get(0).getName();

        holder.origin.setText("From " + name);
        holder.msgText.setText(message.getContent());

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView msgImage;
        private TextView origin;
        private TextView msgText;

        public ViewHolder(View view) {
            super(view);
            origin = (TextView)view.findViewById(R.id.origin);
            msgImage = (ImageView)view.findViewById(R.id.message_img);
            msgText = (TextView)view.findViewById(R.id.message_text);

        }
    }
}
