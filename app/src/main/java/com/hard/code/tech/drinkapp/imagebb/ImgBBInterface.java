package com.hard.code.tech.drinkapp.imagebb;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ImgBBInterface {

    @Multipart
    @POST("upload")
    Call<DataResponse> postImage(@Query("key") String apiKey, @Part MultipartBody.Part image);

}
