package com.hard.code.tech.drinkapp.database.datasource;

import com.hard.code.tech.drinkapp.database.databasemodel.Cart;

import java.util.List;

import io.reactivex.Flowable;

public interface ICartDataSource {

    Flowable<List<Cart>> getCartItems();

    Flowable<List<Cart>> getCartItemsById(int cartItemId);

    int countCartItems();

    void emptyCart();

    void insertIntoCart(Cart... carts);

    void updateCart(Cart... carts);

    void deleteCartItem(Cart... carts);
}
