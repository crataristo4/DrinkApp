package com.hard.code.tech.drinkapp.database.datasource;

import com.hard.code.tech.drinkapp.database.databasemodel.Favorite;

import java.util.List;

import io.reactivex.Flowable;

public class FavoriteRepository implements IFavoriteDataSource {

    public static FavoriteRepository instance;
    private IFavoriteDataSource iFavoriteDataSource;

    private FavoriteRepository(IFavoriteDataSource iFavoriteDataSource) {
        this.iFavoriteDataSource = iFavoriteDataSource;
    }

    public static FavoriteRepository getInstance(IFavoriteDataSource iFavoriteDataSource) {

        if (instance == null)
            instance = new FavoriteRepository(iFavoriteDataSource);

        return instance;
    }

    @Override
    public Flowable<List<Favorite>> getFavorite() {
        return iFavoriteDataSource.getFavorite();
    }

    @Override
    public int isFavorite(int itemId) {
        return iFavoriteDataSource.isFavorite(itemId);
    }

    @Override
    public void deleteFavorite(Favorite favorite) {
        iFavoriteDataSource.deleteFavorite(favorite);
    }

    @Override
    public void insertFavorite(Favorite... favorites) {
        iFavoriteDataSource.insertFavorite(favorites);
    }
}
