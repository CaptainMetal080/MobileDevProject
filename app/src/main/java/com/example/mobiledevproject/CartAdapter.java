package com.example.mobiledevproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Food> foodList;
    private Context context;
    private DatabaseHelper dbHelper;

    public CartAdapter(Context context, List<Food> foodList) {
        this.foodList = foodList;
        this.context = context;
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_view, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.foodName.setText(food.getName());
        holder.foodPrice.setText(String.format("$%.2f", food.getPrice()));
        holder.foodImage.setImageBitmap(food.bytesToBitmap(food.getImage()));
        holder.foodDescription.setText(food.getDescription());
        holder.foodQuantity.setText("Quantity: " + food.getQuantity());

        // Add Button Logic
        holder.addButton.setOnClickListener(v -> {
            food.setQuantity(food.getQuantity() + 1); // Increase quantity by 1
            dbHelper.updateFoodQuantityCart(food.getConcat(), food.getQuantity()); // Update in DB
            notifyDataSetChanged();
            Toast.makeText(context, food.getName() + " quantity updated!", Toast.LENGTH_SHORT).show();
            updateCartSummary();
        });

        // Reduce Button Logic
        holder.reduceButton.setOnClickListener(v -> {
            if (food.getQuantity() > 1) {
                food.setQuantity(food.getQuantity() - 1); // Decrease quantity by 1
                dbHelper.updateFoodQuantityCart(food.getConcat(), food.getQuantity()); // Update in DB
                notifyDataSetChanged();
                Toast.makeText(context, food.getName() + " quantity reduced!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Quantity cannot be less than 1", Toast.LENGTH_SHORT).show();
            }
            updateCartSummary();
        });

        // Delete Button Logic
        holder.deleteButton.setOnClickListener(v -> {
            dbHelper.deleteFoodFromCart(food.getConcat()); // Delete from DB
            foodList.remove(position); // Remove item from the list
            notifyItemRemoved(position); // Update the RecyclerView
            notifyItemRangeChanged(position, foodList.size()); // Notify the rest of the list to update
            Toast.makeText(context, food.getName() + " removed from cart", Toast.LENGTH_SHORT).show();
            updateCartSummary();
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    private void updateCartSummary() {
        if (context instanceof CartActivity) {
            ((CartActivity) context).updateSummary();  // Call updateSummary from CartActivity to refresh totals
        }
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView foodName, foodPrice, foodDescription, foodQuantity;
        ImageView foodImage;
        Button addButton, reduceButton;
        ImageButton deleteButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.item_name);
            foodImage = itemView.findViewById(R.id.item_logo);
            foodDescription = itemView.findViewById(R.id.item_description);
            foodPrice = itemView.findViewById(R.id.item_price);
            foodQuantity = itemView.findViewById(R.id.quantity); // Added for quantity display
            addButton = itemView.findViewById(R.id.add_button); // Add button
            reduceButton = itemView.findViewById(R.id.reduce_button); // Reduce button
            deleteButton = itemView.findViewById(R.id.delete_button); // Delete button
        }
    }

}
