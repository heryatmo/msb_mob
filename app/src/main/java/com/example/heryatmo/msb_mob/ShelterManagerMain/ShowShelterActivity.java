package com.example.heryatmo.msb_mob.ShelterManagerMain;

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
import com.example.heryatmo.msb_mob.adapter.PostAdapter;
import com.example.heryatmo.msb_mob.adapter.ShelterAdapter;
import com.example.heryatmo.msb_mob.model.SemuaShelter;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.RetroClient;
import com.example.heryatmo.msb_mob.response.ShelterResponse;
import com.example.heryatmo.msb_mob.response.TestResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShowShelterActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    Button btnTambahShelter;
    String id_user;

    private List<SemuaShelter> data;

    private ShelterAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_shelter);

        recyclerView = findViewById(R.id.rv_list_shelter_sm);

        SharedPreferences sp = getSharedPreferences("SPLog", Context.MODE_PRIVATE);
        id_user  = sp.getString("id_user","-");

        btnTambahShelter = findViewById(R.id.btnTambahShelter);
        btnTambahShelter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowShelterActivity.this,FormShelterActivity.class );
                startActivity(intent);
            }
        });
        loadJSON();
    }

    private void recycleList(ShelterResponse shelterResponse){
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ShelterAdapter(shelterResponse.getMData());
        recyclerView.setAdapter(adapter);
    }

    private void loadJSON(){
        Retrofit retrofit = RetroClient.getClient();

        APIService request = retrofit.create(APIService.class);
        Call<ShelterResponse> call = request.getShelterSM(id_user);
        call.enqueue(new Callback<ShelterResponse>() {
            @Override
            public void onResponse(Call<ShelterResponse> call, Response<ShelterResponse> response) {
                recycleList(response.body());
            }

            @Override
            public void onFailure(Call<ShelterResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}
