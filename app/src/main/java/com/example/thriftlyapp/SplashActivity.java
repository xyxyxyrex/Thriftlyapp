package com.example.thriftlyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Navigate to LoginActivity after the delay
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);

                // Finish the current activity to prevent coming back to it
                finish();
            }
        }, 3000); // Delay of 3000 milliseconds (3 seconds)
    }
}
