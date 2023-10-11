package com.example.thriftlyapp;

public class StoreItem {
    private String productName;
    private String productPrice;
    private int productImage;
    private String category;
    private int quantity;
    private int productId;  // Added productId field

    // Constructor

    public StoreItem(String productName, String productPrice, int productImage, String category, int productId) {
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.category = category;
        this.productId = productId;
        this.quantity = 1;  // Default quantity is 1
    }

    // Getter methods

    public int getProductImage() {
        return productImage;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }
}
