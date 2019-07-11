package com.hard.code.tech.drinkapp.database.localstorage;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hard.code.tech.drinkapp.database.databasemodel.Cart;

@Database(entities = Cart.class, version = 1)
public abstract class CartDatabase extends RoomDatabase {

    private static CartDatabase instance;

    public static CartDatabase getInstance(Context context) {

        if (instance == null) {

            instance = Room.databaseBuilder(context, CartDatabase.class, "drinkApp")
                    .allowMainThreadQueries()
                    .build();

        }

        return instance;
    }

    public abstract CartDAO cartDAO();
}
