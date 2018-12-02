package com.example.heryatmo.msb_mob.ShelterManagerMain;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.adapter.DaftarVolunteerAdapter;
import com.example.heryatmo.msb_mob.adapter.DaftarVolunteerKeterimaAdapter;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.RetroClient;
import com.example.heryatmo.msb_mob.response.DaftarCalonVolunteerResponse;
import com.example.heryatmo.msb_mob.response.TotalVolunteerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DaftarValonteerKeterimaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DaftarVolunteerKeterimaAdapter adapter;
    private TextView totVolunteer;
    String id_user,totVol;
    int count ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_valonteer_keterima);
        recyclerView = findViewById(R.id.rv_volunteer_keterima_list);

        totVolunteer = findViewById(R.id.textJumlahVolunteerTerkini);


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
        count = recyclerView.getAdapter().getItemCount();
        totVol = String.valueOf(count);
        totVolunteer.setText(totVol);
        Log.d("count : ",totVol);
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

//    private void loadTotalVol(){
//        Retrofit retrofit = RetroClient.getClient();
//
//        APIService request = retrofit.create(APIService.class);
//        Call<TotalVolunteerResponse> call = request.getTotalVolunteer(id_user);
//        call.enqueue(new Callback<TotalVolunteerResponse>() {
//            @Override
//            public void onResponse(Call<TotalVolunteerResponse> call, Response<TotalVolunteerResponse> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<TotalVolunteerResponse> call, Throwable t) {
//
//            }
//        });
//    }
}
