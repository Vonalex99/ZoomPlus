package com.example.zoom.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.zoom.ui.contacts.Contacts;

public class MessageDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME= "ZoomDatabase.db";


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Message.MessageEntry.TABLE_NAME + " (" +
                    Message.MessageEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Message.MessageEntry.COLUMN_ORIGIN + " VARCHAR2 ," +
                    Message.MessageEntry.COLUMN_DEST + " VARCHAR2  ," +
                    Message.MessageEntry.COLUMN_CONTENT + "VARCHAR2 ," +
                    Message.MessageEntry.COLUMN_MEETINGID + "VARCHAR2,"+
                    Message.MessageEntry.COLUMN_HAS_IAMGE + "BOOLEAN,"+
                    Message.MessageEntry.COLUMN_IMAGE + "BLOB)";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Contacts.ContactEntry.TABLE_NAME;


    public MessageDBHelper(@Nullable Context context) {
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
