package com.example.mobiledevproject;

import android.content.Context;
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

import java.io.ByteArrayOutputStream;
import java.text.BreakIterator;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private List<Food> foodList;
    Context context;

    public FoodAdapter(Context context, List<Food> foodList) {

        this.foodList = foodList;
        this.context=context;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_food_item, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.foodName.setText(food.getName());
        holder.foodImage.setImageBitmap(food.bytesToBitmap(food.getImage()));
        holder.foodPrice.setText(String.format("$%.2f", food.getPrice())); // Format price
        holder.foodDescription.setText(food.getDescription());
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView foodPrice;
        TextView foodDescription;
        TextView foodName;
        ImageView foodImage;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.item_name);
            foodImage = itemView.findViewById(R.id.item_logo);
            foodDescription = itemView.findViewById(R.id.item_description);
            foodPrice = itemView.findViewById(R.id.item_price);
        }
    }

    public void insertData() {
        // Example data
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.logo1); // Use ContextCompat
        byte[] logoBytes = bitmapToByte(drawable);

        //String name, byte[] image, double price, String description, String restaurant,String tag
        Food food1 = new Food("burger", logoBytes,20.66,"very tasty food","MCDS","burger, meatbased, halal, fastfood");

        foodList.add(food1);

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
