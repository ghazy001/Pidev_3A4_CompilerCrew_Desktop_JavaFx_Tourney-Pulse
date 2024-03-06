package tn.Esprit.services;

import tn.Esprit.models.Cart;
import tn.Esprit.models.MarketPlace;

import java.util.List;

public class ServiceCart {
    private Cart cart;

    public ServiceCart() {
        this.cart = new Cart();
    }

    public void addItemToCart(MarketPlace item) {
        cart.addItem(item);
    }

    public void removeItemFromCart(MarketPlace item) {
        cart.removeItem(item);
    }

    public List<MarketPlace> getItemsInCart() {
        return cart.getItems();
    }

    public double getTotalPriceInCart() {
        return cart.getTotalPrice();
    }

    public int getTotalQuantityInCart() {
        return cart.getTotalQuantity();
    }

    public void clearCart() {
        cart.clearCart();
    }

    public void updateQuantityInCart(MarketPlace item, int newQuantity) {
        cart.updateQuantity(item, newQuantity);
    }

    public boolean isCartEmpty() {
        return cart.isEmpty();
    }
}
