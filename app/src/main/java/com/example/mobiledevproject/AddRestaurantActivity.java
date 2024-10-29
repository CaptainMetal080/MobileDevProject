package com.example.mobiledevproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class AddRestaurantActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private EditText editTextTitle;
    private ImageView imageViewLogo;
    private Bitmap logoBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_restaurant);

        editTextTitle = findViewById(R.id.editTextTitle);
        imageViewLogo = findViewById(R.id.imageViewLogo);
        Button buttonChooseLogo = findViewById(R.id.buttonChooseLogo);
        Button buttonAddRestaurant = findViewById(R.id.buttonAddRestaurant);

        buttonChooseLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        buttonAddRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRestaurant();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                logoBitmap = BitmapFactory.decodeStream(inputStream);
                imageViewLogo.setImageBitmap(logoBitmap);
                imageViewLogo.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addRestaurant() {
        String title = editTextTitle.getText().toString();
        if (title.isEmpty() || logoBitmap == null) {
            return; // Handle error
        }

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TITLE, title);
        values.put(DatabaseHelper.COLUMN_LOGO, bitmapToByteArray(logoBitmap));

        db.insert(DatabaseHelper.TABLE_RESTAURANTS, null, values);
        db.close();

        // Clear inputs
        editTextTitle.setText("");
        imageViewLogo.setVisibility(View.GONE);
        logoBitmap = null;

        // Go back to the main activity
        Intent intent = new Intent(AddRestaurantActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Close this activity
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
