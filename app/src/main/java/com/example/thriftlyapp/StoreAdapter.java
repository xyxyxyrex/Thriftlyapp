package com.example.thriftlyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

    private List<StoreItem> storeItemList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public StoreAdapter(List<StoreItem> storeItemList, Context context) {
        this.storeItemList = storeItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StoreItem storeItem = storeItemList.get(position);

        holder.productImageView.setImageResource(storeItem.getProductImage());
        holder.productNameTextView.setText(storeItem.getProductName());
        holder.productPriceTextView.setText(storeItem.getProductPrice());

        // Set a click listener for the entire item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });

        // Set a click listener for the "Add to Cart" button
        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the "Add to Cart" button click
                // You can implement the logic to add the item to the cart here
            }
        });
    }

    @Override
    public int getItemCount() {
        return storeItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImageView;
        TextView productNameTextView;
        TextView productPriceTextView;
        Button addToCartButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.productImageView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
