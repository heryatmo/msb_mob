package com.example.heryatmo.msb_mob.remote;

import com.example.heryatmo.msb_mob.model.RegisterResponse;
import com.example.heryatmo.msb_mob.model.User;
import com.example.heryatmo.msb_mob.response.DaftarResponse;
import com.example.heryatmo.msb_mob.response.ShelterResponse;
import com.example.heryatmo.msb_mob.response.TestResponse;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    @POST("api/v1/loginAPI")
    @FormUrlEncoded
    Call<User>
    loginRequest(@Field("email") String email, @Field("password") String password);

    @POST("api/v1/registerUser")
    Call<com.example.heryatmo.msb_mob.response.RegisterResponse>
    registerRequest(@Body RegisterResponse dataUser);

    @POST("api/v1/daftarPeran/{id}")
    Call<DaftarResponse>
    daftarPeranRequest(@Body DaftarResponse dataDaftar);

    @GET("api/v1/tampilPost")
    Call<TestResponse> getPost();

    @GET("api/v1/tampilShelter")
    Call<ShelterResponse> getShelter();

//    @POST("api/v1/registerUser")
//    Call<com.example.heryatmo.msb_mob.response.RegisterResponse>
//    registerRequest(@Field("email") String email, @Field("password") String password, @Field("nama") String nama, @Field("tempat_lahir") String tempat_lahir,
//                   @Field("tanggal_lahir") Date tanggal_lahir, @Field("jenis_kelamin") String jenis_kelamin,
//                    @Field("golongan_darah") String golongan_darah, @Field("alamat") String alamat, @Field("no_hp") String no_hp,
//                    @Field("id_role") int id_role, @Field("status_user") String status_user);


}