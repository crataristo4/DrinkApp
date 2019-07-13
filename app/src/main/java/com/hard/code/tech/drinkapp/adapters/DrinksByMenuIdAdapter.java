package com.hard.code.tech.drinkapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.gson.Gson;
import com.hard.code.tech.drinkapp.R;
import com.hard.code.tech.drinkapp.activities.CartItemActivity;
import com.hard.code.tech.drinkapp.api.RetrofitClient;
import com.hard.code.tech.drinkapp.database.databasemodel.Cart;
import com.hard.code.tech.drinkapp.databinding.LayoutAddToCartBinding;
import com.hard.code.tech.drinkapp.databinding.LayoutAlertDialogBinding;
import com.hard.code.tech.drinkapp.databinding.LayoutDrinksByMenuIdBinding;
import com.hard.code.tech.drinkapp.model.DrinkById;
import com.hard.code.tech.drinkapp.utils.Utils;

import java.util.List;

public class DrinksByMenuIdAdapter extends RecyclerView.Adapter<DrinksByMenuIdAdapter.DrinksViewHolder> {

    private List<DrinkById> drinkByIdList;
    private Context context;

    public DrinksByMenuIdAdapter(List<DrinkById> drinkByIdList, Context context) {
        this.drinkByIdList = drinkByIdList;
        this.context = context;
    }

    @NonNull
    @Override
    public DrinksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutDrinksByMenuIdBinding layoutDrinksByMenuIdBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.layout_drinks_by_menu_id, parent, false);

        return new DrinksViewHolder(layoutDrinksByMenuIdBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinksViewHolder holder, int position) {


        DrinkById drinkById = drinkByIdList.get(position);
        holder.layoutDrinksByMenuIdBinding.setDrinks(drinkById);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawableColor());
        requestOptions.error(Utils.getRandomDrawableColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(context)
                .load(drinkById.getLink())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.layoutDrinksByMenuIdBinding.loading.setVisibility(View.VISIBLE);
                        holder.layoutDrinksByMenuIdBinding.btnAddToCart.setEnabled(false);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.layoutDrinksByMenuIdBinding.loading.setVisibility(View.INVISIBLE);
                        holder.layoutDrinksByMenuIdBinding.btnAddToCart.setEnabled(true);
                        return false;
                    }
                }).transition(DrawableTransitionOptions.withCrossFade())

                .into(holder.layoutDrinksByMenuIdBinding.drinkImage);

        //add to cart click event
        holder.layoutDrinksByMenuIdBinding.btnAddToCart.setOnClickListener(view -> {

            showAlertDialog(position);
        });

        //favorite process

        //add to favorite click event



    }

    private void showAlertDialog(int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View view = LayoutInflater.from(context)
                .inflate(R.layout.layout_alert_dialog, null);

        LayoutAlertDialogBinding layoutAlertDialogBinding = DataBindingUtil.bind(view);


        DrinkById getDrinks = drinkByIdList.get(position);
        if (layoutAlertDialogBinding != null) {

            layoutAlertDialogBinding.setDrinkByCart(getDrinks);
            builder.setView(layoutAlertDialogBinding.getRoot());

            RecyclerView toppingsRecycler = layoutAlertDialogBinding.recyclerToppings;
            toppingsRecycler.setHasFixedSize(true);
            toppingsRecycler.setLayoutManager(new LinearLayoutManager(context));

            //size of cup
            layoutAlertDialogBinding.radioBtnMedium.setOnCheckedChangeListener((compoundButton, b) -> {

                if (b) RetrofitClient.cupSize = 0;

            });

            layoutAlertDialogBinding.radioBtnLarge.setOnCheckedChangeListener((compoundButton, b) -> {

                if (b) RetrofitClient.cupSize = 1;

            });


            //Sugar content radio button
            layoutAlertDialogBinding.radioBtnSugar100.setOnCheckedChangeListener((compoundButton, b) -> {

                if (b) RetrofitClient.sugarContent = 100;
            });
            layoutAlertDialogBinding.radioBtnSugar75.setOnCheckedChangeListener((compoundButton, b) -> {

                if (b) RetrofitClient.sugarContent = 75;
            });
            layoutAlertDialogBinding.radioBtnSugar50.setOnCheckedChangeListener((compoundButton, b) -> {

                if (b) RetrofitClient.sugarContent = 50;
            });
            layoutAlertDialogBinding.radioBtnSugar25.setOnCheckedChangeListener((compoundButton, b) -> {

                if (b) RetrofitClient.sugarContent = 25;
            });
            layoutAlertDialogBinding.radioBtnSugarFree.setOnCheckedChangeListener((compoundButton, b) -> {

                if (b) RetrofitClient.sugarContent = 0;
            });

            //Ice content radio button
            layoutAlertDialogBinding.radioBtnIce100.setOnCheckedChangeListener((compoundButton, b) -> {

                if (b) RetrofitClient.iceContent = 100;
            });
            layoutAlertDialogBinding.radioBtnIce75.setOnCheckedChangeListener((compoundButton, b) -> {

                if (b) RetrofitClient.iceContent = 75;
            });
            layoutAlertDialogBinding.radioBtnIce50.setOnCheckedChangeListener((compoundButton, b) -> {

                if (b) RetrofitClient.iceContent = 50;
            });
            layoutAlertDialogBinding.radioBtnIce25.setOnCheckedChangeListener((compoundButton, b) -> {

                if (b) RetrofitClient.iceContent = 25;
            });
            layoutAlertDialogBinding.radioBtnNoIce.setOnCheckedChangeListener((compoundButton, b) -> {

                if (b) RetrofitClient.iceContent = 0;
            });

            MultiChoiceToppingsAdater toppingsAdater = new MultiChoiceToppingsAdater(context, RetrofitClient.toppingList);
            toppingsRecycler.setAdapter(toppingsAdater);
        }


        //display Add to cart alert dialog
        builder.setNegativeButton("Add to Cart", (dialogInterface, i) -> {

            assert layoutAlertDialogBinding != null;
            ElegantNumberButton txtCountItems = layoutAlertDialogBinding.cartCounter;

            //check if user has selected the required items
            if (RetrofitClient.cupSize == -1) {
                Utils.displayToast(context, "Please select a cup size");
                return;

            }
            if (RetrofitClient.sugarContent == -1) {
                Utils.displayToast(context, "Please select sugar content for drink");
                return;

            }
            if (RetrofitClient.iceContent == -1) {
                Utils.displayToast(context, "Please select ice content for drink");
                return;

            }


            displayConfirmationDialog(position, txtCountItems.getNumber());


        });

        builder.show();


    }

    /**
     * show confirmation dialog
     *
     * @param position Returns the position of the item selected
     * @param number   Returns to total item count (drinks) that will be selected
     **/
    private void displayConfirmationDialog(int position, String number) {

        AlertDialog.Builder addToCartBuilder = new AlertDialog.Builder(context);
        View addToCartView = LayoutInflater.from(context)
                .inflate(R.layout.layout_add_to_cart, null);

        LayoutAddToCartBinding addToCartBinding = DataBindingUtil.bind(addToCartView);
        if (addToCartBinding != null) {

            Glide.with(context).load(drinkByIdList.get(position).getLink()).into(addToCartBinding.imgItemImage);


            addToCartBinding.txtItemName.setText(new StringBuilder(drinkByIdList.get(position).getName())
                    .append(" x ")
                    .append(number)
                    .append(RetrofitClient.cupSize == 0 ? " Medium size " : " Large size "));

            addToCartBinding.txtSugarContent.setText(new StringBuilder("Sugar ")
                    .append(RetrofitClient.sugarContent)
                    .append(" % "));

            addToCartBinding.txtIceContent.setText(new StringBuilder("Ice ")
                    .append(RetrofitClient.iceContent)
                    .append(" % "));

            /*calculate the price by multiplying the price of drink
             *by the number of drinks selected
             *and adding it to the number of toppings selected
             */
            double price = (Double.parseDouble(String.valueOf(drinkByIdList.get(position).getPrice())) * Double.parseDouble(number)) + RetrofitClient.toppingsPrice;

            //increase the price when a large cup size is selected
            if (RetrofitClient.cupSize == 1) price += 5.0;

            Log.i("Price : ", "" + price);

            TextView txtItemPrice = addToCartBinding.txtItemPrice;
            txtItemPrice.setText(String.valueOf(price));

            StringBuilder setToppings = new StringBuilder("");
            for (String line : RetrofitClient.addedToppings)
                setToppings.append(line).append("\n");

            TextView txtToppingsAdded = addToCartBinding.txtToppingsAdded;
            txtToppingsAdded.setText(setToppings);

            addToCartBuilder.setView(addToCartBinding.getRoot());

            double finalPrice = price;
            addToCartBuilder.setNegativeButton("Confirm", (dialogInterface, i) -> {

                try {

                    //create a new Cart Item
                    Cart addCartItem = new Cart(
                            addToCartBinding.txtItemName.getText().toString(),
                            drinkByIdList.get(position).getLink(),
                            Integer.parseInt(number),
                            Double.parseDouble(String.valueOf(finalPrice)),
                            RetrofitClient.sugarContent,
                            RetrofitClient.iceContent,
                            txtToppingsAdded.getText().toString()

                    );

                    //Add to database
                    RetrofitClient.cartRepository.insertIntoCart(addCartItem);

                    Log.i("Response : ", new Gson().toJson(addCartItem));

                    dialogInterface.dismiss();
                    context.startActivity(new Intent(context, CartItemActivity.class));

                } catch (Exception e) {
                    e.printStackTrace();
                }


            });
        }

        addToCartBuilder.show();
    }

    @Override
    public int getItemCount() {
        return drinkByIdList == null ? 0 : drinkByIdList.size();
    }

    static class DrinksViewHolder extends RecyclerView.ViewHolder {

        LayoutDrinksByMenuIdBinding layoutDrinksByMenuIdBinding;

        DrinksViewHolder(@NonNull LayoutDrinksByMenuIdBinding layoutDrinksByMenuIdBinding) {
            super(layoutDrinksByMenuIdBinding.getRoot());

            this.layoutDrinksByMenuIdBinding = layoutDrinksByMenuIdBinding;

        }


    }
 /*   @SuppressLint("CheckResult")
    @BindingAdapter("drinkImageUrl")
    public  static void loadDrinkImages(AppCompatImageView imageView, String link) {
        Context context = imageView.getContext();


    }*/


}
