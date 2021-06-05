package com.example.zoom.contacts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ContactDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME= "ZoomDatabase.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Contact.ContactEntry.TABLE_NAME + " (" +
                    Contact.ContactEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Contact.ContactEntry.COLUMN_NAME + " VARCHAR2 ," +
                    Contact.ContactEntry.COLUMN_EMAIL + " VARCHAR2 )";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Contact.ContactEntry.TABLE_NAME;


    public ContactDbHelper(@Nullable Context context) {
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
