package com.hard.code.tech.drinkapp.database.datasource;

import com.hard.code.tech.drinkapp.database.databasemodel.Cart;

import java.util.List;

import io.reactivex.Flowable;

public class CartRepository implements ICartDataSource {

    private static CartRepository instance;
    private ICartDataSource iCartDataSource;

    private CartRepository(ICartDataSource iCartDataSource) {
        this.iCartDataSource = iCartDataSource;
    }

    public static CartRepository getInstance(ICartDataSource iCartDataSource) {
        if (instance == null)
            instance = new CartRepository(iCartDataSource);

        return instance;
    }

    @Override
    public Flowable<List<Cart>> getCartItems() {
        return this.iCartDataSource.getCartItems();
    }

    @Override
    public Flowable<List<Cart>> getCartItemsById(int cartItemId) {
        return iCartDataSource.getCartItemsById(cartItemId);
    }

    @Override
    public int countCartItems() {
        return iCartDataSource.countCartItems();
    }

    @Override
    public void emptyCart() {
        iCartDataSource.emptyCart();
    }

    @Override
    public void insertIntoCart(Cart... carts) {
        iCartDataSource.insertIntoCart(carts);
    }

    @Override
    public void updateCart(Cart... carts) {
        iCartDataSource.updateCart(carts);
    }

    @Override
    public void deleteCartItem(Cart... carts) {
        iCartDataSource.deleteCartItem(carts);
    }
}
