package com.example.thriftlyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    Button signUp;
    TextView signIn;
    TextView username, email, password, confirmpass, mobilenum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signUp = findViewById(R.id.register_btn);
        username = findViewById(R.id.username_reg);
        email = findViewById(R.id.email_reg);
        password = findViewById(R.id.password_reg);
        confirmpass = findViewById(R.id.confirmpass_reg);
        mobilenum = findViewById(R.id.mobilenum_reg);
        signIn = findViewById(R.id.sign_in); // Initialize the signIn TextView

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start LoginActivity
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform registration
                String registerUrl = "http://192.168.55.178/thriftlyMobile/register.php";
                String registrationData = "username=" + username.getText().toString() +
                        "&email=" + email.getText().toString() +
                        "&password=" + password.getText().toString() +
                        "&confirm_password=" + confirmpass.getText().toString() +
                        "&mobile_number=" + mobilenum.getText().toString();
                String response = HttpUtility.sendHttpRequest(registerUrl, registrationData);

                // Process the response
                if (response.equals("Registration successful")) {
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                } else if (response.equals("Passwords do not match")) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else if (response.equals("Error: Email already registered")) {
                    Toast.makeText(RegisterActivity.this, "Error: Email already registered", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Error: Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
