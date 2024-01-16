package com.example.myapplication;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class UserTemplate {
    String username;
    String phonenumber;
    int likesOnProfile;
    String timestamp;
    String userId;
    String profileimage_url;
    String profilebio;

    ArrayList<LikedProfile> likedProfilesList;

    public UserTemplate() {
        // empty constructor
    }

    public UserTemplate(String username, String phonenumber, String timestamp,String userId) {
        this.username = username;
        this.phonenumber = phonenumber;
        this.timestamp = timestamp;
        this.userId=userId;
    }

    public ArrayList<LikedProfile> getLikedProfilesList() {
        return likedProfilesList;
    }

    public void setLikedProfilesList(ArrayList<LikedProfile> likedProfilesList) {
        this.likedProfilesList = likedProfilesList;
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

    public String getProfileimage_url() {
        return profileimage_url;
    }

    public void setProfileimage_url(String profileimage_url) {
        this.profileimage_url = profileimage_url;
    }

    public String getProfilebio() {
        return profilebio;
    }

    public void setProfilebio(String profilebio) {
        this.profilebio = profilebio;
    }

    public int getLikesOnProfile() {
        return likesOnProfile;
    }

    public void setLikesOnProfile(int likesOnProfile) {
        this.likesOnProfile = likesOnProfile;
    }
}

