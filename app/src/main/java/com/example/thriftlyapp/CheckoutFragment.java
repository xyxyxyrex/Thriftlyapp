package com.example.thriftlyapp;

import static android.util.Log.e;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thriftlyapp.CartViewModel;

import java.util.List;

public class CheckoutFragment extends Fragment {

    private CartViewModel cartViewModel;
    private RecyclerView checkoutRecyclerView;
    private TextView totalTextView;

    public CheckoutFragment() {
        // Required empty public constructor
    }

    public static CheckoutFragment newInstance() {
        return new CheckoutFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_checkout, container, false);

        checkoutRecyclerView = rootView.findViewById(R.id.checkoutRecyclerView);
        totalTextView = rootView.findViewById(R.id.totalTextView);

        // Initialize CartViewModel
        cartViewModel = new ViewModelProvider(requireActivity())
                .get(CartViewModel.class);

        // Set up RecyclerView
        CheckoutAdapter checkoutAdapter = new CheckoutAdapter(cartViewModel.getCartItems(), totalTextView);
        checkoutRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        checkoutRecyclerView.setAdapter(checkoutAdapter);

        // Display the total price
        updateTotalPrice();

        return rootView;
    }

    private void updateTotalPrice() {
        double totalPrice = 0;
        List<StoreItem> cartItems = cartViewModel.getCartItems();

        for (StoreItem item : cartItems) {
            try {
                String priceString = item.getProductPrice().replaceAll("[^\\d.]+", "");
                double price = Double.parseDouble(priceString);
                totalPrice += price * item.getQuantity();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        totalTextView.setText(getString(R.string.total_price, String.format("₱%.2f", totalPrice)));
    }
}
