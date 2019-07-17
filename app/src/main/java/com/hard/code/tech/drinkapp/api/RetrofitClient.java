package com.hard.code.tech.drinkapp.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hard.code.tech.drinkapp.database.datasource.CartRepository;
import com.hard.code.tech.drinkapp.database.datasource.FavoriteRepository;
import com.hard.code.tech.drinkapp.database.localstorage.DrinksRoomDatabase;
import com.hard.code.tech.drinkapp.model.DrinkById;
import com.hard.code.tech.drinkapp.model.MenuCategory;
import com.hard.code.tech.drinkapp.model.Users;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {

    // public static final String BASE_URL = "http://tastydrinks.epizy.com/MyDrinkApp/";
    public static final String BASE_URL = "http://10.0.2.2/MyDrinkApp/";
    private static RetrofitClient instance;
    private static Retrofit retrofit;

    public static Users currentUser = null;
    public static MenuCategory menuCategory = null;

    public static final String TOPPINGS_ID = "7"; //because 7 is the id from the menu database
    public static List<DrinkById> toppingList = new ArrayList<>();

    public static double toppingsPrice = 0.0;
    public static List<String> addedToppings = new ArrayList<>();

    /*
    Set values to -1 to represent no item selected which is an error
    Set values 0 for M
    Set Values 1 for L
     */
    public static int cupSize = -1;
    public static int sugarContent = -1;
    public static int iceContent = -1;


    //database
    public static DrinksRoomDatabase drinksRoomDatabase;
    public static CartRepository cartRepository;
    public static FavoriteRepository favoriteRepository;


    private RetrofitClient() {

        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }


    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }


    public RetrofitInterface getAPI() {
        return retrofit.create(RetrofitInterface.class);
    }

}
