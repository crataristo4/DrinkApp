package com.hard.code.tech.drinkapp.database.localstorage;

import com.hard.code.tech.drinkapp.database.databasemodel.Cart;
import com.hard.code.tech.drinkapp.database.datasource.ICartDataSource;

import java.util.List;

import io.reactivex.Flowable;

public class CartDataSource implements ICartDataSource {

    private static CartDataSource instance;
    private CartDAO cartDAO;

    private CartDataSource(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }

    public static CartDataSource getInstance(CartDAO cartDAO) {

        if (instance == null)
            instance = new CartDataSource(cartDAO);

        return instance;
    }

    @Override
    public Flowable<List<Cart>> getCartItems() {
        return cartDAO.getCartItems();
    }

    @Override
    public Flowable<List<Cart>> getCartItemsById(int cartItemId) {
        return cartDAO.getCartItemsById(cartItemId);
    }

    @Override
    public int countCartItems() {
        return cartDAO.countCartItems();
    }

    @Override
    public void emptyCart() {
        cartDAO.emptyCart();
    }

    @Override
    public void insertIntoCart(Cart... carts) {
        cartDAO.insertIntoCart(carts);
    }

    @Override
    public void updateCart(Cart... carts) {
        cartDAO.updateCart(carts);
    }

    @Override
    public void deleteCartItem(Cart... carts) {
        cartDAO.deleteCartItem(carts);
    }
}
