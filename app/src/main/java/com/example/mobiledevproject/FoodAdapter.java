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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private List<Food> foodList;
    Context context;
    private DatabaseHelper dbHelper;


    public FoodAdapter(Context context, List<Food> foodList) {

        this.foodList = foodList;
        this.context=context;
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_view, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.foodName.setText(food.getName());
        holder.foodImage.setImageBitmap(food.bytesToBitmap(food.getImage()));
        holder.foodPrice.setText(String.format("$%.2f", food.getPrice())); // Format price
        holder.foodDescription.setText(food.getDescription());

        holder.itemView.setOnClickListener(v -> {
            dbHelper.addFoodToCart(food.getRestaurant(),food.getName(), food.getPrice(), food.getImage(), food.getDescription(), food.getTag(),food.getConcat(), 1);
            Toast.makeText(context, food.getName() + " added to cart!", Toast.LENGTH_SHORT).show();
        });
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

    public void insertData(String restaurant_title) {

        if(Objects.equals(restaurant_title, "Aldenaire")) {
            Drawable drawable1 = ContextCompat.getDrawable(context, R.drawable.aldenairesteak); // Use ContextCompat
            byte[] logoBytes1 = bitmapToByte(drawable1);
            Food food1 = new Food("SirLoin Streak", logoBytes1, 38.66, "Well-seasoned Steak cooked to the customer's desire", "Aldenaire", "steak, meat, cuisine", "AldenaireSteak",1);

            Drawable drawable2 = ContextCompat.getDrawable(context, R.drawable.aldenairepasta); // Use ContextCompat
            byte[] logoBytes2 = bitmapToByte(drawable2);
            Food food2 = new Food("Alfredo Pasta", logoBytes2, 18.99, "Italian style pasta with a creamy alfredo sauce", "Aldenaire", "pasta, italian, cuisine", "AldenairePasta",1);

            Drawable drawable3 = ContextCompat.getDrawable(context, R.drawable.aldenaireescargot); // Use ContextCompat
            byte[] logoBytes3 = bitmapToByte(drawable3);
            Food food3 = new Food("Escargot", logoBytes3, 23.34, "Escargot, or cooked snails, are a beloved French delicacy, served with a special sauce", "Aldenaire", "french, cuisine", "AldenaireEscargot",1);


            foodList.add(food1);
            foodList.add(food2);
            foodList.add(food3);

        } else if (Objects.equals(restaurant_title, "Thynk Cafe")) {
            Drawable drawable1 = ContextCompat.getDrawable(context, R.drawable.thynklatte); // Use ContextCompat
            byte[] logoBytes1 = bitmapToByte(drawable1);
            Food food1 = new Food("Thynk Latte", logoBytes1, 4.99, "Get a taste of the season with our seasonal special latte", "Thynk Cafe", "coffee, latte, cafe", "ThynkCafeLatte",1);

            Drawable drawable2 = ContextCompat.getDrawable(context, R.drawable.thynkcookie); // Use ContextCompat
            byte[] logoBytes2 = bitmapToByte(drawable2);
            Food food2 = new Food("Chocolate Chip Cookie", logoBytes2, 1.99, "Our customer favourite, the original chocolate chip cookie", "Thynk Cafe", "cookie, dessert, cafe", "ThynkCafeCookie",1);

            Drawable drawable3 = ContextCompat.getDrawable(context, R.drawable.thynkbrownie); // Use ContextCompat
            byte[] logoBytes3 = bitmapToByte(drawable3);
            Food food3 = new Food("Brownie", logoBytes3, 3.58, "Fudgy brownies made with high quality chocolate", "Thynk Cafe", "chocolate, dessert, cafe", "ThynkCafeBrownie",1);

            foodList.add(food1);
            foodList.add(food2);
            foodList.add(food3);

        }else if (Objects.equals(restaurant_title, "the Tree")) {
            Drawable drawable1 = ContextCompat.getDrawable(context, R.drawable.thetreekaraage); // Use ContextCompat
            byte[] logoBytes1 = bitmapToByte(drawable1);
            Food food1 = new Food("Chicken Karaage", logoBytes1, 14.99, "Crispy double fried japanese style chicken with a side of special sauces", "the Tree", "chicken, japanese", "TheTreeKaraage",1);

            Drawable drawable2 = ContextCompat.getDrawable(context, R.drawable.thetreesushi); // Use ContextCompat
            byte[] logoBytes2 = bitmapToByte(drawable2);
            Food food2 = new Food("Sushi Platter", logoBytes2, 62.99, "A platter of all tastes of the sea", "the Tree", "sushi, seafood, japanese", "TheTreeSushi",1);

            Drawable drawable3 = ContextCompat.getDrawable(context, R.drawable.thetreecheese); // Use ContextCompat
            byte[] logoBytes3 = bitmapToByte(drawable3);
            Food food3 = new Food("Japanese Cheesecake", logoBytes3, 5.99, "Creamy milky sweet cheese cakes, a treat for your day", "the Tree", "cheese, dessert, japanese", "TheTreeCake",1);

            foodList.add(food1);
            foodList.add(food2);
            foodList.add(food3);

        }else if (Objects.equals(restaurant_title, "Street Food Spencers")) {
            Drawable drawable1 = ContextCompat.getDrawable(context, R.drawable.sfsburger); // Use ContextCompat
            byte[] logoBytes1 = bitmapToByte(drawable1);
            Food food1 = new Food("Burglar Burger", logoBytes1, 6.99, "a juicy burger that'll steal you away", "Street Food Spencers", "burger, meat, fastfood", "SFSBurger",1);

            Drawable drawable2 = ContextCompat.getDrawable(context, R.drawable.sfschoppedcheese); // Use ContextCompat
            byte[] logoBytes2 = bitmapToByte(drawable2);
            Food food2 = new Food("Greasy peter", logoBytes2, 12.99, "the greasy chopped cheese sandwich! only for those who dare", "Street Food Spencers", "sandwich, meat, fastfood", "SFSGreesy",1);

            Drawable drawable3 = ContextCompat.getDrawable(context, R.drawable.sfsfries); // Use ContextCompat
            byte[] logoBytes3 = bitmapToByte(drawable3);
            Food food3 = new Food("The Fries", logoBytes3, 4.57, "Fries! crispy fries!", "Street Food Spencers", "fries, fastfood", "SFSFries",1);

            foodList.add(food1);
            foodList.add(food2);
            foodList.add(food3);

        }

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
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        return stream.toByteArray();
    }

    public Bitmap bytesToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

}
