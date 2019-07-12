package com.hard.code.tech.drinkapp.activities;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hard.code.tech.drinkapp.R;
import com.hard.code.tech.drinkapp.adapters.CartItemAdapter;
import com.hard.code.tech.drinkapp.api.RetrofitClient;
import com.hard.code.tech.drinkapp.database.databasemodel.Cart;
import com.hard.code.tech.drinkapp.databinding.ActivityCartItemBinding;
import com.hard.code.tech.drinkapp.utils.Utils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class CartItemActivity extends AppCompatActivity {

    ActivityCartItemBinding activityCartItemBinding;
    RecyclerView recyclerViewCartItems;
    CartItemAdapter cartItemAdapter;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityCartItemBinding = DataBindingUtil.setContentView(this, R.layout.activity_cart_item);
        compositeDisposable = new CompositeDisposable();

        recyclerViewCartItems = activityCartItemBinding.recyclerViewCartItems;
        recyclerViewCartItems.setHasFixedSize(true);
        recyclerViewCartItems.setLayoutManager(new LinearLayoutManager(this));

        loadCartItems();

    }

    private void loadCartItems() {

        compositeDisposable.add(RetrofitClient.cartRepository.getCartItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayItems)
        );


    }

    private void displayItems(List<Cart> carts) {
        cartItemAdapter = new CartItemAdapter(carts, CartItemActivity.this);
        recyclerViewCartItems.setAdapter(cartItemAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    boolean isBackPressed = false;

    @Override
    public void onBackPressed() {
        if (isBackPressed) {
            super.onBackPressed();
            return;

        }
        this.isBackPressed = true;
        Utils.displayToast(this, "Press back again to exit");

    }


    @Override
    protected void onResume() {

        super.onResume();
        isBackPressed = false;
    }
}
