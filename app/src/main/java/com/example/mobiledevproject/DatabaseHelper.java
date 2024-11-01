package com.example.mobiledevproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Creation of database
    private static final String DATABASE_NAME = "restaurants.db";
    private static final int DATABASE_VERSION = 2; // Increment version for upgrade

    // Creation of restaurants table
    public static final String TABLE_RESTAURANTS = "restaurants";
    public static final String COLUMN_RESTAURANT_NAME = "restaurant_name"; // Restaurant name will be PK
    public static final String COLUMN_RESTAURANT_LONG = "restaurant_longitude"; // Restaurant's longitude on map
    public static final String COLUMN_RESTAURANT_LAT = "restaurant_latitude"; // Restaurant's latitude on map
    public static final String COLUMN_LOGO = "logo"; // Assuming logo is stored as BLOB

    // Creation of foods table
    public static final String TABLE_FOODS = "foods"; // New table for foods
    public static final String COLUMN_RESTAURANT_FOOD = "restaurant_food"; // Primary key for food table
    public static final String COLUMN_RESTAURANT = "restaurant"; // Restaurant name
    public static final String COLUMN_FOOD_NAME = "food_name"; // Food name
    public static final String COLUMN_FOOD_PRICE = "food_price"; // Food price
    public static final String COLUMN_FOOD_IMAGE = "food_image"; // Food image (BLOB)
    public static final String COLUMN_FOOD_DESCRIPTION = "food_description"; // Food description
    public static final String COLUMN_FOOD_TAGS = "food_tags"; // Food tags for searchability

    // Creation of order table
    public static final String TABLE_ORDERS = "orders"; // New table for orders
    public static final String COLUMN_ORDER_ID = "order_id"; // Primary key for orders
    public static final String COLUMN_RESTAURANT_ORDER = "restaurant_order"; // Restaurant name
    public static final String COLUMN_FOOD_ITEMS = "food_items"; // Food ordered
    public static final String COLUMN_TOTAL_PRICE = "total_price"; // Total price of order
    public static final String COLUMN_ETA = "ETA"; // Food ordered
    public static final String COLUMN_ORDER_DATE = "order_date"; // Date/time ordered

    // Query for creating the tables
    private static final String TABLE_CREATE_RESTAURANTS =
            "CREATE TABLE " + TABLE_RESTAURANTS + " (" +
                    COLUMN_RESTAURANT_NAME + " TEXT PRIMARY KEY, " +
                    COLUMN_RESTAURANT_LONG + " REAL, " +
                    COLUMN_RESTAURANT_LAT + " REAL, " +
                    COLUMN_LOGO + " BLOB);";

    private static final String TABLE_CREATE_FOODS =
            "CREATE TABLE " + TABLE_FOODS + " (" +
                    COLUMN_RESTAURANT_FOOD + " TEXT PRIMARY KEY, " +
                    COLUMN_RESTAURANT + " TEXT, " +
                    COLUMN_FOOD_NAME + " TEXT, " +
                    COLUMN_FOOD_PRICE + " REAL, " +
                    COLUMN_FOOD_IMAGE + " BLOB, " +
                    COLUMN_FOOD_DESCRIPTION + " TEXT, " +
                    COLUMN_FOOD_TAGS + " TEXT);";

    private static final String TABLE_CREATE_ORDERS =
            "CREATE TABLE " + TABLE_ORDERS + " (" +
                    COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_RESTAURANT_ORDER + " TEXT, " +
                    COLUMN_FOOD_ITEMS + " TEXT, " +
                    COLUMN_TOTAL_PRICE + " REAL, " +
                    COLUMN_ETA + " REAL, " +
                    COLUMN_ORDER_DATE + " TEXT); ";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_RESTAURANTS); // Create restaurants table
        db.execSQL(TABLE_CREATE_FOODS); // Create foods table
        db.execSQL(TABLE_CREATE_ORDERS); // Create orders table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOODS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }

    public Cursor retrieveFoodByTitle(String title) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_FOODS + " WHERE " + COLUMN_RESTAURANT + " = ?", new String[]{title});
    }
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_FOODS);
        db.execSQL("DELETE FROM " + TABLE_RESTAURANTS);
        db.execSQL("DELETE FROM " + TABLE_ORDERS);
        db.close();
    }
}
