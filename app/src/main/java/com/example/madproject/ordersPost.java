package com.example.madproject;

public class ordersPost {

    String Date_of_adding;
    String Items;
    String grandtotal;
    String Status;

    public ordersPost() {
    }

    @Override
    public String toString() {
        return "ordersPost{" +
                "Date_of_adding='" + Date_of_adding + '\'' +
                ", Items='" + Items + '\'' +
                ", grandtotal='" + grandtotal + '\'' +
                ", Status='" + Status + '\'' +
                '}';
    }

    public String getDate_of_adding() {
        return Date_of_adding;
    }

    public void setDate_of_adding(String date_of_adding) {
        Date_of_adding = date_of_adding;
    }

    public String getItems() {
        return Items;
    }

    public void setItems(String items) {
        Items = items;
    }

    public String getGrandtotal() {
        return grandtotal;
    }

    public void setGrandtotal(String grandtotal) {
        this.grandtotal = grandtotal;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
