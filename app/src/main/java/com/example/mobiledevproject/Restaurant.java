package com.example.mobiledevproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Restaurant {
    private String title;
    private byte[] logo;

    public Restaurant(String title, byte[] logo) {
        this.title = title;
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public byte[] getLogo() {
        return logo;
    }

    public Bitmap bytesToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
}
