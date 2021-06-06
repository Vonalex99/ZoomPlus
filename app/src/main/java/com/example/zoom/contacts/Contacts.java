package com.example.zoom.contacts;

import android.provider.BaseColumns;

public class Contacts {

    private static int ids = 0;

    private int id;

    private java.lang.String email,name;

    public Contacts(java.lang.String email, java.lang.String name){
        setId(ids++);
        setEmail(email);
        setName(name);
    }

    public static class ContactEntry implements BaseColumns {
        public static final java.lang.String TABLE_NAME = "contact";
        public static final java.lang.String COLUMN_ID = "id";
        public static final java.lang.String COLUMN_NAME = "name";
        public static final java.lang.String COLUMN_EMAIL = "email";
    }


    public java.lang.String getEmail() {
        return email;
    }

    public void setEmail(java.lang.String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }
}
