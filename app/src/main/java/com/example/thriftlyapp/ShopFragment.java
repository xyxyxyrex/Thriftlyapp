package com.example.thriftlyapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thriftlyapp.R;
import com.example.thriftlyapp.StoreAdapter;
import com.example.thriftlyapp.StoreItem;

import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends Fragment {

    private List<StoreItem> originalStoreItemList;
    private List<StoreItem> filteredStoreItemList;
    private StoreAdapter storeAdapter;
    private CartViewModel cartViewModel; // Add this line


    public ShopFragment() {
        // Required empty public constructor
    }

    public static ShopFragment newInstance() {
        return new ShopFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shop, container, false);

        // Find the RecyclerView, search EditText, and category Spinner
        RecyclerView storeRecyclerView = rootView.findViewById(R.id.storeRecyclerView);
        EditText searchEditText = rootView.findViewById(R.id.searchEditText);
        Spinner categorySpinner = rootView.findViewById(R.id.categorySpinner);

        // Create a grid layout manager with two columns
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);

        // Set the layout manager to your RecyclerView
        storeRecyclerView.setLayoutManager(layoutManager);

        // Fetch your actual product data and create a list of StoreItems
        originalStoreItemList = fetchStoreItemData();
        filteredStoreItemList = new ArrayList<>(originalStoreItemList); // Initialize with all items

        // Create and set the store adapter with dynamic data
        storeAdapter = new StoreAdapter(filteredStoreItemList, requireContext());
        storeRecyclerView.setAdapter(storeAdapter);

        // Set up the category Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // Initialize the CartViewModel
        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);

        // Set a default selection for the spinner

        // Set a TextWatcher on the search EditText for real-time filtering
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                filter(charSequence.toString(), categorySpinner.getSelectedItem().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // Set an OnItemSelectedListener on the category Spinner for filtering
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                filter(searchEditText.getText().toString(), categorySpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle the case when no item is selected
                Log.d("Spinner", "Nothing selected");
                Toast.makeText(parentView.getContext(), "Nothing selected", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up the store item click listener to add items to the cart
        storeAdapter.setOnItemClickListener(new StoreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                StoreItem selectedItem = filteredStoreItemList.get(position);

                // Add the selected item to the cart using the CartViewModel
                cartViewModel.addToCart(selectedItem);
                Toast.makeText(requireContext(), "Item added to cart", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    private void filter(String searchText, String selectedCategory) {
        // Clear the current list
        filteredStoreItemList.clear();

        // If the search text and category are empty, or "All" is selected, add all items to the filtered list
        if (TextUtils.isEmpty(searchText) &&
                (TextUtils.isEmpty(selectedCategory) || selectedCategory.equalsIgnoreCase("All"))) {
            filteredStoreItemList.addAll(originalStoreItemList);
        } else {
            // Filter items based on search text and category
            for (StoreItem item : originalStoreItemList) {
                boolean matchesSearchText = TextUtils.isEmpty(searchText) ||
                        item.getProductName().toLowerCase().contains(searchText.toLowerCase());

                boolean matchesCategory = TextUtils.isEmpty(selectedCategory) ||
                        selectedCategory.equalsIgnoreCase("All") ||
                        item.getCategory().equalsIgnoreCase(selectedCategory);

                if (matchesSearchText && matchesCategory) {
                    filteredStoreItemList.add(item);
                }
            }
        }

        // Notify the adapter that the data has changed
        storeAdapter.notifyDataSetChanged();
    }

    private List<StoreItem> fetchStoreItemData() {
        List<StoreItem> storeItemList = new ArrayList<>();

        // Add sample data (replace this with your actual data fetching logic)
        storeItemList.add(new StoreItem("Baseball Summer Cun Caps Casual Style", "₱199", R.drawable.hat1, "Hats", 1));
        storeItemList.add(new StoreItem("British Style Wool Fedoras Hat For Women Men Winter Autumn Warm Vintage Belt", "₱299", R.drawable.hat2, "Hats", 2));
        storeItemList.add(new StoreItem("Elegant Womens Organza Bucket Hat Purple with Flower Design", "₱399", R.drawable.hat3, "Hats", 3));

        storeItemList.add(new StoreItem("Jordan 1 Low Bredtoe", "₱999", R.drawable.shoes, "Shoes", 4));
        storeItemList.add(new StoreItem("Air Force 1 White", "₱899", R.drawable.shoes1, "Shoes", 5));
        storeItemList.add(new StoreItem("AirForce1 LV8 Utility", "₱799", R.drawable.shoes2, "Shoes", 6));
        storeItemList.add(new StoreItem("Jordan 1 Low Noble Red", "₱799", R.drawable.shoes4, "Shoes", 7));
        storeItemList.add(new StoreItem("Jordan 1 Low Travis Scott", "₱1099", R.drawable.shoes5, "Shoes", 8));

        storeItemList.add(new StoreItem("Sports Watch", "₱2499", R.drawable.accessories1, "Accessories", 9));
        storeItemList.add(new StoreItem("Sports Sunglasses", "₱1199", R.drawable.accessories2, "Accessories", 10));
        storeItemList.add(new StoreItem("Knee Pads", "₱899", R.drawable.accessories3, "Accessories", 11));

        storeItemList.add(new StoreItem("Sun Dress", "₱999", R.drawable.dress1, "Dress", 12));
        storeItemList.add(new StoreItem("Cocktail Dress", "₱899", R.drawable.dress2, "Dress", 13));
        storeItemList.add(new StoreItem("Maxi Dress", "₱799", R.drawable.dress3, "Dress", 14));
        storeItemList.add(new StoreItem("Classic Denim Jacket", "₱1899", R.drawable.jacket1, "Jackets", 15));
        storeItemList.add(new StoreItem("Stylish Leather Jacket", "₱2499", R.drawable.jacket2, "Jackets", 16));
        storeItemList.add(new StoreItem("Windbreaker", "₱2199", R.drawable.jacket3, "Jackets", 17));
        storeItemList.add(new StoreItem("Mom Jeans", "₱1349", R.drawable.jeans1, "Jeans", 18));
        storeItemList.add(new StoreItem("Boyfriend Jeans", "₱1249", R.drawable.jeans2, "Jeans", 19));
        storeItemList.add(new StoreItem("Skinny Jeans", "₱999", R.drawable.jeans3, "Jeans", 20));
        storeItemList.add(new StoreItem("Tennis Skirt and Top", "₱1599", R.drawable.sportswear1, "Sportswear", 21));
        storeItemList.add(new StoreItem("Running Shorts and Tank Top", "₱1799", R.drawable.sportswear2, "Sportswear", 22));
        storeItemList.add(new StoreItem("Basketball Jersey and Shorts", "₱1999", R.drawable.sportswear3, "Sportswear", 23));
        storeItemList.add(new StoreItem("Bikini", "₱699", R.drawable.swimwear1, "Swimwear", 24));
        storeItemList.add(new StoreItem("Swim Shorts", "₱599", R.drawable.swimwear2, "Swimwear", 25));
        storeItemList.add(new StoreItem("Wetsuit", "₱2999", R.drawable.swimwear3, "Swimwear", 26));
        storeItemList.add(new StoreItem("Trunks", "₱399", R.drawable.underwear1, "Underwear", 27));
        storeItemList.add(new StoreItem("Hipster Briefs", "₱499", R.drawable.underwear2, "Underwear", 28));
        storeItemList.add(new StoreItem("Briefs", "₱349", R.drawable.underwear3, "Underwear", 29));
        // Add more products as needed

        return storeItemList;
    }
}
