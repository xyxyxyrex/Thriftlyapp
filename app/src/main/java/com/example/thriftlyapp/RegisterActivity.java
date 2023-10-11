package com.example.thriftlyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    }
}
