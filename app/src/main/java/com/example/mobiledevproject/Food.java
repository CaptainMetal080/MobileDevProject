package com.example.mobiledevproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;

public class Food implements Serializable {
    private String name;
    private byte[] image;
    private double price;
    private String description;
    private String restaurant;
    private String tag;
    private String concat;

    public Food(String name, byte[] image, double price, String description, String restaurant, String tag, String concat) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.restaurant=restaurant;
        this.tag=tag;
        this.concat=concat;
    }

    public String getName() {
        return name;
    }

    public byte[] getImage() {
        return image;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
    public String getRestaurant(){
        return  restaurant;
    }
    public String getTag(){
        return tag;
    }

    public String getConcat(){
        if(!concat.isEmpty()) {
            return concat;
        }
        else{return  restaurant+name;
        }
    }

    public Bitmap bytesToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }


}
