package com.example.mystore.models;

import java.io.Serializable;

public class AllProductModel implements Serializable {
    private String name, rating, description,type,division,img_url,productId;
    private int price;

    public AllProductModel(){

    }
    public AllProductModel(String name, String rating, String description,String type, String division, String img_url, int price, String productId){
        this.name = name;
        this.rating = rating;
        this.description = description;
        this.type = type;
        this.division = division;
        this.img_url = img_url;
        this.price = price;
        this.productId = productId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
