package com.example.zoom.contacts;

import android.provider.BaseColumns;

public class Contact {

    private static int ids = 0;

    private int id;

    private String email,name;

    public Contact(String email, String name){
        setId(ids++);
        setEmail(email);
        setName(name);
    }

    public static class ContactEntry implements BaseColumns {
        public static final String TABLE_NAME = "contact";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
