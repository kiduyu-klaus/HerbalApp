package com.kiduyu.joshuaproject.HerbalApp.Models;

public class User {

    String fname,area,email,phone,pass,image;

    public User (){

    }

    public User(String fname, String area, String email, String phone, String pass, String image) {
        this.fname = fname;
        this.area = area;
        this.email = email;
        this.phone = phone;
        this.pass = pass;
        this.image = image;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
