package com.hard.code.tech.drinkapp.api;

import com.hard.code.tech.drinkapp.database.datasource.CartRepository;
import com.hard.code.tech.drinkapp.database.localstorage.CartDatabase;
import com.hard.code.tech.drinkapp.model.DrinkById;
import com.hard.code.tech.drinkapp.model.MenuCategory;
import com.hard.code.tech.drinkapp.model.Users;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

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
    public static CartDatabase cartDatabase;
    public static CartRepository cartRepository;


    private RetrofitClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
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
