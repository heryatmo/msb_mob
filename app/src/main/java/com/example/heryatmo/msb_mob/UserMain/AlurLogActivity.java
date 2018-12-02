package com.example.heryatmo.msb_mob.UserMain;

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
import com.example.heryatmo.msb_mob.adapter.AlurLogAdapter;
import com.example.heryatmo.msb_mob.adapter.DaftarVolunteerAdapter;
import com.example.heryatmo.msb_mob.model.AlurLog;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.RetroClient;
import com.example.heryatmo.msb_mob.response.AlurLogResponse;
import com.example.heryatmo.msb_mob.response.DaftarCalonVolunteerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AlurLogActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AlurLogAdapter adapter;
    String id_user;
    Button btnTbhLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alur_log);

        btnTbhLog = findViewById(R.id.btnTambahLogistik);
        btnTbhLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlurLogActivity.this,LogistikActivity.class );
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.rv_list_alur_log);
        SharedPreferences sp = getSharedPreferences("SPLog", Context.MODE_PRIVATE);
        id_user  = sp.getString("id_user","-");
        loadJSON();
    }

    private void recycleList(AlurLogResponse alurResponse){
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new AlurLogAdapter(alurResponse.getMData(), this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void loadJSON(){
        Retrofit retrofit = RetroClient.getClient();

        APIService request = retrofit.create(APIService.class);
        AlurLog alurLog = AlurLog.builder().mIdUser(id_user).build();
        Call<AlurLogResponse> call = request.alurLog(alurLog);
        call.enqueue(new Callback<AlurLogResponse>() {
            @Override
            public void onResponse(Call<AlurLogResponse> call, Response<AlurLogResponse> response) {
                recycleList(response.body());
            }

            @Override
            public void onFailure(Call<AlurLogResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}
