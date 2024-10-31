package com.example.mobiledevproject;

import android.graphics.Bitmap;
import java.io.Serializable;

public class Food implements Serializable {
    private String name;
    private Bitmap image;
    private double price;
    private String description;

    public Food(String name, Bitmap image, double price, String description) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Bitmap getImage() {
        return image;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }


}
