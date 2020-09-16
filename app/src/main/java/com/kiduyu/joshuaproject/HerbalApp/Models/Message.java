package com.kiduyu.joshuaproject.HerbalApp.Models;

/**
 * Created by Kiduyu klaus
 * on 13/09/2020 22:42 2020
 */
public class Message {

    String time,trimmedmessage,sender,receiver;

    public Message (){

    }

    public Message(String time, String trimmedmessage, String sender, String receiver) {
        this.time = time;
        this.trimmedmessage = trimmedmessage;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTrimmedmessage() {
        return trimmedmessage;
    }

    public void setTrimmedmessage(String trimmedmessage) {
        this.trimmedmessage = trimmedmessage;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
