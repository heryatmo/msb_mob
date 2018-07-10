package com.example.heryatmo.msb_mob.ShelterManagerMain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.model.Post;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_post);

        tvPost = findViewById(R.id.txtPostPengumuman);
        tambahPost();
    }

    private void tambahPost(){
        Post tambahPost = Post.builder()
                .mKebutuhan(tvPost.getText().toString())
                .build();

        Retrofit retrofit = RetroClient.getClient();
        Call<PengumumanResponse> call = retrofit.create(APIService.class).tambahPost(tambahPost);
        call.enqueue(new Callback<PengumumanResponse>() {
            @Override
            public void onResponse(Call<PengumumanResponse> call, Response<PengumumanResponse> response) {
                Intent intent = new Intent(getApplicationContext(),MainMshelterActivity.class );
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<PengumumanResponse> call, Throwable t) {

            }
        });
    }
}
