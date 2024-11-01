package com.example.mobiledevproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<Food> cartItemList;
    private Context context;

    public CartAdapter(Context context, List<Food> cartItemList) {
        this.context = context;
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food item = cartItemList.get(position);
        holder.nameTextView.setText(item.getName());
        holder.foodImage.setImageBitmap(item.bytesToBitmap(item.getImage()));
        holder.priceTextView.setText("$" + String.format("%.2f", item.getPrice()));
        holder.foodDescription.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView priceTextView;
        ImageView foodImage;
        TextView foodDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.item_name);
            foodImage = itemView.findViewById(R.id.item_logo);
            foodDescription = itemView.findViewById(R.id.item_description);
            priceTextView = itemView.findViewById(R.id.item_price);

        }
    }
}
