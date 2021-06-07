package com.example.zoom.db;

public class Chat {
    private String id;
    private String orig;
    private String dest;


    public Chat(String id) {
        this.setId(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
