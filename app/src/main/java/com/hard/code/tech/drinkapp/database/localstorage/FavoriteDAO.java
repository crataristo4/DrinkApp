package com.hard.code.tech.drinkapp.database.localstorage;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.hard.code.tech.drinkapp.database.databasemodel.Favorite;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface FavoriteDAO {

    @Query("Select * from favorite")
    Flowable<List<Favorite>> getFavorite();

    @Query("Select Exists (Select 1 from favorite where id=:itemId)")
    int isFavorite(int itemId);

    @Insert
    void insertFavorite(Favorite... favorites);

    @Delete
    void deleteFavorite(Favorite favorite);
}
