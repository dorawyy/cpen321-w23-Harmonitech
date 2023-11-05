package com.cpen321.tunematch;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BackendInterface {

    @GET("/v1/users/{userId}")
    Call<String> getUser(@Path("userId") String userId, @Query("fullProfile") boolean full);
    @POST("/v1/users/create")
    Call<String> createUser(@Body JsonObject body);
    @GET("/v1/users/search")
    Call<String> searchUser(@Query("q") String searchTerm, @Header("user-id") String currentUserId);
    @GET("/v1/me")
    Call<String> getMe(@Header("user-id") String currentUserId, @Query("fullProfile") boolean full);
    @GET("/v1/me/matches")
    Call<String> getMatches(@Header("user-id") String currentUserId);
    @PUT("/v1/me/update")
    Call<String> updateMe(@Body JsonObject body, @Header("user-id") String currentUserId);
    @POST("/v1/reports/create")
    Call<String> createReport(@Body JsonObject body, @Header("user-id") String currentUserId);

}