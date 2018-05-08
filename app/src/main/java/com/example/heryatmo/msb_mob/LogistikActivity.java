package com.example.heryatmo.msb_mob;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.model.Logistik;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.RetroClient;
import com.example.heryatmo.msb_mob.response.LogistikResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LogistikActivity extends AppCompatActivity {

    TextView tvNamaLogistik,tvJumlahLogistik,tvKeterangan;
    String id_user;
    Button bSubmitLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistik);

        SharedPreferences sp = getSharedPreferences("SPLog", Context.MODE_PRIVATE);
        id_user  = sp.getString("id_user","-");

        tvNamaLogistik = findViewById(R.id.txtNamaLog);
        tvJumlahLogistik = findViewById(R.id.txtJumlahLog);
        tvKeterangan = findViewById(R.id.txtKeteranganLog);
        bSubmitLog = findViewById(R.id.btnSubmitLog);
        bSubmitLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createLogistik();
            }
        });
    }

    private void createLogistik(){
        Logistik dataLogistik = Logistik.builder()
                .mIdUser(id_user)
                .mNamaLogistik(tvNamaLogistik.getText().toString())
                .mJumlahLogistik(tvJumlahLogistik.getText().toString())
                .mKeterangan(tvKeterangan.getText().toString())
                .build();

        Retrofit retrofit = RetroClient.getClient();
        Call<LogistikResponse> call = retrofit.create(APIService.class).logistikRequest(dataLogistik);
        call.enqueue(new Callback<LogistikResponse>() {
            @Override
            public void onResponse(Call<LogistikResponse> call, Response<LogistikResponse> response) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class );
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<LogistikResponse> call, Throwable t) {
                Log.i("Failed","Insert Gagal");
                Toast.makeText(getBaseContext(), "Data Gagal Masuk", Toast.LENGTH_LONG).show();
            }
        });
    }
}
