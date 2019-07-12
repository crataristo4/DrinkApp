package com.hard.code.tech.drinkapp.database.databasemodel;


import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cart")
public class Cart extends BaseObservable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "imageUrl")
    public String imageUrl;

    @ColumnInfo(name = "quantity")
    public int quantity;

    @ColumnInfo(name = "price")
    public double price;

    @ColumnInfo(name = "sugar")
    public int sugar;

    @ColumnInfo(name = "ice")
    public int ice;

    @ColumnInfo(name = "toppingsAdded")
    public String toppingsAdded;

    public Cart(String name, String imageUrl, int quantity, double price, int sugar, int ice, String toppingsAdded) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.price = price;
        this.sugar = sugar;
        this.ice = ice;
        this.toppingsAdded = toppingsAdded;
    }
}
