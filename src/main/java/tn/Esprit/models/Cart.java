package tn.Esprit.models;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<MarketPlace> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public void addItem(MarketPlace item) {
        items.add(item);
    }

    public void removeItem(MarketPlace item) {
        items.remove(item);
    }

    public List<MarketPlace> getItems() {
        return items;
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (MarketPlace item : items) {
            totalPrice += item.getPrice();
        }
        return totalPrice;
    }

    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (MarketPlace item : items) {
            totalQuantity += item.getQuantity();
        }
        return totalQuantity;
    }

    public void clearCart() {
        items.clear();
    }

    public void updateQuantity(MarketPlace item, int newQuantity) {
        if (items.contains(item)) {
            item.setQuantity(newQuantity);
        }
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    // Add more methods as needed
}

