package com.example.thriftlyapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder> {

    private List<StoreItem> cartItems;
    private TextView totalTextView; // Add this field

    // Update the constructor to accept a TextView
    public CheckoutAdapter(List<StoreItem> cartItems, TextView totalTextView) {
        this.cartItems = cartItems;
        this.totalTextView = totalTextView;
    }

    @NonNull
    @Override
    public CheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_item, parent, false);
        return new CheckoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutViewHolder holder, int position) {
        StoreItem item = cartItems.get(position);

        // Bind data to views in the ViewHolder
        holder.checkoutProductImageView.setImageResource(item.getProductImage());
        holder.productNameTextView.setText(item.getProductName());
        holder.productPriceTextView.setText(item.getProductPrice());
        holder.quantityTextView.setText(String.valueOf(item.getQuantity()));

        // Calculate and set the total price for each product multiplied by its quantity
        double totalPrice = calculateProductQuantityPrice(item);
        holder.totalPriceTextView.setText(String.format("₱%.2f", totalPrice));

        // Update the total price in the CheckoutFragment
        updateTotalPrice();
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    private double calculateProductQuantityPrice(StoreItem item) {
        try {
            String priceString = item.getProductPrice().replaceAll("[^\\d.]+", "");
            double price = Double.parseDouble(priceString);
            return price * item.getQuantity();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    private void updateTotalPrice() {
        double totalPrice = 0;
        for (StoreItem item : cartItems) {
            double productQuantityPrice = calculateProductQuantityPrice(item);
            totalPrice += productQuantityPrice;
        }
        totalTextView.setText(String.format("₱%.2f", totalPrice));
    }

    public static class CheckoutViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView;
        TextView productPriceTextView;
        TextView quantityTextView;
        TextView totalPriceTextView; // Add this TextView
        ImageView checkoutProductImageView;

        public CheckoutViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            checkoutProductImageView = itemView.findViewById(R.id.checkoutProductImageView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            totalPriceTextView = itemView.findViewById(R.id.totalPriceTextView); // Initialize the TextView
        }
    }
}
