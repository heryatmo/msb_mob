package com.example.heryatmo.msb_mob.UserMain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.adapter.AlurLogAdapter;
import com.example.heryatmo.msb_mob.adapter.DetilAlurLogAdapter;
import com.example.heryatmo.msb_mob.model.AlurLog;
import com.example.heryatmo.msb_mob.model.DetilAlurLog;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.RetroClient;
import com.example.heryatmo.msb_mob.response.AlurLogResponse;
import com.example.heryatmo.msb_mob.response.DetilAlurLogResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetilAlurLogActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DetilAlurLogAdapter adapter;
    String id_user;
    String id_logistik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil_alur_log);

        recyclerView = findViewById(R.id.rv_list_detil_alur_logistik);
        SharedPreferences sp = getSharedPreferences("SPLog", Context.MODE_PRIVATE);
        id_user  = sp.getString("id_user","-");
        id_logistik = getIntent().getStringExtra("id_logistik");

        loadJSON();
    }

    private void recycleList(DetilAlurLogResponse detilAlurResponse){
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new DetilAlurLogAdapter(detilAlurResponse.getMData(), this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void loadJSON(){
        Retrofit retrofit = RetroClient.getClient();

        APIService request = retrofit.create(APIService.class);
        DetilAlurLog detilAlurLog = DetilAlurLog.builder().mIdUser(id_user).mIdLogistik(id_logistik).build();
        Call<DetilAlurLogResponse> call = request.detilAlurLog(detilAlurLog);
        call.enqueue(new Callback<DetilAlurLogResponse>() {
            @Override
            public void onResponse(Call<DetilAlurLogResponse> call, Response<DetilAlurLogResponse> response) {
                recycleList(response.body());
            }

            @Override
            public void onFailure(Call<DetilAlurLogResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}
