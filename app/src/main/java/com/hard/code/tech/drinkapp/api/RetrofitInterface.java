package com.hard.code.tech.drinkapp.api;

import com.hard.code.tech.drinkapp.model.Banners;
import com.hard.code.tech.drinkapp.model.DrinkById;
import com.hard.code.tech.drinkapp.model.MenuCategory;
import com.hard.code.tech.drinkapp.model.UserResponse;
import com.hard.code.tech.drinkapp.model.Users;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitInterface {

    @FormUrlEncoded
    @POST("checkUser.php")
    Call<UserResponse> USER_RESPONSE_CALL(
            @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST("registerUser.php")
    Call<Users> createUser(
            @Field("phone") String phone,
            @Field("name") String name,
            @Field("dob") String dob,
            @Field("address") String address


    );

    @FormUrlEncoded
    @POST("getUsersInfo.php")
    Call<UserResponse> getUsersInfo(
            @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST("getMenuById.php")
    Observable<List<DrinkById>> getMenuById(
            @Field("menuId") String menuId
    );


    @GET("getBanners.php")
    Observable<List<Banners>> getBanners();


    @GET("getMenus.php")
    Observable<List<MenuCategory>> getCategory();

    @Multipart
    @POST("uploadFile.php")
    Call<String> uploadFile(@Part("phone") String phone, @Part MultipartBody.Part file);
}
