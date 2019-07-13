package com.hard.code.tech.drinkapp.database.datasource;

import com.hard.code.tech.drinkapp.database.databasemodel.Favorite;

import java.util.List;

import io.reactivex.Flowable;

public interface IFavoriteDataSource {

    Flowable<List<Favorite>> getFavorite();

    int isFavorite(int itemId);

    void deleteFavorite(Favorite favorite);

    void insertFavorite(Favorite... favorites);
}
