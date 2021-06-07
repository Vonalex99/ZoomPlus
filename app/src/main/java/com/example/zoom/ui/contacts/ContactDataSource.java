package com.example.zoom.ui.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContactDataSource {

    private ContactDbHelper db;
    private SQLiteDatabase database;

    public ContactDataSource(Context context){
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

    public void addContact(Contacts c){
        ContentValues values = new ContentValues();
        values.put(Contacts.ContactEntry.COLUMN_ID,c.getId());
        values.put(Contacts.ContactEntry.COLUMN_NAME,c.getName());
        values.put(Contacts.ContactEntry.COLUMN_EMAIL,c.getEmail());
        database.insert(Contacts.ContactEntry.TABLE_NAME,null, values);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Contacts> getContacts() {
        List<Contacts> contactsList = new ArrayList<>();

        try {
            Cursor cursor = database.query(Contacts.ContactEntry.TABLE_NAME, null, null, null, null, null, null);

            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                contactsList.add(cursorToContact(cursor));
            }
        } catch (Exception e) {
           // Log.e(RecipeDataSource.class.getSimpleName(), e.getMessage());
        }

        contactsList.sort(new Comparator<Contacts>() {
            @Override
            public int compare(Contacts o1, Contacts o2) {
                return  o1.getName().compareTo(o2.getName());
            }
        });

        return contactsList;
    }

    public List<Contacts> getParticipantsById(List<Integer> contacts){
        List<Contacts> c = getContacts();
        List<Contacts> contactsList = new ArrayList<>();
        for (Contacts cont : c)
            for(Integer id: contacts)
                if (cont.getId() == id)
                    contactsList.add(cont);
        return contactsList;
    }


    private Contacts cursorToContact(Cursor cursor) {
        Contacts contact = new Contacts(null,null);
        contact.setId(cursor.getInt(cursor
                .getColumnIndexOrThrow(Contacts.ContactEntry.COLUMN_ID)));
        contact.setName(cursor.getString(cursor
                .getColumnIndexOrThrow(Contacts.ContactEntry.COLUMN_NAME)));
        contact.setEmail(cursor.getString(cursor
                .getColumnIndexOrThrow(Contacts.ContactEntry.COLUMN_EMAIL)));


        return contact;

    }

    public void fix() {
        db.fix(database);
    }
}
