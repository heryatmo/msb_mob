package com.example.heryatmo.msb_mob.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.UserMain.MainActivity;
import com.example.heryatmo.msb_mob.model.UbahPassword;
import com.example.heryatmo.msb_mob.model.User;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.RetroClient;
import com.example.heryatmo.msb_mob.response.UbahPasswordResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GantiPasswordActivity extends AppCompatActivity {

    EditText passLama, passBaru;
    String id_user;
    Button submitPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganti_password);

        SharedPreferences sp = getSharedPreferences("SPLog", Context.MODE_PRIVATE);
        id_user  = sp.getString("id_user","-");

        passLama = findViewById(R.id.txtPasswordLama);
        passBaru = findViewById(R.id.txtPasswordBaru);
        submitPass = findViewById(R.id.btnSubmitPass);

//        gantiPass();
    }

//    private void gantiPass(){
//        UbahPassword ubahPassword = UbahPassword.builder()
//                .mIdUser(id_user)
//                .mPasswordLama(passLama.getText().toString())
//                .mPasswordBaru(passBaru.getText().toString())
//                .build();
//
//        Retrofit retrofit = RetroClient.getClient();
//        Call<UbahPasswordResponse> call = retrofit.create(APIService.class).ubahPassword(ubahPassword,id_user);
//        call.enqueue(new Callback<UbahPasswordResponse>() {
//            @Override
//            public void onResponse(Call<UbahPasswordResponse> call, Response<UbahPasswordResponse> response) {
//                Intent intent = new Intent(getApplicationContext(),MainActivity.class );
//                startActivity(intent);
//                finish();
//            }
//
//            @Override
//            public void onFailure(Call<UbahPasswordResponse> call, Throwable t) {
//
//            }
//        });
//    }
}
