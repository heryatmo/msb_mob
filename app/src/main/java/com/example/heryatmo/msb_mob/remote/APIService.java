package com.example.heryatmo.msb_mob.remote;

import com.example.heryatmo.msb_mob.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    @POST("api/v1/loginAPI")
    Call<User>
    loginRequest(@Field("email") String username, @Field("password") String password);

    @GET("api/v1/tampilPost")
    Call<PostResponse> getPost();
}
