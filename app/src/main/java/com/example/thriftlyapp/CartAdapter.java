package com.example.thriftlyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<StoreItem> cartItemList;
    private Context context;
    private OnRemoveItemClickListener removeItemClickListener;
    private CartViewModel cartViewModel; // Added this variable

    public CartAdapter(List<StoreItem> cartItemList, Context context, CartViewModel cartViewModel) {
        this.cartItemList = cartItemList;
        this.context = context;
        this.cartViewModel = cartViewModel; // Initialize cartViewModel
    }

    public void setCartItems(List<StoreItem> cartItemList) {
        this.cartItemList = cartItemList;
        notifyDataSetChanged();
    }

    public interface OnRemoveItemClickListener {
        void onRemoveItemClick(int position);
    }

    public void setOnRemoveItemClickListener(OnRemoveItemClickListener listener) {
        this.removeItemClickListener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        StoreItem item = cartItemList.get(position);

        // Update views with item details
        holder.cartProductImageView.setImageResource(item.getProductImage());
        holder.cartProductNameTextView.setText(item.getProductName());
        holder.cartProductPriceTextView.setText(item.getProductPrice());

        // Set quantity
        holder.quantityEditText.setText(String.valueOf(item.getQuantity()));

        // Increase quantity
        holder.increaseButton.setOnClickListener(view -> {
            if (cartViewModel != null) {
                cartViewModel.increaseQuantity(position);
            }
        });

        // Decrease quantity
        holder.decreaseButton.setOnClickListener(view -> {
            int newQuantity = Math.max(0, item.getQuantity() - 1);
            item.setQuantity(newQuantity);
            holder.quantityEditText.setText(String.valueOf(newQuantity));
        });

        // Remove item
        holder.removeButton.setOnClickListener(view -> {
            if (removeItemClickListener != null) {
                removeItemClickListener.onRemoveItemClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView cartProductImageView;
        TextView cartProductNameTextView;
        TextView cartProductPriceTextView;
        EditText quantityEditText;
        ImageButton increaseButton;
        ImageButton decreaseButton;
        ImageButton removeButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            cartProductImageView = itemView.findViewById(R.id.cartProductImageView);
            cartProductNameTextView = itemView.findViewById(R.id.cartProductNameTextView);
            cartProductPriceTextView = itemView.findViewById(R.id.cartProductPriceTextView);
            quantityEditText = itemView.findViewById(R.id.quantityEditText);
            increaseButton = itemView.findViewById(R.id.increaseButton);
            decreaseButton = itemView.findViewById(R.id.decreaseButton);
            removeButton = itemView.findViewById(R.id.removeButton);
        }
    }
}
