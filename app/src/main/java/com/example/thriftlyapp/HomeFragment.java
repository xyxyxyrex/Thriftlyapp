package com.example.thriftlyapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        // Find the RecyclerView in your fragment's layout using rootView
        RecyclerView storiesRecyclerView = rootView.findViewById(R.id.storiesRecyclerView);

        // Create a layout manager (e.g., LinearLayoutManager for horizontal scrolling)
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        storiesRecyclerView.setLayoutManager(layoutManager);

        // Create and set the custom adapter
        StoryPlaceholderAdapter adapter = new StoryPlaceholderAdapter();
        storiesRecyclerView.setAdapter(adapter);

        return rootView;
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
