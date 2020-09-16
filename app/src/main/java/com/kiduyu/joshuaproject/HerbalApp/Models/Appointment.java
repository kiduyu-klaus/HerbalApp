package com.kiduyu.joshuaproject.HerbalApp.Models;

/**
 * Created by Kiduyu klaus
 * on 13/09/2020 15:26 2020
 */
public class Appointment {
    String cname,cphone,uname,uphone,udescri,udate,image;

    public Appointment(){

    }

    public Appointment(String cname, String cphone, String uname, String uphone, String udescri, String udate, String image) {
        this.cname = cname;
        this.cphone = cphone;
        this.uname = uname;
        this.uphone = uphone;
        this.udescri = udescri;
        this.udate = udate;
        this.image = image;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCphone() {
        return cphone;
    }

    public void setCphone(String cphone) {
        this.cphone = cphone;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUphone() {
        return uphone;
    }

    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    public String getUdescri() {
        return udescri;
    }

    public void setUdescri(String udescri) {
        this.udescri = udescri;
    }

    public String getUdate() {
        return udate;
    }

    public void setUdate(String udate) {
        this.udate = udate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
