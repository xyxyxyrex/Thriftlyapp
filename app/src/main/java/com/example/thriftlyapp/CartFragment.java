package com.example.thriftlyapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private CartViewModel cartViewModel;
    private CartAdapter cartAdapter;
    private TextView totalTextView;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);

        RecyclerView cartRecyclerView = rootView.findViewById(R.id.cartRecyclerView);
        totalTextView = rootView.findViewById(R.id.totalTextView);
        Button checkoutButton = rootView.findViewById(R.id.checkoutButton);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        cartRecyclerView.setLayoutManager(layoutManager);

        cartViewModel = new ViewModelProvider(requireActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()))
                .get(CartViewModel.class);

        cartAdapter = new CartAdapter(new ArrayList<>(), requireContext(), cartViewModel);
        cartRecyclerView.setAdapter(cartAdapter);

        cartAdapter.setOnRemoveItemClickListener(position -> removeItemFromCart(position));

        cartViewModel.cartItemsLiveData.observe(getViewLifecycleOwner(), storeItems -> {
            cartAdapter.setCartItems(storeItems);
            updateTotalPrice();
        });

        updateTotalPrice();

        checkoutButton.setOnClickListener(view -> {
            // Replace the current fragment with CheckoutFragment
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, CheckoutFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        });

        return rootView;
    }

    private void removeItemFromCart(int position) {
        List<StoreItem> currentItems = new ArrayList<>(cartViewModel.getCartItems());
        currentItems.remove(position);
        cartViewModel.cartItemsLiveData.setValue(currentItems);
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double totalPrice = 0;
        for (StoreItem item : cartViewModel.getCartItems()) {
            try {
                // Assuming getProductPrice() returns a currency-formatted string like "$19.99"
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
