package com.kiduyu.joshuaproject.HerbalApp.Models;

/**
 * Created by Kiduyu klaus
 * on 13/09/2020 17:10 2020
 */
public class ChatList {
    private String photo;
    private String name;
    private String lastMessage;
    private String date;
    private String phone;

    public ChatList(){

    }

    public ChatList(String photo, String name, String lastMessage, String date, String phone) {
        this.photo = photo;
        this.name = name;
        this.lastMessage = lastMessage;
        this.date = date;
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
