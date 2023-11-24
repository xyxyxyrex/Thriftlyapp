package com.example.thriftlyapp;

public class Post {
    private String username;
    private String description;
    private int profileImage;
    private int postImage;
    private boolean liked;

    // Constructor without 'liked' parameter
    public Post(String username, String description, int postImage) {
        this.username = username;
        this.description = description;
        this.postImage = postImage;
        // Assuming you want to initialize profileImage to some default value
        this.profileImage = R.drawable.appicon; // Change this to the appropriate default image
        this.liked = false; // Default value for 'liked'
    }

    // Constructor with 'liked' parameter
    public Post(String username, String description, int postImage, int profileImage, boolean liked) {
        this.username = username;
        this.description = description;
        this.postImage = postImage;
        this.profileImage = profileImage;
        this.liked = liked;
    }

    // Rest of the class remains unchanged...

    // Add getters and setters as needed
    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String getUsername() {
        return username;
    }

    public String getDescription() {
        return description;
    }

    public int getPostImage() {
        return postImage;
    }

    public int getProfileImage() {
        return profileImage;
    }
}
