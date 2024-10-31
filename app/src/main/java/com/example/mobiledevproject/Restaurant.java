package com.example.mobiledevproject;

import android.graphics.Bitmap;
import java.util.List;

public class Restaurant {
    private String title;
    private Bitmap logo;

    public Restaurant(String title, Bitmap logo) {
        this.title = title;
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public Bitmap getLogo() {
        return logo;
    }
}
