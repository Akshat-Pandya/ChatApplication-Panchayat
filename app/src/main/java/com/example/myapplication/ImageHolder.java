package com.example.myapplication;

import android.graphics.drawable.Drawable;

public class ImageHolder {
    private static Drawable imageDrawable;

    public static void setImageDrawable(Drawable drawable) {
        imageDrawable = drawable;
    }

    public static Drawable getImageDrawable() {
        return imageDrawable;
    }
}