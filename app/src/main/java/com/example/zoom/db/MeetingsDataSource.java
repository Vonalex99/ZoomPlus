package com.example.zoom.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MeetingsDataSource {
    private MeetingDbHelper db;
    private SQLiteDatabase database;

    public MeetingsDataSource(Context context){
        db = new MeetingDbHelper(context);
    }


    public SQLiteDatabase getDatabase(){
        return database;
    }

    public long addMeeting(Meeting m) {
        ContentValues values = new ContentValues();
        values.put(Meeting.MeetingEntry.COLUMN_NAME, m.getName());
        values.put(Meeting.MeetingEntry.COLUMN_DATE, m.getDate());
        values.put(Meeting.MeetingEntry.COLUMN_HOST_ID, m.getHostId());
        values.put(Meeting.MeetingEntry.COLUMN_PARTICIPANTS, m.getParticipants());
        values.put(Meeting.MeetingEntry.COLUMN_CHAT_ID,  m.getChatId());
        // Inserting Row
        long id = database.insert(Meeting.MeetingEntry.TABLE_NAME, null, values);
        return id;
    }

    public void fix(){
        db.fix(database);
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

    public void deleteItem(Meeting card) {
        database.delete(Meeting.MeetingEntry.TABLE_NAME, Meeting.MeetingEntry.COLUMN_ID + "= ?" , new String[] {String.valueOf(card.getId())});
    }

    public long updateDatabase(Meeting m){
       /* ContentValues values = new ContentValues();
        values.put(Meeting.MeetingEntry.COLUMN_NAME, m.getName());
        values.put(Meeting.MeetingEntry.COLUMN_HOST_ID, m.getHostId());
        values.put(Meeting.MeetingEntry.COLUMN_DATE, m.getDate());
        values.put(Meeting.MeetingEntry.COLUMN_PARTICIPANTS,  m.getParticipants());
        values.put(Meeting.MeetingEntry.COLUMN_CHAT_ID,  m.getChatId());
        // updating row
        database.update(Meeting.MeetingEntry.TABLE_NAME,values,Meeting.MeetingEntry.COLUMN_ID + "= ?", new String[] {m.getId()});
        */
        return addMeeting(m);
    }

    public List<Meeting> getPreviousMeetings(){
        List<Meeting> meetingList = new ArrayList<>();

        try {
            String query = "SELECT * FROM " + Meeting.MeetingEntry.TABLE_NAME  + " WHERE " + Meeting.MeetingEntry.COLUMN_ID + "<5";
            Cursor cursor = database.rawQuery(query, new String[]{});

            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                meetingList.add(cursorToMeeting(cursor));
            }
        } catch (Exception e) {
            Log.e(MeetingsDataSource.class.getSimpleName(), e.getMessage());
        }

        return meetingList;
    }

    public List<Meeting> getScheduledMeetings(){
        List<Meeting> meetingList = new ArrayList<>();

        try {
            String query = "SELECT * FROM " + Meeting.MeetingEntry.TABLE_NAME + " WHERE " + Meeting.MeetingEntry.COLUMN_ID + ">=5";
            Cursor cursor = database.rawQuery(query, new String[]{});

            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                meetingList.add(cursorToMeeting(cursor));
            }
        } catch (Exception e) {
            Log.e(MeetingsDataSource.class.getSimpleName(), e.getMessage());
        }

        return meetingList;
    }

    public Meeting getMeetingbyId(String id){
            List<Meeting> list = getPreviousMeetings();

            for(Meeting m : list)
                if(m.getId().equals(id))
                    return m;

            list = getScheduledMeetings();//foi a joana

            for(Meeting m : list)
                if(m.getId().equals(id))
                    return m;

            return null;
    }

    private Meeting cursorToMeeting(Cursor cursor) {
        Meeting meeting = new Meeting();
        meeting.setId(cursor.getString(cursor.getColumnIndexOrThrow(Meeting.MeetingEntry.COLUMN_ID)));
        meeting.setName(cursor.getString(cursor.getColumnIndexOrThrow(Meeting.MeetingEntry.COLUMN_NAME)));
        meeting.setDate(cursor.getString(cursor.getColumnIndexOrThrow(Meeting.MeetingEntry.COLUMN_DATE)));
        meeting.setHostId(cursor.getString(cursor.getColumnIndex(Meeting.MeetingEntry.COLUMN_HOST_ID)));
        meeting.setParticipants(cursor.getString(cursor.getColumnIndex(Meeting.MeetingEntry.COLUMN_PARTICIPANTS)));
        meeting.setChatId(cursor.getString(cursor.getColumnIndex(Meeting.MeetingEntry.COLUMN_CHAT_ID)));
        return meeting;

    }

}
