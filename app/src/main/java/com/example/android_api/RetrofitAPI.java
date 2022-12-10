package com.example.android_api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @POST("Dogs")
    Call<Modal> createPost(@Body Modal mModal);

    @PUT("Dogs/")
    Call<Modal> updateData(@Query("id") int Id, @Body Modal mModal);

    @DELETE("Dogs/")
    Call<Modal> deleteData(@Query("id") int Id);
}
