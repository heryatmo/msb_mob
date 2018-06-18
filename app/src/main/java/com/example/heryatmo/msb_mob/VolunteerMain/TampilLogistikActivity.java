package com.example.heryatmo.msb_mob.VolunteerMain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.adapter.LogistikAdapter;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.RetroClient;
import com.example.heryatmo.msb_mob.response.TampilLogistikResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TampilLogistikActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LogistikAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_logistik);
        recyclerView = findViewById(R.id.rv_logistik_list);
        loadJSON();
    }

    private void recycleList(TampilLogistikResponse daftarLogistik){
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new LogistikAdapter(daftarLogistik.getMData(), this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void loadJSON(){
        Retrofit retrofit = RetroClient.getClient();

        APIService request = retrofit.create(APIService.class);
        Call<TampilLogistikResponse> call = request.getLogistik();
        call.enqueue(new Callback<TampilLogistikResponse>() {
            @Override
            public void onResponse(Call<TampilLogistikResponse> call, Response<TampilLogistikResponse> response) {
                recycleList(response.body());
            }

            @Override
            public void onFailure(Call<TampilLogistikResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}
