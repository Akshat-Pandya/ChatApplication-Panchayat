package com.example.myapplication;

public class UserTemplate {
    String username;
    String phonenumber;
    String timestamp;

    public UserTemplate() {
        // empty constructor
    }

    public UserTemplate(String username, String phonenumber, String timestamp) {
        this.username = username;
        this.phonenumber = phonenumber;
        this.timestamp = timestamp;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}

