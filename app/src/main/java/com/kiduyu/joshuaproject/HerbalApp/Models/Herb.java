package com.kiduyu.joshuaproject.HerbalApp.Models;

public class Herb {

    String name,description,disease,image;

    public Herb(String name, String description, String disease, String image) {
        this.name = name;
        this.description = description;
        this.disease = disease;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
