package com.example.mystore.models;

import java.io.Serializable;

public class MyCartModel implements Serializable {
    private String img_url, productName, productPrice, totalQuantity,totalPrice, productId;

    public MyCartModel(){

    }
    public MyCartModel(String img_url, String productName, String productPrice, String totalQuantity, String totalPrice,String productId){
        this.img_url = img_url;
        this.productName = productName;
        this.productPrice = productPrice;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.productId = productId;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String computeTotalPrice(){
        totalPrice = String.valueOf(Integer.parseInt(productPrice)* Integer.parseInt(totalQuantity));
        return totalPrice;
    }
}
