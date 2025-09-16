package com.example.cart.Model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CartModel {
    private ArrayList<CartproductModel> CartItems;
    private int TotalAmount;
    private int DiscountAmount;
    private  int ToatlItems;
    private int mrp;

    public int getMrp() {
        return mrp;
    }

    public void setMrp(int mrp) {
        this.mrp = mrp;
    }

    public ArrayList<CartproductModel> getCartItems() {
        return CartItems;
    }

    public void setCartItems(ArrayList<CartproductModel> cartItems) {
        CartItems = cartItems;
    }

    public int getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        TotalAmount = totalAmount;
    }

    public int getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        DiscountAmount = discountAmount;
    }

    public int getToatlItems() {
        return ToatlItems;
    }

    public void setToatlItems(int toatlItems) {
        ToatlItems = toatlItems;
    }
}
