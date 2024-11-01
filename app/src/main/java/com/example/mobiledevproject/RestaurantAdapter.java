package com.example.mobiledevproject;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<Restaurant> restaurantList;
    private Context context;

    public RestaurantAdapter(Context context, List<Restaurant> restaurantList) {
        this.context = context; // Initialize context
        this.restaurantList = restaurantList;
    }
    public RestaurantAdapter(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
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
        holder.logoImageView.setImageBitmap(restaurant.bytesToBitmap(restaurant.getLogo()));
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
    public void insertData() throws IOException {
        // Example data

        @SuppressLint("UseCompatLoadingForDrawables") byte[] pasta = bitmapToByte(context.getResources().getDrawable(R.drawable.logo1));

        Bitmap logo1 = BitmapFactory.decodeStream(context.getAssets().open("pasta.png"));
        Bitmap logo2 = BitmapFactory.decodeStream(context.getAssets().open("pasta.png"));

        Restaurant restaurant1 = new Restaurant("MCDS", pasta);
        Restaurant restaurant2 = new Restaurant("Restaurant 2", pasta);

        restaurantList.add(restaurant1);
        restaurantList.add(restaurant2);

        notifyDataSetChanged(); // Notify adapter of data changes
    }

    public byte[] bitmapToByte(Drawable image){
        // Convert the Drawable image to a Bitmap
        Bitmap bitmap = ((BitmapDrawable)image).getBitmap();

        // Convert the Bitmap to a byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();

        return bitmapdata;
    }
}
