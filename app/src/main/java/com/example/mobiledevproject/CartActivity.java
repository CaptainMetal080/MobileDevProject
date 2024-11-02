package com.example.mobiledevproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteBlobTooBigException;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
            try {

            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_ITEM));
                @SuppressLint("Range") byte[] image = cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_IMAGE_CART));
                @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRICE));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_DESC_CART));
                @SuppressLint("Range") String restaurant = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_RESTAURANT_CART));
                @SuppressLint("Range") String concat = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_RESTAURANT_FOOD_CART));
                @SuppressLint("Range") String tag = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_TAGS_CART));
                @SuppressLint("Range") int quantity = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUANTITY)));

                cartItemList.add(new Food(name, image, price, description, restaurant, tag,concat ,quantity));
            } while (cursor.moveToNext());
            }
            catch (SQLiteBlobTooBigException e) {
                throw new RuntimeException(e);
            }
            finally {
                cursor.close();
            }
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

    // Method to handle checkout
    public void performCheckout(View view) {
        if (!cartItemList.isEmpty()) {
            // Clear the cart in the database
            dbHelper.deleteFromCart();
            // Clear the local cart item list
            cartItemList.clear();
            cartAdapter.notifyDataSetChanged();
            updateSummary();
            // Display a message to the user indicating checkout was successful
            // e.g., Toast.makeText(this, "Checkout successful!", Toast.LENGTH_SHORT).show();
        } else {
            // Notify user cart is empty
            // e.g., Toast.makeText(this, "Your cart is empty.", Toast.LENGTH_SHORT).show();
        }
    }
    public void Checkout(View view){
        if (!cartItemList.isEmpty()) {
            dbHelper.deleteFromCart();
            cartItemList.clear();
            Intent intent = new Intent(this, DeliveryTrackingActivity.class);
            startActivity(intent);
            cartAdapter.notifyDataSetChanged();
            updateSummary();
        } else {
            // Notify user cart is empty
            Toast.makeText(this, "Your cart is empty.", Toast.LENGTH_SHORT).show();
        }
    }
}
