package com.example.thriftlyapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();

                if (validateInputs(usernameText, passwordText)) {
                    new LoginTask().execute(usernameText, passwordText);
                } else {
                    // Handle invalid input, for example, show an error message
                    Toast.makeText(LoginActivity.this, "Invalid input. Please check your inputs and try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInputs(String username, String password) {
        // Add your input validation logic here
        return !username.isEmpty() && !password.isEmpty();
    }

    private class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String loginUrl = "http://192.168.55.178/thriftlyMobile/login.php";
                String loginData = "username=" + params[0] +
                        "&password=" + params[1];
                return HttpUtility.sendHttpRequest(loginUrl, loginData);
            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String response) {
            Log.d("LoginActivity", "Server response: " + response);
            Log.d("LoginActivity", "Response length: " + response.length());

            if (response != null && response.contains("Login successful")) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } else {
                // Handle login failure, for example, show an error message
                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
