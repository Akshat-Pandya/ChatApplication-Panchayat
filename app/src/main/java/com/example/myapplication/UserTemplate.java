package com.example.myapplication;

import com.google.firebase.auth.FirebaseAuth;

public class UserTemplate {
    String username;
    String phonenumber;
    String timestamp;
    String userId;

    public UserTemplate() {
        // empty constructor
    }

    public UserTemplate(String username, String phonenumber, String timestamp,String userId) {
        this.username = username;
        this.phonenumber = phonenumber;
        this.timestamp = timestamp;
        this.userId=userId;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getUserId() {
        return userId;
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

