package com.hard.code.tech.drinkapp.database.localstorage;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hard.code.tech.drinkapp.database.databasemodel.Cart;
import com.hard.code.tech.drinkapp.database.databasemodel.Favorite;

@Database(entities = {Cart.class, Favorite.class}, version = 1)
public abstract class DrinksRoomDatabase extends RoomDatabase {

    private static DrinksRoomDatabase instance;

    public static DrinksRoomDatabase getInstance(Context context) {

        if (instance == null) {

            instance = Room.databaseBuilder(context, DrinksRoomDatabase.class, "drinkApp")
                    .allowMainThreadQueries()
                    .build();

        }

        return instance;
    }

    public abstract FavoriteDAO favoriteDAO();

    public abstract CartDAO cartDAO();


}
