package com.example.thriftlyapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private GestureDetector gestureDetector;
    private List<Post> postList; // Assuming you have a Post class representing your data
    private RecyclerView recyclerView;

    public PostAdapter(Context context, List<Post> postList, RecyclerView recyclerView) {
        this.postList = postList;
        this.recyclerView = recyclerView;


        // Initialize GestureDetector
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }


            @Override
            public boolean onDoubleTap(MotionEvent e) {
                // Toggle like state on double-tap
                View itemView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (itemView != null) {
                    ViewHolder holder = (ViewHolder) recyclerView.getChildViewHolder(itemView);
                    int position = holder.getAdapterPosition();
                    Post post = postList.get(position);
                    post.setLiked(!post.isLiked());
                    notifyItemChanged(position);
                    holder.heartImageView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.heart_beat));
                }
                return true;
            }






        });
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
        holder.pictureImageView.setImageResource(post.getPostImage());
        holder.profileImageView.setImageResource(post.getProfileImage());
        // Set other data to views...

        holder.heartImageView.setSelected(post.isLiked());
        updateHeartIcon(holder.heartImageView, post.isLiked());

        holder.heartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the state (clicked or not)
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    postList.get(adapterPosition).setLiked(!postList.get(adapterPosition).isLiked());
                    notifyItemChanged(adapterPosition);
                }
            }
        });

        holder.pictureImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
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

    private void updateHeartIcon(ImageView imageView, boolean isLiked) {
        Drawable drawable;
        if (isLiked) {
            drawable = ContextCompat.getDrawable(imageView.getContext(), R.drawable.baseline_favorite_24);
        } else {
            drawable = ContextCompat.getDrawable(imageView.getContext(), R.drawable.baseline_favorite_border_24);
        }
        imageView.setImageDrawable(drawable);
    }
}
