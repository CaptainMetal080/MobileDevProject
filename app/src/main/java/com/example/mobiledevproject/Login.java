package com.example.mobiledevproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private Button btnLogin, btnSignup;
    private EditText usernameInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize the buttons and EditText fields
        btnLogin = findViewById(R.id.btn_login);
        btnSignup = findViewById(R.id.btn_signup);
        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);

        // Set onClickListener for the Login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Capture input values (for display only)
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                // Display a simple message with the entered username
                Toast.makeText(Login.this, "Logged in as " + username, Toast.LENGTH_SHORT).show();

                // Launch MainActivity
                Intent mainIntent = new Intent(Login.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });

        // Set onClickListener for the Sign Up button
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch SignUpActivity (you can create this activity)
                Intent signupIntent = new Intent(Login.this, MainActivity.class);
                startActivity(signupIntent);
            }
        });
    }
}
