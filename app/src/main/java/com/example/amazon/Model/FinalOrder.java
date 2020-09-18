package com.example.amazon.Model;

public class FinalOrder {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public FinalOrder() {
    }

    public FinalOrder(String name, String address, String price, String phoneNumber, String date) {
        this.name = name;
        this.address = address;
        this.price = price;
        this.phoneNumber = phoneNumber;
        this.date = date;
    }

    private String name,address,price,phoneNumber,date;
}
