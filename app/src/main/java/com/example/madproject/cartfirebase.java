package com.example.madproject;

public class cartfirebase
{
    private  String ItemName,Price,quantity,imgUrl;

    public cartfirebase() {
    }

    public cartfirebase(String itemName, String price, String quantity,String imgUrl) {
        ItemName = itemName;
        Price = price;
        this.imgUrl=imgUrl;
        this.quantity = quantity;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getimgUrl() { return imgUrl; }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}

