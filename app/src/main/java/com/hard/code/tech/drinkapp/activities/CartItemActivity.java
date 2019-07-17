package com.hard.code.tech.drinkapp.activities;


import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hard.code.tech.drinkapp.Interface.RecyclerItemTouchHelper;
import com.hard.code.tech.drinkapp.R;
import com.hard.code.tech.drinkapp.adapters.CartItemAdapter;
import com.hard.code.tech.drinkapp.api.RetrofitClient;
import com.hard.code.tech.drinkapp.database.databasemodel.Cart;
import com.hard.code.tech.drinkapp.databinding.ActivityCartItemBinding;
import com.hard.code.tech.drinkapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class CartItemActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    ActivityCartItemBinding activityCartItemBinding;
    RecyclerView recyclerViewCartItems;
    CartItemAdapter cartItemAdapter;
    List<Cart> cartList = new ArrayList<>();
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
        swipeLeftToDelete();

    }

    private void loadCartItems() {

        compositeDisposable.add(RetrofitClient.cartRepository.getCartItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayItems)
        );


    }

    private void displayItems(List<Cart> carts) {
        cartList = carts;
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

    private void swipeLeftToDelete() {
        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
                new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerViewCartItems);


        //swipe to delete
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                    }

                    @Override
                    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                };

        // attaching the touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(recyclerViewCartItems);

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CartItemAdapter.CartItemViewHolder) {

            String nameOfItem = cartList.get(viewHolder.getAdapterPosition()).name;

            Cart itemDeleted = cartList.get(viewHolder.getAdapterPosition());

            int deletedIndex = viewHolder.getAdapterPosition();

            //delete item from adapter
            cartItemAdapter.onCartItemDeleted(deletedIndex);
            //delete from database
            RetrofitClient.cartRepository.deleteCartItem(itemDeleted);

            Utils.makeSnackbar(activityCartItemBinding.linearLayout,
                    nameOfItem + " has been deleted from carts",
                    "UNDO DELETE", view -> {

                        cartItemAdapter.onCartItemRestored(itemDeleted, deletedIndex);
                        //restore to database
                        RetrofitClient.cartRepository.insertIntoCart(itemDeleted);

                    });


        }
    }
}
