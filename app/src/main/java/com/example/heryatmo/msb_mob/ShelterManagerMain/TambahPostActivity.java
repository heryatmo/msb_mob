package com.example.heryatmo.msb_mob.ShelterManagerMain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.model.Post;
import com.example.heryatmo.msb_mob.model.TambahPost;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.RetroClient;
import com.example.heryatmo.msb_mob.response.PengumumanResponse;
import com.example.heryatmo.msb_mob.response.TestResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TambahPostActivity extends AppCompatActivity {

    EditText tvPost;
    Button btnSubPo;
    String id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_post);

        SharedPreferences sp = getSharedPreferences("SPLog", Context.MODE_PRIVATE);
        id_user  = sp.getString("id_user","-");

        tvPost = findViewById(R.id.txtPostPengumuman);
        btnSubPo = findViewById(R.id.btnSubmitPost);
        btnSubPo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("id user", id_user);
                tambahPost();
                Intent intent = new Intent(TambahPostActivity.this,MainMshelterActivity.class );
                startActivity(intent);
            }
        });

    }

    private void tambahPost(){
        TambahPost tbhPost = TambahPost.builder()
                .mIdUser(id_user)
                .mPost(tvPost.getText().toString())
                .build();

        Retrofit retrofit = RetroClient.getClient();
        Call<PengumumanResponse> call = retrofit.create(APIService.class).tambahPost(tbhPost,id_user);
        call.enqueue(new Callback<PengumumanResponse>() {
            @Override
            public void onResponse(Call<PengumumanResponse> call, Response<PengumumanResponse> response) {

                finish();
            }

            @Override
            public void onFailure(Call<PengumumanResponse> call, Throwable t) {

            }
        });
    }
}
