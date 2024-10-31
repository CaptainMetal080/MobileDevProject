package com.example.mobiledevproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "restaurants.db";
    private static final int DATABASE_VERSION = 2; // Increment version for upgrade

    public static final String TABLE_RESTAURANTS = "restaurants";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_LOGO = "logo"; // Assuming logo is stored as BLOB

    public static final String TABLE_FOODS = "foods"; // New table for foods
    public static final String COLUMN_FOOD_ID = "_id"; // Primary key for food items
    public static final String COLUMN_FOOD_NAME = "food_name"; // Food name
    public static final String COLUMN_FOOD_IMAGE = "food_image"; // Food image (BLOB)
    public static final String COLUMN_FOOD_PRICE = "food_price"; // Food price
    public static final String COLUMN_FOOD_DESCRIPTION = "food_description"; // Food description

    private static final String TABLE_CREATE_RESTAURANTS =
            "CREATE TABLE " + TABLE_RESTAURANTS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_LOGO + " BLOB);";

    private static final String TABLE_CREATE_FOODS =
            "CREATE TABLE " + TABLE_FOODS + " (" +
                    COLUMN_TITLE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FOOD_NAME + " TEXT, " +
                    COLUMN_FOOD_IMAGE + " BLOB, " +
                    COLUMN_FOOD_PRICE + " REAL, " + // Adding food price
                    COLUMN_FOOD_DESCRIPTION + " TEXT );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_RESTAURANTS);
        db.execSQL(TABLE_CREATE_FOODS); // Create foods table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOODS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS);
        onCreate(db);
    }


    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_FOODS);
        db.execSQL("DELETE FROM " + TABLE_RESTAURANTS);
        db.close();
    }
}
