package com.example.mobiledevproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private List<Restaurant> restaurantList;

    // Declare ActivityResultLauncher
    private ActivityResultLauncher<Intent> addRestaurantLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerview);
        restaurantList = new ArrayList<>();
        adapter = new RestaurantAdapter(restaurantList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Initialize ActivityResultLauncher
        addRestaurantLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        loadRestaurants(); // Reload restaurants if a new one was added
                    }
                }
        );

        Button buttonAddRestaurant = findViewById(R.id.button);
        buttonAddRestaurant.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            addRestaurantLauncher.launch(intent); // Use ActivityResultLauncher
        });

        loadRestaurants();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRestaurants(); // Reload restaurants when coming back from the add activity
    }

    private void loadRestaurants() {
        restaurantList.clear();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DatabaseHelper.TABLE_RESTAURANTS,
                null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE));
            @SuppressLint("Range") byte[] logoBytes = cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.COLUMN_LOGO));
            Bitmap logo = BitmapFactory.decodeByteArray(logoBytes, 0, logoBytes.length);

            // Load food items for the restaurant

            restaurantList.add(new Restaurant(title, logo));
        }
        cursor.close();
        db.close();

        adapter.notifyDataSetChanged();
    }

    public void Cart(View view) {

    }
}
