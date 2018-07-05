package com.example.heryatmo.msb_mob.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.model.LupaPassword;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.RetroClient;
import com.example.heryatmo.msb_mob.response.LupaPasswordResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LupaPassActivity extends AppCompatActivity {

    EditText edLupaPass;
    Button btnLupaPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_pass);

        edLupaPass = findViewById(R.id.txtEmailPass);
        btnLupaPass = findViewById(R.id.btnSubmitPass);
    }

    private void lupaPass(){
        LupaPassword lupaPassword = LupaPassword.builder()
                .mEmail(edLupaPass.getText().toString())
                .build();

        Retrofit retrofit = RetroClient.getClient();
        Call<LupaPasswordResponse> call = retrofit.create(APIService.class).lupaPassword(lupaPassword);
        call.enqueue(new Callback<LupaPasswordResponse>() {
            @Override
            public void onResponse(Call<LupaPasswordResponse> call, Response<LupaPasswordResponse> response) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class );
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<LupaPasswordResponse> call, Throwable t) {

            }
        });
    }
}

