package com.ce.cechat.event;


public class ContactEvent {

    private String mContactEvent;

    public ContactEvent(String pContactEvent) {
        this.mContactEvent = pContactEvent;
    }

    public String getContactEvent() {
        return mContactEvent;
    }

    public void setContactEvent(String pContactEvent) {
        this.mContactEvent = pContactEvent;
    }
}
