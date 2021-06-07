package com.example.zoom.db;

import android.provider.BaseColumns;

public class Meeting {
    private String id;
    private String name;
    private String date;
    private String hostId;
    private String participants;
    private String chatId;

    public Meeting(String id, String name, String date, String hostId){
        setId(id);
        this.name = name;
        this.date = date;
        this.hostId = hostId;
        participants = "";
        chatId = id;
    }

    public Meeting(){

    }

    public static class MeetingEntry implements BaseColumns {
        public static final String TABLE_NAME = "meetings";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_HOST_ID = "host_id";
        public static final String COLUMN_PARTICIPANTS = "participants";
        public static final String COLUMN_CHAT_ID = "chat_id";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public void addParticipant(String participant){
        participants = participants + ";" + participant;
    }

    public String getChatId() {
        return chatId;
    }

    public String getHostId() {
        return hostId;
    }

    public String setHostId(String hostId) {
        return this.hostId = hostId;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
