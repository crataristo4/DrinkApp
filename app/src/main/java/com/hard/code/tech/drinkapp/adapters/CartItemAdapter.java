package com.hard.code.tech.drinkapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.hard.code.tech.drinkapp.R;
import com.hard.code.tech.drinkapp.api.RetrofitClient;
import com.hard.code.tech.drinkapp.database.databasemodel.Cart;
import com.hard.code.tech.drinkapp.databinding.LayoutCartItemsBinding;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {

    private List<Cart> cartList;
    private Context context;

    public CartItemAdapter(List<Cart> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutCartItemsBinding layoutCartItemsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.layout_cart_items, parent, false);

        return new CartItemViewHolder(layoutCartItemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {

        Cart cart = cartList.get(position);

        holder.layoutCartItemsBinding.setCartItem(cart);

        //auto update cart item when the quantity is changed
        cart.quantity = Integer.parseInt(holder.elegantNumberButton.getNumber());

        RetrofitClient.cartRepository.updateCart(cart);

    }

    @Override
    public int getItemCount() {
        return cartList == null ? 0 : cartList.size();
    }

    static class CartItemViewHolder extends RecyclerView.ViewHolder {

        LayoutCartItemsBinding layoutCartItemsBinding;
        ElegantNumberButton elegantNumberButton;

        CartItemViewHolder(@NonNull LayoutCartItemsBinding layoutCartItemsBinding) {
            super(layoutCartItemsBinding.getRoot());

            this.layoutCartItemsBinding = layoutCartItemsBinding;

            elegantNumberButton = layoutCartItemsBinding.btnCartItemQty;
        }
    }
}
