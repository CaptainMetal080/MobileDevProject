package com.example.mobiledevproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Signup extends AppCompatActivity {

    private Button btnLogin, btnSignup;
    private EditText usernameInput, emailInput, passwordInput, confirmPasswordInput;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        dbHelper = new DatabaseHelper(this);

        // Initialize the buttons and EditText fields
        btnLogin = findViewById(R.id.login);
        btnSignup = findViewById(R.id.signup);
        usernameInput = findViewById(R.id.username_input);
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        confirmPasswordInput = findViewById(R.id.confirm_password_input);

        // Set onClickListener for the Login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go back to login
                Intent i = new Intent(Signup.this, Login.class);
                startActivity(i);
                finish();
            }
        });

        // Set onClickListener for the Sign Up button
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add new user to database
                String username = usernameInput.getText().toString().trim();
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();
                String confirmPassword = confirmPasswordInput.getText().toString().trim();

                // check if all fields filled
                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Signup.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!password.equals(confirmPassword)) { // check if passwords match
                    Toast.makeText(Signup.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                dbHelper.addUser(username, email, password);
                Toast.makeText(Signup.this, "User added successfully", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Signup.this, Login.class);
                startActivity(i);
                finish();
            }
        });
    }
}
