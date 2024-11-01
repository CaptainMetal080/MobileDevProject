package com.example.mobiledevproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
public class RestaurantActivity extends AppCompatActivity {
    private TextView text;
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private List<Food> foodList;
    private DatabaseHelper databaseHelper;
    public String restaurantTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_page);
        restaurantTitle = getIntent().getStringExtra("TITLE");

        // Get the restaurant title from the intent
        text=findViewById(R.id.textView);
        recyclerView = findViewById(R.id.restraurant_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        text.setText(restaurantTitle);
        databaseHelper = new DatabaseHelper(this);
        foodList = new ArrayList<>();

        foodAdapter = new FoodAdapter(this, foodList);
        foodList.clear();

        recyclerView.setAdapter(foodAdapter);

        // Load food items for the specific restaurant
        loadFoodsFromDatabase();
    }

    private void loadFoodsFromDatabase() {
        foodList.clear();
        foodAdapter.insertData(restaurantTitle);

        Cursor cursor=databaseHelper.retrieveFoodByTitle(restaurantTitle);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_NAME));
                @SuppressLint("Range") byte[] image = cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_IMAGE));
                @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_PRICE));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_DESCRIPTION));
                @SuppressLint("Range") String restaurant = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_RESTAURANT));
                @SuppressLint("Range") String tag = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_TAGS));
                @SuppressLint("Range") String concat = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_RESTAURANT_NAME));

                foodList.add(new Food(name, image, price, description, restaurant, tag, concat));
            } while (cursor.moveToNext());
            cursor.close();
        }
        foodAdapter.notifyDataSetChanged();

    }

    public void backButton(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
