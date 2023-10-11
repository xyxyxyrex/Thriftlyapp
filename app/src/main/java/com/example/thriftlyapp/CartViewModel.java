package com.example.thriftlyapp;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartViewModel extends ViewModel {
    private MutableLiveData<List<StoreItem>> _cartItemsLiveData = new MutableLiveData<>();
    public MutableLiveData<List<StoreItem>> cartItemsLiveData = _cartItemsLiveData;

    public CartViewModel() {
        _cartItemsLiveData.setValue(new ArrayList<>());
    }

    public void addToCart(StoreItem item) {
        List<StoreItem> currentItems = new ArrayList<>(_cartItemsLiveData.getValue());
        currentItems.add(item);
        _cartItemsLiveData.setValue(currentItems);
        Log.d("CartViewModel", "Item added to cart: " + item.getProductName());
    }

    public void increaseQuantity(int position) {
        List<StoreItem> currentItems = new ArrayList<>(_cartItemsLiveData.getValue());
        if (position >= 0 && position < currentItems.size()) {
            currentItems.get(position).setQuantity(currentItems.get(position).getQuantity() + 1);
            _cartItemsLiveData.setValue(currentItems);
        }
    }

    public void removeFromCart(StoreItem item) {
        List<StoreItem> currentItems = new ArrayList<>(_cartItemsLiveData.getValue());
        currentItems.remove(item);
        _cartItemsLiveData.setValue(currentItems);
    }

    public List<StoreItem> getCartItems() {
        List<StoreItem> currentItems = _cartItemsLiveData.getValue();
        return currentItems != null ? new ArrayList<>(currentItems) : Collections.emptyList();
    }
}
