package com.example.thriftlyapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtility {

    public static String sendHttpRequest(String urlString, String postData) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            // Send POST data
            OutputStream os = urlConnection.getOutputStream();
            os.write(postData.getBytes());
            os.flush();
            os.close();

            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line).append('\n');
            }
            reader.close();

            // Return the response
            return response.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null; // Handle the error appropriately in your app
        }
    }
}
