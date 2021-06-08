package com.example.zoom.db;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zoom.R;
import com.example.zoom.ui.contacts.ContactDataSource;
import com.example.zoom.ui.contacts.Contacts;
import com.example.zoom.ui.listMeetingParticipants.ListParticipantsAdapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatDialogAdapter extends RecyclerView.Adapter<ChatDialogAdapter.ViewHolder>{
    private Context mContext;
    private List<Message> messages;
    private MessageDataSource messageDataSource;

    public ChatDialogAdapter(Context context,String meetingId) throws SQLException {
        mContext = context;

        messageDataSource = new MessageDataSource(mContext);
        messageDataSource.open();
        messages = messageDataSource.getMessagesById(meetingId);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.message_row, parent, false);
        return new ChatDialogAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = messages.get(position);

        if(!message.isHasImage())
            holder.imageChat.setVisibility(View.GONE);
        else
            holder.imageChat.setVisibility(View.VISIBLE);

        holder.originTextView.setText(message.getOrig());
        holder.msgText.setText(message.getContent());

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageChat;
        private TextView originTextView;
        private TextView msgText;

        public ViewHolder(View view) {
            super(view);
            imageChat = (ImageView)view.findViewById(R.id.chatImg);
            originTextView = (TextView)view.findViewById(R.id.originTextView);
            msgText = (TextView)view.findViewById(R.id.messageTextView);

        }
    }







}
