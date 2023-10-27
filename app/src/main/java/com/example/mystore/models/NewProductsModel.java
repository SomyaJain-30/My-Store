package com.example.mystore.models;

import java.io.Serializable;

public class NewProductsModel implements Serializable {
    private String img_url, description, name, rating, type,  division;
    private int price;

    public NewProductsModel(){

    }
    public NewProductsModel(String img_url, String description, String name, String rating, String type, String division, int price){
        this.description = description;
        this.img_url = img_url;
        this.name = name;
        this.rating = rating;
        this.price = price;
        this.type = type;
        this.division = division;

    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }
}
