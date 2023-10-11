package com.example.thriftlyapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> postList; // Assuming you have a Post class representing your data

    public PostAdapter(List<Post> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data to the views for each item
        Post post = postList.get(position);
        holder.usernameTextView.setText(post.getUsername());
        holder.descriptionTextView.setText(post.getDescription());
        // Set other data to views...
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        // Define views within cardview_item.xml and initialize them here
        ImageView profileImageView;
        TextView usernameTextView;
        ImageView pictureImageView;
        TextView descriptionTextView;
        ImageView heartImageView;
        ImageView shareImageView;

        ViewHolder(View itemView) {
            super(itemView);
            // Initialize views
            profileImageView = itemView.findViewById(R.id.profileImageView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            pictureImageView = itemView.findViewById(R.id.pictureImageView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            heartImageView = itemView.findViewById(R.id.heartImageView);
            shareImageView = itemView.findViewById(R.id.shareImageView);
        }
    }
}
