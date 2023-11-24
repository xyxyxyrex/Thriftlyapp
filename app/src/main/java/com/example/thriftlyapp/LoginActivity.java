package com.example.thriftlyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button signIn;
    EditText username, password;
    TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signIn = findViewById(R.id.login_btn);
        username = findViewById(R.id.username_login);
        password = findViewById(R.id.password_login);
        signUp = findViewById(R.id.sign_up);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform login
                String loginUrl = "http://localhost/phpmyadmin/thriftlyMobile/login.php";
                String loginData = "username=" + username.getText().toString() +
                        "&password=" + password.getText().toString();
                String response = HttpUtility.sendHttpRequest(loginUrl, loginData);

                // Process the response
                if (response != null && response.equals("Login successful")) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    // Handle login failure, for example, show an error message
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
