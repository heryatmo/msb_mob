package com.example.heryatmo.msb_mob.VolunteerMain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.heryatmo.msb_mob.Login.LoginActivity;
import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.model.DistribusiLogistik;
import com.example.heryatmo.msb_mob.model.RegisterResponse;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.RetroClient;
import com.example.heryatmo.msb_mob.response.DistribusiLogistikResponse;
import com.example.heryatmo.msb_mob.response.ShelterResponse;
import com.example.heryatmo.msb_mob.response.TampilPengungsiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DistribusiLogistikActivity extends AppCompatActivity {

    //UI
    LinearLayout lyGantiPassword,lyLogout;
    TextView txtNamaLogistik, txtKeteranganLogistik;
    TextView txtNamaShelter, txtNamaUser, txtJumlahDistribusi, txtKeteranganDistribusi;

    //User
    String id_user="", nama_user="", id_role="", nama_role="";
    String jenis_kelamin="", golongan_darah="";
    String tempat_lahir="", tanggal_lahir="";
    String alamat="", email="", no_hp="";
    String id_shelter="", nama_shelter="";

    //Logistik
    String id_logistik="", nama_logistik="", jumlah_logistik="", keterangan_logistik="";
    String jumlah_distribusi, keterangan_distribusi;

    //Button
    Button btnSubmitDistribusi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribusi_logistik);

        setDistribusiLogistik();

        btnSubmitDistribusi =  findViewById(R.id.btnSubmitDistribusi);
        btnSubmitDistribusi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitDistribusi();
            }
        });
    }

    private void loadUser(){
        SharedPreferences sp = getSharedPreferences("SPUser", Context.MODE_PRIVATE);
        this.id_user = sp.getString("id_user","-");
        this.nama_user = sp.getString("nama_user","-");
        this.id_role = sp.getString("id_role","-");
        this.nama_role = sp.getString("nama_role","-");

        this.jenis_kelamin = sp.getString("jenis_kelamin","-");
        this.golongan_darah = sp.getString("golongan_darah","-");

        this.tempat_lahir = sp.getString("tempat_lahir","-");
        this.tanggal_lahir = sp.getString("tanggal_lahir","-");

        this.alamat = sp.getString("alamat","-");
        this.email = sp.getString("email","-");
        this.no_hp = sp.getString("tanggal_lahir","-");
    }

    private void loadLogistik(){
        Intent intent = getIntent();
        this.id_logistik = intent.getStringExtra("id_logistik");
        this.nama_logistik = intent.getStringExtra("nama_logistik");
        this.jumlah_logistik = intent.getStringExtra("jumlah_logistik");
        this.keterangan_logistik = intent.getStringExtra("keterangan_logistik");
    }

    private void loadShelter(){
        Retrofit retrofit = RetroClient.getClient();

        APIService request = retrofit.create(APIService.class);
        Call<ShelterResponse> call = request.getShelterSM(id_user);
        call.enqueue(new Callback<ShelterResponse>() {
            @Override
            public void onResponse(Call<ShelterResponse> call, Response<ShelterResponse> response) {
                id_shelter = response.body().getMData().get(0).getMIdShelter() + "";
                nama_shelter = response.body().getMData().get(0).getMNamaShelter() + "";

                txtNamaShelter.setText(nama_shelter);
                txtNamaUser.setText(nama_user);
            }

            @Override
            public void onFailure(Call<ShelterResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    private void setDistribusiLogistik(){
        loadUser();
        loadLogistik();

        txtNamaLogistik = findViewById(R.id.txtNamaLogistik);
        txtKeteranganLogistik = findViewById(R.id.txtKeteranganLogistik);

        txtNamaShelter = findViewById(R.id.txtNamaShelter);
        txtNamaUser = findViewById(R.id.txtNamaUser);
        txtJumlahDistribusi = findViewById(R.id.txtJumlahDistribusi);
        txtKeteranganDistribusi = findViewById(R.id.txtKeteranganDistribusi);

        txtNamaLogistik.setText(nama_logistik + " (" + jumlah_logistik + ")");
        txtKeteranganLogistik.setText(keterangan_logistik);

        loadShelter();
    }

    private void submitDistribusi(){
        jumlah_logistik = txtJumlahDistribusi.getText().toString();
        keterangan_distribusi = txtKeteranganDistribusi.getText().toString();
        DistribusiLogistik dataDistribusi = DistribusiLogistik.builder()
                .mId_logistik(id_logistik)
                .mId_user(id_user)
                .mId_shelter(id_shelter)
                .mJumlah_logistik(jumlah_logistik)
                .mKeterangan(keterangan_distribusi)
                .build();
        Retrofit retrofit = RetroClient.getClient();
        Call<DistribusiLogistikResponse> call = retrofit.create(APIService.class).requestDistribusiLogistik(dataDistribusi);
        call.enqueue(new Callback<DistribusiLogistikResponse>() {
            @Override
            public void onResponse(Call<DistribusiLogistikResponse> call, Response<DistribusiLogistikResponse> response) {
                Intent intent = new Intent(getApplicationContext(),VolunteerMainActivity.class );
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<DistribusiLogistikResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}
