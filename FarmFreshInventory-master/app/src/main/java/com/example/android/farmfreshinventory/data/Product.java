package com.example.android.farmfreshinventory.data;

public class Product {

    private int mProductId;
    private int mProductQuantity;

    public Product(int mProductId, int mProductQuantity) {
        this.mProductId = mProductId;
        this.mProductQuantity = mProductQuantity;
    }

    public int getmProductId() {
        return mProductId;
    }

    public int getmProductQuantity() {
        return mProductQuantity;
    }
}
