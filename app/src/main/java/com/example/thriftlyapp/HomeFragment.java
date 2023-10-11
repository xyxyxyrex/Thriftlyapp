package com.example.thriftlyapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Find the RecyclerViews in your fragment's layout using rootView
        RecyclerView storiesRecyclerView = rootView.findViewById(R.id.storiesRecyclerView);
        RecyclerView postsRecyclerView = rootView.findViewById(R.id.postsRecyclerView);

        // Create layout managers
        LinearLayoutManager storiesLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager postsLayoutManager = new LinearLayoutManager(requireContext());

        // Set layout managers to RecyclerViews
        storiesRecyclerView.setLayoutManager(storiesLayoutManager);
        postsRecyclerView.setLayoutManager(postsLayoutManager);

        // Create and set the story placeholder adapter
        StoryPlaceholderAdapter storyAdapter = new StoryPlaceholderAdapter();
        storiesRecyclerView.setAdapter(storyAdapter);

        // Fetch your actual post data and create a list of posts
        List<Post> postList = fetchPostData();

        // Create and set the post adapter with dynamic data
        PostAdapter postAdapter = new PostAdapter(postList);
        postsRecyclerView.setAdapter(postAdapter);

        // Inside onCreateView method in HomeFragment.java
        postsRecyclerView.setAdapter(postAdapter);
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        return rootView;


    }
    private List<Post> fetchPostData() {
        List<Post> postData = new ArrayList<>();

        // Add sample data (replace this with your actual data fetching logic)
        postData.add(new Post("User1", "Description 1"));
        postData.add(new Post("User2", "Description 2"));
        postData.add(new Post("User2", "Description 2"));
        postData.add(new Post("User2", "Description 2"));
        postData.add(new Post("User2", "Description 2"));
        postData.add(new Post("User2", "Description 2"));
        postData.add(new Post("User2", "Description 2"));
        // Add more posts as needed

        return postData;
    }

    public class StoryPlaceholderAdapter extends RecyclerView.Adapter<StoryPlaceholderAdapter.ViewHolder> {
        private static final int NUM_PLACEHOLDERS = 10; // Adjust the number of placeholders as needed

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_placeholder_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            // Customize the appearance of the circular placeholders (e.g., background color, size, etc.)
        }

        @Override
        public int getItemCount() {
            return NUM_PLACEHOLDERS;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ViewHolder(View itemView) {
                super(itemView);
                // Initialize and customize the circular placeholder view (e.g., set background, size)
            }
        }
    }
}