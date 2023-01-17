package com.example.madproject;

import android.widget.ProgressBar;

public class Post {

    String Name;
    String Price;
    String purl;

    public Post() {
    }

    @Override
    public String toString() {
        return "Post{" +
                "Name='" + Name + '\'' +
                ", Price='" + Price + '\'' +
                ", purl='" + purl + '\'' +
                '}';
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
