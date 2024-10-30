package com.example.mobiledevproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private Button btnDriver, btnCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize buttons
        btnDriver = findViewById(R.id.btn_driver);
        btnCustomer = findViewById(R.id.btn_customer);

        // Set onClickListeners
        btnDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch DriverActivity
                //Intent driverIntent = new Intent(Login.this, DriverActivity.class);
                //startActivity(driverIntent);
            }
        });

        btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch CustomerActivity
                Intent customerIntent = new Intent(Login.this, MainActivity.class);
                startActivity(customerIntent);
            }
        });
    }
}
