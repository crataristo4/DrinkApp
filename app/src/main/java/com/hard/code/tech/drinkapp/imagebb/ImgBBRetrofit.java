package com.hard.code.tech.drinkapp.imagebb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ImgBBRetrofit {

    private static final String BASE_URL = "https://api.imgbb.com/1/";
    private static ImgBBRetrofit instance;
    private static Retrofit retrofit;

    private ImgBBRetrofit() {

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

    public static synchronized ImgBBRetrofit getInstance() {
        if (instance == null) {
            instance = new ImgBBRetrofit();
        }
        return instance;
    }


    public ImgBBInterface getImgbbApi() {
        return retrofit.create(ImgBBInterface.class);
    }
}
