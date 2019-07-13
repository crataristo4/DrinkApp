package com.hard.code.tech.drinkapp.database.localstorage;

import com.hard.code.tech.drinkapp.database.databasemodel.Favorite;
import com.hard.code.tech.drinkapp.database.datasource.IFavoriteDataSource;

import java.util.List;

import io.reactivex.Flowable;

public class FavoriteDataSource implements IFavoriteDataSource {

    public static FavoriteDataSource instance;
    private FavoriteDAO favoriteDAO;

    private FavoriteDataSource(FavoriteDAO favoriteDAO) {
        this.favoriteDAO = favoriteDAO;
    }


    public static FavoriteDataSource getInstance(FavoriteDAO favoriteDAO) {

        if (instance == null) {
            instance = new FavoriteDataSource(favoriteDAO);
        }
        return instance;
    }

    @Override
    public Flowable<List<Favorite>> getFavorite() {
        return favoriteDAO.getFavorite();
    }

    @Override
    public int isFavorite(int itemId) {
        return favoriteDAO.isFavorite(itemId);
    }

    @Override
    public void deleteFavorite(Favorite favorite) {
        favoriteDAO.deleteFavorite(favorite);
    }

    @Override
    public void insertFavorite(Favorite... favorites) {
        favoriteDAO.insertFavorite(favorites);
    }
}
