package com.hard.code.tech.drinkapp.model;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import de.hdodenhof.circleimageview.CircleImageView;

public class Users extends BaseObservable {
    private int id;
    private String phone, name, dob, address, imageUrl, error_msg;

    public Users() {

    }

    public Users(int id, String phone, String name, String dob, String address, String imageUrl) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    @BindingAdapter("image")
    public static void loadImage(CircleImageView imageView, String link) {
        Context context = imageView.getContext();

        Glide.with(context)
                .load(link)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
