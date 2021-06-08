package com.example.zoom.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.zoom.ui.contacts.ContactDbHelper;
import com.example.zoom.ui.contacts.Contacts;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDataSource {

    private ContactDbHelper db;
    private SQLiteDatabase database;

    public MessageDataSource(Context context){
        db = new ContactDbHelper(context);
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
        values.put(Message.MessageEntry.COLUMN_MEETINGID,c.getId());
        values.put(Message.MessageEntry.COLUMN_ORIGIN,c.getOrig());
        values.put(Message.MessageEntry.COLUMN_DEST,c.getDest());
        values.put(Message.MessageEntry.COLUMN_CONTENT,c.getContent());
        values.put(Message.MessageEntry.COLUMN_HAS_IAMGE,c.isHasImage());
        values.put(Message.MessageEntry.COLUMN_IMAGE,c.getImage());

        database.insert(Contacts.ContactEntry.TABLE_NAME,null, values);
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
                .getColumnIndexOrThrow(Message.MessageEntry.COLUMN_HAS_IAMGE)) == 1); // se der shit é aqui
        contact.setImage(cursor.getBlob(cursor
                .getColumnIndexOrThrow(Message.MessageEntry.COLUMN_IMAGE)));

        return contact;

    }

    public void fix() {
        db.fix(database);
    }
}
