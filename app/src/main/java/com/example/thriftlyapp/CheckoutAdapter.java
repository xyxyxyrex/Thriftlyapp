package com.example.thriftlyapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder> {

    private List<StoreItem> cartItems;

    public CheckoutAdapter(List<StoreItem> cartItems) {
        this.cartItems = cartItems;
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
        holder.productNameTextView.setText(item.getProductName());
        holder.productPriceTextView.setText(item.getProductPrice());
        holder.quantityTextView.setText(String.valueOf(item.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CheckoutViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView;
        TextView productPriceTextView;
        TextView quantityTextView;

        public CheckoutViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
        }
    }
}
