package com.example.mobiledevproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<Restaurant> restaurantList;
    private Context context;

    public RestaurantAdapter(Context context, List<Restaurant> restaurantList) {
        // Initialize context
        this.restaurantList = restaurantList;
        this.context=context;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_restaurant_view, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.titleTextView.setText(restaurant.getTitle());
        holder.logoImageView.setImageBitmap(bytesToBitmap(restaurant.getLogo()));
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView logoImageView;

        RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.restaurant_title);
            logoImageView = itemView.findViewById(R.id.restaurant_logo);
        }
    }

    public void insertData() {
        // Example data
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.logo1); // Use ContextCompat
        byte[] logoBytes = bitmapToByte(drawable);

        Restaurant restaurant1 = new Restaurant("MCDS", logoBytes);
        Restaurant restaurant2 = new Restaurant("Restaurant 2", logoBytes);

        restaurantList.add(restaurant1);
        restaurantList.add(restaurant2);

        notifyDataSetChanged(); // Notify adapter of data changes
    }

    public byte[] bitmapToByte(Drawable image) {
        Bitmap bitmap;

        // Convert the Drawable image to a Bitmap
        if (image instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) image).getBitmap();
        } else {
            bitmap = Bitmap.createBitmap(image.getIntrinsicWidth(), image.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            image.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            image.draw(canvas);
        }

        // Convert the Bitmap to a byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    public Bitmap bytesToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
}
