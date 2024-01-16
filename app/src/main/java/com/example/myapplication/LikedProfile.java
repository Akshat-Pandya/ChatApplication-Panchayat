package com.example.myapplication;

public class LikedProfile {
    private String username;
    private String phonenumber;
    private String imageurl;

    public LikedProfile() {
    }

    public LikedProfile(String username, String phonenumber) {
        this.username = username;
        this.phonenumber = phonenumber;
    }

    public LikedProfile(String username, String phonenumber, String imageurl) {
        this.username = username;
        this.phonenumber = phonenumber;
        this.imageurl = imageurl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
