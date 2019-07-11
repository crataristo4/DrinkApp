package com.hard.code.tech.drinkapp.database.localstorage;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.hard.code.tech.drinkapp.database.databasemodel.Cart;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface CartDAO {

    @Query("SELECT * from Cart")
    Flowable<List<Cart>> getCartItems();

    @Query("select * from Cart where id=:cartItemId")
    Flowable<List<Cart>> getCartItemsById(int cartItemId);

    @Query("select COUNT(*) from Cart")
    int countCartItems();

    @Query("delete from Cart")
    void emptyCart();

    @Insert
    void insertIntoCart(Cart... carts);

    @Update
    void updateCart(Cart... carts);

    @Delete
    void deleteCartItem(Cart... carts);
}
