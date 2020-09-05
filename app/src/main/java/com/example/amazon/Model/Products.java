package com.example.amazon.Model;

public class Products {
    private String pname,description,price,category,pid,date,time,image;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Products(String pname, String description, String price, String category, String pid, String date, String time, String image) {
        this.pname = pname;
        this.description = description;
        this.price = price;
        this.category = category;
        this.pid = pid;
        this.date = date;
        this.time = time;
        this.image = image;
    }

    public Products(){

    }
}
