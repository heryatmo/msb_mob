package com.example.heryatmo.msb_mob.ShelterManagerMain;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.adapter.DaftarVolunteerAdapter;
import com.example.heryatmo.msb_mob.adapter.DaftarVolunteerKeterimaAdapter;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.RetroClient;
import com.example.heryatmo.msb_mob.response.DaftarCalonVolunteerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DaftarValonteerKeterimaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DaftarVolunteerKeterimaAdapter adapter;
    String id_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_valonteer_keterima);
        recyclerView = findViewById(R.id.rv_volunteer_keterima_list);
        SharedPreferences sp = getSharedPreferences("SPLog", Context.MODE_PRIVATE);
        id_user  = sp.getString("id_user","-");
        loadJSON();
    }

    private void recycleList(DaftarCalonVolunteerResponse daftarResponse){
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new DaftarVolunteerKeterimaAdapter(daftarResponse.getData(), this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void loadJSON(){
        Retrofit retrofit = RetroClient.getClient();

        APIService request = retrofit.create(APIService.class);
        Call<DaftarCalonVolunteerResponse> call = request.getDaftarVolunteerKeterima(id_user);
        call.enqueue(new Callback<DaftarCalonVolunteerResponse>() {
            @Override
            public void onResponse(Call<DaftarCalonVolunteerResponse> call, Response<DaftarCalonVolunteerResponse> response) {
                recycleList(response.body());
            }

            @Override
            public void onFailure(Call<DaftarCalonVolunteerResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}
