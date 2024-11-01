package com.example.mobiledevproject;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private List<Food> foodList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_page); // Use your activity's layout XML

        recyclerView = findViewById(R.id.restraurant_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);
        foodList = new ArrayList<>();
        loadFoodsFromDatabase();

        foodAdapter = new FoodAdapter(this ,foodList);
        recyclerView.setAdapter(foodAdapter);
    }

    private void loadFoodsFromDatabase() {
        Cursor cursor = databaseHelper.getReadableDatabase().query(DatabaseHelper.TABLE_FOODS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_NAME));
                @SuppressLint("Range") byte[] image = cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_IMAGE));
                @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_PRICE));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_DESCRIPTION));
                foodList.add(new Food(name, image, price, description));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}
