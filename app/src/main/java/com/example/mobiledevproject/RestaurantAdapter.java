package com.example.mobiledevproject;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
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
        holder.logoImageView.setImageBitmap(restaurant.getLogo());
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
        Restaurant restaurant1 = new Restaurant("MCDS", BitmapFactory.decodeResource(context.getResources(), R.drawable.logo1));
        Restaurant restaurant2 = new Restaurant("Restaurant 2", BitmapFactory.decodeResource(context.getResources(), R.drawable.logo2));

        restaurantList.add(restaurant1);
        restaurantList.add(restaurant2);

        notifyDataSetChanged(); // Notify adapter of data changes
    }


}
