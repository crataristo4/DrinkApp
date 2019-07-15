package com.hard.code.tech.drinkapp.database.databasemodel;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite")
public class Favorite extends BaseObservable {


    @NonNull
    @PrimaryKey()
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "imageUrl")
    public String imageUrl;

    @ColumnInfo(name = "price")
    public float price;

    @ColumnInfo(name = "menuId")
    public int menuId;

    public Favorite(int id, String name, String imageUrl, float price, int menuId) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.menuId = menuId;
    }
}
