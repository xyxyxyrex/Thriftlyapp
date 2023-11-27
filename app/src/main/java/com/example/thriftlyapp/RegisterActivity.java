package com.example.thriftlyapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

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
        signIn = findViewById(R.id.sign_in);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameText = username.getText().toString();
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
                String confirmPasswordText = confirmpass.getText().toString();
                String mobileNumberText = mobilenum.getText().toString();

                if (validateInputs(usernameText, emailText, passwordText, confirmPasswordText, mobileNumberText)) {
                    new RegisterTask().execute(usernameText, emailText, passwordText, confirmPasswordText, mobileNumberText);
                } else {
                    Toast.makeText(RegisterActivity.this, "Invalid input. Please check your inputs and try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInputs(String username, String email, String password, String confirmPassword, String mobileNumber) {
        // Add your input validation logic here
        return !username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty() && !mobileNumber.isEmpty();
    }

    private class RegisterTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String registerUrl = "http://192.168.55.178/thriftlyMobile/register.php";
                String registrationData = "username=" + params[0] +
                        "&email=" + params[1] +
                        "&password=" + params[2] +
                        "&confirm_password=" + params[3] +
                        "&mobile_number=" + params[4];
                return HttpUtility.sendHttpRequest(registerUrl, registrationData);
            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String response) {
            if (response != null) {
                if (response.equals("Registration successful")) {
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                } else if (response.equals("Passwords do not match")) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else if (response.equals("Error: Email already registered")) {
                    Toast.makeText(RegisterActivity.this, "Error: Email already registered", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Error: Registration failed - " + response, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegisterActivity.this, "Error: Null response received", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
