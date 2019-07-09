package com.hard.code.tech.drinkapp.model;

import android.content.Context;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hard.code.tech.drinkapp.BR;

public class MenuCategory extends BaseObservable {
    private String id;
    private String name;
    private String link;

    public MenuCategory() {
    }

    public MenuCategory(String id, String name, String link) {
        this.id = id;
        this.name = name;
        this.link = link;
    }

    @Bindable
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
        notifyPropertyChanged(BR.link);
    }

    @BindingAdapter("app:imageUrl")
    public static void loadImages(AppCompatImageView imageView, String link) {
        Context context = imageView.getContext();
        Glide.with(context)
                .load(link)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
