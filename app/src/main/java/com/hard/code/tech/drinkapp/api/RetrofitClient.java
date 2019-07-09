package com.hard.code.tech.drinkapp.api;

import com.hard.code.tech.drinkapp.model.MenuCategory;
import com.hard.code.tech.drinkapp.model.Users;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.RxThreadFactory;
import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://10.0.2.2/MyDrinkApp/";
    private static RetrofitClient instance;
    private static Retrofit retrofit;
    public static Users currentUser = null;
    public static MenuCategory menuCategory = null;

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
