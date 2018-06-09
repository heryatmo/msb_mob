package com.example.heryatmo.msb_mob.remote;

import com.example.heryatmo.msb_mob.model.CalonVolunteer;
import com.example.heryatmo.msb_mob.model.DaftarPeran;
import com.example.heryatmo.msb_mob.model.Donasi;
import com.example.heryatmo.msb_mob.model.Pengungsi;
import com.example.heryatmo.msb_mob.model.Posisi;
import com.example.heryatmo.msb_mob.model.Post;
import com.example.heryatmo.msb_mob.model.Upload;
import com.example.heryatmo.msb_mob.model.User;
import com.example.heryatmo.msb_mob.response.BencanaResponse;
import com.example.heryatmo.msb_mob.response.DaftarCalonVolunteerResponse;
import com.example.heryatmo.msb_mob.response.JenisResponse;
import com.example.heryatmo.msb_mob.model.Logistik;
import com.example.heryatmo.msb_mob.model.RegisterResponse;
import com.example.heryatmo.msb_mob.response.DonasiResponse;
import com.example.heryatmo.msb_mob.response.LogistikResponse;
import com.example.heryatmo.msb_mob.response.PengungsiResponse;
import com.example.heryatmo.msb_mob.response.PosisiResponse;
import com.example.heryatmo.msb_mob.response.UploadResponse;
import com.example.heryatmo.msb_mob.response.UserResponse;
import com.example.heryatmo.msb_mob.response.DaftarResponse;
import com.example.heryatmo.msb_mob.response.ShelterResponse;
import com.example.heryatmo.msb_mob.response.TestResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIService {

    //----------------------------- POST ----------------------------------------------------------------

    @POST("api/v1/loginAPI")
    @FormUrlEncoded
    Call<UserResponse>
    loginRequest(@Field("email") String email, @Field("password") String password);

    @POST("api/v1/registerUser")
    Call<com.example.heryatmo.msb_mob.response.RegisterResponse>
    registerRequest(@Body RegisterResponse dataUser);

    @POST("api/v1/daftarPeran/{id}")
    Call<DaftarResponse>
    daftarPeranRequest(@Body DaftarPeran dataDaftar, @Path("id") String id);

    @POST("api/v1/logistik/{id}")
    Call<LogistikResponse>
    logistikRequest(@Body Logistik logistik, @Path("id") String id);

    @POST("api/v1/donasi/{id}")
    Call<DonasiResponse>
    donasiRequest(@Body Donasi donasi, @Path("id") String id);

    @POST("api/v1/createShelter")
    Call<PosisiResponse>
    shelterRequest(@Body Posisi posisi);

    @Multipart
    @POST("api/v1/uploadImage")
    Call<UploadResponse>
    uploadRequest(@Part Upload upload);

    @POST("api/v1/createPengungsi")
    Call<PengungsiResponse>
    pengungsiRequest(@Body Pengungsi pengungsi);

    @POST("api/v1/postingKebutuhan")
    Call<TestResponse>
    postResponse(@Body Post post);

    @POST("api/v1/terimaVolunteer/{id}/{id_user}")
    Call<DaftarPeran>
    terimaVolunteer(@Path("id") String id, @Path("id_user") String id_user);

    @POST("api/v1/terimaDonasi/{id}")
    Call<DonasiResponse>
    terimaDonasi(@Body Donasi terimaDonasi, @Path("id") String id);



    //------------------------------- GET ----------------------------------------------------------------

    @GET("api/v1/tampilPost")
    Call<TestResponse> getPost();

    @GET("api/v1/tampilShelter")
    Call<ShelterResponse> getShelter();

    @GET("api/v1/tampilJenis")
    Call<JenisResponse> getJenis();

    @GET("api/v1/tampilBencana")
    Call<BencanaResponse> getBencana();

    @GET("api/v1/tampilSesuaiSM/{id}")
    Call<ShelterResponse> getShelterSM(@Path("id") String id);

    @GET("api/v1/tampilPengungsiSM/{id}")
    Call<PengungsiResponse> getPengungsiSM(@Path("id") String id);

    @GET("api/v1/tampilDaftarVolunteer2/{id}")
    Call<DaftarCalonVolunteerResponse> getDaftarVolunteer(@Path("id") String id);

}