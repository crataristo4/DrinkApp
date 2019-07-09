package com.hard.code.tech.drinkapp.model;

import android.content.Context;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class DrinkById extends BaseObservable {
    private String id;
    private String name;
    private String link;
    private int menuId;
    private float price;


    public DrinkById() {
    }

    public DrinkById(String id, String name, String link, int menuId, float price) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.menuId = menuId;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Bindable
    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    @Bindable
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @BindingAdapter("app:drinkImageUrl")
    public static void loadDrinkImages(AppCompatImageView imageView, String link) {
        Context context = imageView.getContext();
        Glide.with(context)
                .load(link)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
