package com.example.heryatmo.msb_mob.VolunteerMain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.adapter.DaftarVolunteerAdapter;
import com.example.heryatmo.msb_mob.adapter.PengungsiAdapter;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.RetroClient;
import com.example.heryatmo.msb_mob.response.DaftarCalonVolunteerResponse;
import com.example.heryatmo.msb_mob.response.TampilPengungsiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PengungsiMainActivity extends AppCompatActivity {

    Button btnTambahPengungsi;
    private RecyclerView recyclerView;
    private PengungsiAdapter adapter;
    String id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pengungsi_main);

            recyclerView = findViewById(R.id.rv_pengungsi_list);

            SharedPreferences sp = getSharedPreferences("SPLog", Context.MODE_PRIVATE);
            id_user  = sp.getString("id_user","-");

            btnTambahPengungsi = findViewById(R.id.btnTambahPengungsi);
            btnTambahPengungsi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PengungsiMainActivity.this, PengungsiActivity.class);
                    startActivity(intent);

                }
            });
            loadJSON();
        }
        private void recycleList(TampilPengungsiResponse daftarPengungsi){
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            adapter = new PengungsiAdapter(daftarPengungsi.getMData(), this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }

        private void loadJSON(){
            Retrofit retrofit = RetroClient.getClient();

            APIService request = retrofit.create(APIService.class);
            Call<TampilPengungsiResponse> call = request.getPengungsiSM(id_user);
            call.enqueue(new Callback<TampilPengungsiResponse>() {
                @Override
                public void onResponse(Call<TampilPengungsiResponse> call, Response<TampilPengungsiResponse> response) {
                    recycleList(response.body());
                }

                @Override
                public void onFailure(Call<TampilPengungsiResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                }
            });
        }
}
