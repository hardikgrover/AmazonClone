package com.example.amazon.Model;

public class users {
    public users(String name, String phone, String password) {
        this.name = name;
        this.phone = phone;
        this.password = password;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String phone;
    private String password;
public users(){

}

}
