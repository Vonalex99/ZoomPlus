package com.example.zoom.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDataSource {

    private MessageDBHelper db;
    private SQLiteDatabase database;

    public MessageDataSource(Context context){
        db = new MessageDBHelper(context);
    }

    public SQLiteDatabase getDatabase(){
        return database;
    }

    public void open() throws SQLException {
        database = db.getWritableDatabase();
        //db.onUpgrade(database, 7, 8);
    }

    public void close() {
        if (db != null) {
            db.close();
        }
        if (database != null) {
            database.close();
        }
    }

    public void addMessage(Message c){
        ContentValues values = new ContentValues();
        values.put(Message.MessageEntry.COLUMN_ID,c.getId());
        values.put(Message.MessageEntry.COLUMN_MEETINGID,c.getMeetingId());
        values.put(Message.MessageEntry.COLUMN_ORIGIN,c.getOrig());
        values.put(Message.MessageEntry.COLUMN_HAS_IAMGE,c.isHasImage());
        values.put(Message.MessageEntry.COLUMN_DEST,c.getDest());
        values.put(Message.MessageEntry.COLUMN_CONTENT,c.getContent());
        int bool = c.isHasImage() ? 1:0;
        values.put(Message.MessageEntry.COLUMN_HAS_IAMGE,bool);
        values.put(Message.MessageEntry.COLUMN_IMAGE,c.getImage());

        database.insert(Message.MessageEntry.TABLE_NAME,null, values);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Message> getMessages() {
        List<Message> messagesList = new ArrayList<>();

        try {
            Cursor cursor = database.query(Message.MessageEntry.TABLE_NAME, null, null, null, null, null, null);

            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                messagesList.add(cursorToMessage(cursor));
            }
        } catch (Exception e) {
            // Log.e(RecipeDataSource.class.getSimpleName(), e.getMessage());
        }

        return messagesList;
    }

    public List<Message> getMessagesById(String id){
        List<Message> m = getMessages();
        List<Message> messagesList = new ArrayList<>();
        for (Message msg : m)
            if(msg.getMeetingId().equals(id))
                messagesList.add(msg);
        return messagesList;
    }

    public long updateDatabase(Message m){
       ContentValues values = new ContentValues();
       values.put(Message.MessageEntry.COLUMN_CONTENT, m.getContent());
       values.put(Message.MessageEntry.COLUMN_DEST, m.getDest());
       values.put(Message.MessageEntry.COLUMN_HAS_IAMGE, m.isHasImage());
       values.put(Message.MessageEntry.COLUMN_IMAGE,  m.getImage());
       values.put(Message.MessageEntry.COLUMN_ORIGIN,  m.getOrig());
       values.put(Message.MessageEntry.COLUMN_MEETINGID,  m.getMeetingId());
       // updating row
       return database.update(Message.MessageEntry.TABLE_NAME,values,Message.MessageEntry.COLUMN_ID + "= ?", new String[] {m.getId()});

       // return addMeeting(m);
    }
    public void updateMessagesMeeting(String oldId, String newId){
        List<Message> messages = getMessages();
        for(Message m: messages){
            if(m.getMeetingId().equals(oldId)) {
                m.setMeetingId(newId);
                updateDatabase(m);
            }
        }

    }

    private Message cursorToMessage(Cursor cursor) {
        Message contact = new Message(null, null,null,null,null,false,null);
        contact.setId(cursor.getString(cursor
                .getColumnIndexOrThrow(Message.MessageEntry.COLUMN_ID)));
        contact.setOrig(cursor.getString(cursor
                .getColumnIndexOrThrow(Message.MessageEntry.COLUMN_ORIGIN)));
        contact.setDest(cursor.getString(cursor
                .getColumnIndexOrThrow(Message.MessageEntry.COLUMN_DEST)));
        contact.setMeetingId(cursor.getString(cursor
                .getColumnIndexOrThrow(Message.MessageEntry.COLUMN_MEETINGID)));
        contact.setContent(cursor.getString(cursor
                .getColumnIndexOrThrow(Message.MessageEntry.COLUMN_CONTENT)));
        contact.setHasImage(cursor.getInt(cursor
                .getColumnIndexOrThrow(Message.MessageEntry.COLUMN_HAS_IAMGE)) == 1);
        contact.setImage(cursor.getBlob(cursor
                .getColumnIndexOrThrow(Message.MessageEntry.COLUMN_IMAGE)));

        return contact;
    }

    public void fix() {
        db.fix(database);
    }
}
