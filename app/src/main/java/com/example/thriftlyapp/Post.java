package com.example.thriftlyapp;

public class Post {
    private String username;
    private String description;
    // Add other attributes as needed

    public Post(String username, String description) {
        this.username = username;
        this.description = description;
    }

    // Add getters and setters as needed
    public String getUsername() {
        return username;
    }

    public String getDescription() {
        return description;
    }
}