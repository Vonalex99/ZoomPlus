package com.example.zoom.meeting;

import com.example.zoom.contacts.Contact;

import java.util.LinkedList;
import java.util.List;

public class Meeting {

    private String id;
    private List <Contact> participants;

    public Meeting(String id){
        setId(id);
        participants = new LinkedList<>();
    }

    public void addParticipant(Contact c){
        participants.add(c);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Contact> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Contact> participants) {
        this.participants = participants;
    }
}
