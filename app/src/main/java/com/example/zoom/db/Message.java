package com.example.zoom.db;

import android.provider.BaseColumns;

public class Message {

    private String id;
    private String meetingId;
    private String orig;
    private String dest;
    private String content;
    private boolean hasImage;
    private byte[] image;

    public Message(String id, String orig, String dest, String meetingId, String content, boolean hasImage, byte[] img) {
        this.id = id;
        this.orig = orig;
        this.dest = dest;
        this.meetingId = meetingId;
        this.content = content;
        this.hasImage = hasImage;
        this.image = img;

    }



    public static class MessageEntry implements BaseColumns {
        public static final java.lang.String TABLE_NAME = "message";
        public static final java.lang.String COLUMN_ID = "id";
        public static final java.lang.String COLUMN_MEETINGID = "meetId";
        public static final java.lang.String COLUMN_ORIGIN = "origin";
        public static final java.lang.String COLUMN_DEST = "destinatary";
        public static final java.lang.String COLUMN_CONTENT = "content";
        public static final java.lang.String COLUMN_HAS_IAMGE = "HASMESSAGE";
        public static final java.lang.String COLUMN_IMAGE = "IMAGE";


    }

    public void setImage(byte[] blob) {
        image = blob;
    }

    public byte[] getImage() {
        return image;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public String getOrig() {
        return orig;
    }

    public void setOrig(String orig) {
        this.orig = orig;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Message(String id) {
        this.setId(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
