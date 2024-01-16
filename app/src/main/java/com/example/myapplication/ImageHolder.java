package com.example.myapplication;

import android.graphics.drawable.Drawable;

public class ImageHolder {
    private static Drawable imageDrawable;
    private static String usernameX;
    private static String phonenumberX;
    private static int likesOnProfile;
    private static String userIdX;

    public static void setImageDrawable(Drawable drawable) {
        imageDrawable = drawable;
    }

    public static Drawable getImageDrawable() {
        return imageDrawable;
    }

    public static String getUsernameX() {
        return usernameX;
    }

    public static void setUsernameX(String usernameX) {
        ImageHolder.usernameX = usernameX;
    }

    public static String getPhonenumberX() {
        return phonenumberX;
    }

    public static void setPhonenumberX(String phonenumberX) {
        ImageHolder.phonenumberX = phonenumberX;
    }

    public static String getUserIdX() {
        return userIdX;
    }

    public static void setUserIdX(String userIdX) {
        ImageHolder.userIdX = userIdX;
    }

    public static void setLikesOnProfileX(int likesOnProfile) {
        ImageHolder.likesOnProfile=likesOnProfile;
    }

    public static int getLikesOnProfile() {
        return likesOnProfile;
    }
}