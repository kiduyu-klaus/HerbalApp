package com.kiduyu.joshuaproject.HerbalApp.Models;

public class Consultant {
    String name, phone, idnumber, location, image, date;

    public Consultant(String name, String phone, String idnumber, String location, String image, String date) {
        this.name = name;
        this.phone = phone;
        this.idnumber = idnumber;
        this.location = location;
        this.image = image;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
