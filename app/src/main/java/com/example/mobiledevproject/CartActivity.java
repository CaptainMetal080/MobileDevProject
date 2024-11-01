package com.example.mobiledevproject;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FoodAdapter cartAdapter;
    private List<Food> cartItemList;
    private TextView subtotalTextView, deliveryFeeTextView, taxesTextView, totalTextView;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new DatabaseHelper(this);
        subtotalTextView = findViewById(R.id.subtotal);
        deliveryFeeTextView = findViewById(R.id.delivery);
        taxesTextView = findViewById(R.id.tax);
        totalTextView = findViewById(R.id.total);

        cartItemList = new ArrayList<>();
        cartAdapter = new FoodAdapter(this, cartItemList);
        recyclerView.setAdapter(cartAdapter);

        // Load cart items (this should be replaced with your actual cart loading logic)
        loadCartItems();
        updateSummary();
    }

    private void loadCartItems() {
        cartItemList.clear();
        Cursor cursor = dbHelper.getAllCartItems(); // This method should retrieve all items in the orders table

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_NAME));
                @SuppressLint("Range") byte[] image = cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_IMAGE));
                @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_PRICE));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_DESCRIPTION));
                @SuppressLint("Range") String restaurant = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_RESTAURANT_ORDER));

                cartItemList.add(new Food(name, image, price, description, restaurant, "", ""));
            } while (cursor.moveToNext());
            cursor.close();
        }
        cartAdapter.notifyDataSetChanged();
    }

    private void updateSummary() {
        double subtotal = calculateSubtotal();
        double deliveryFee = 5.00; // Example delivery fee
        double taxes = subtotal * 0.10; // Example tax calculation
        double total = subtotal + deliveryFee + taxes;

        subtotalTextView.setText("$" + String.format("%.2f", subtotal));
        deliveryFeeTextView.setText("$" + String.format("%.2f", deliveryFee));
        taxesTextView.setText("$" + String.format("%.2f", taxes));
        totalTextView.setText("$" + String.format("%.2f", total));
    }

    private double calculateSubtotal() {
        double subtotal = 0.0;
        for (Food item : cartItemList) {
            subtotal += item.getPrice();
        }
        return subtotal;
    }

    public void backButton(View v){
        finish();
    }
}