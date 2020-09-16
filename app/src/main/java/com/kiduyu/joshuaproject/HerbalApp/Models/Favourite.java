package com.kiduyu.joshuaproject.HerbalApp.Models;

/**
 * Created by Kiduyu klaus
 * on 13/09/2020 14:05 2020
 */
public class Favourite {
    String name,disease, description, image;

    public Favourite(){

    }

    public Favourite(String name, String disease, String description, String image) {
        this.name = name;
        this.disease = disease;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
