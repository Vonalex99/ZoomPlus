package com.example.zoom.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.zoom.db.Meeting;

public class MeetingDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME= "ZoomDatabase.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Meeting.MeetingEntry.TABLE_NAME + " (" +
                    Meeting.MeetingEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Meeting.MeetingEntry.COLUMN_NAME + " VARCHAR2 ," +
                    Meeting.MeetingEntry.COLUMN_DATE + " VARCHAR2 ," +
                    Meeting.MeetingEntry.COLUMN_HOST_ID + " VARCHAR2," +
                    Meeting.MeetingEntry.COLUMN_PARTICIPANTS + " VARCHAR2 ," +
                    Meeting.MeetingEntry.COLUMN_CHAT_ID + "INTEGER)";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " +Meeting.MeetingEntry.TABLE_NAME;


    public MeetingDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void fix(SQLiteDatabase database) {
        database.execSQL(SQL_DELETE_ENTRIES);
        database.execSQL(SQL_CREATE_ENTRIES);
    }
}
