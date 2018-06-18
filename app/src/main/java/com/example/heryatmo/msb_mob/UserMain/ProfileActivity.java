package com.example.heryatmo.msb_mob.UserMain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.heryatmo.msb_mob.Login.LoginActivity;
import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.ShelterManagerMain.MainMshelterActivity;
import com.example.heryatmo.msb_mob.VolunteerMain.VolunteerMainActivity;

public class ProfileActivity extends AppCompatActivity {

    //UI
    LinearLayout lyGantiPassword,lyLogout;
    TextView tvNama, tvNamaRole;
    TextView tvJenisKelamin, tvGolonganDarah;
    TextView tvTempatTanggalLahir;
    TextView tvAlamat, tvEmail, tvNoHP;

    //User
    String id_user="", nama_user="", id_role="", nama_role="";
    String jenis_kelamin="", golongan_darah="";
    String tempat_lahir="", tanggal_lahir="";
    String alamat="", email="", no_hp="";
    String id_shelter="", nama_shelter="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setProfile();

        lyGantiPassword = findViewById(R.id.layGantiPassword);
        lyGantiPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, LogistikActivity.class );
                startActivity(intent);
            }
        });

        lyLogout = findViewById(R.id.layLogout);
        lyLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("SPLog", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("email","-");
                editor.putString("password","-");
                editor.putString("id_role", "-");
                editor.commit();
                Intent intent = new Intent(ProfileActivity.this,LoginActivity.class );
                startActivity(intent);
                finish();
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

        this.id_shelter = sp.getString("id_shelter","-");
        this.nama_shelter = sp.getString("nama_shelter","-");
    }

    private void setProfile(){
        loadUser();

        tvNama = findViewById(R.id.tvNama);
        tvNamaRole = findViewById(R.id.tvNamaRole);

        tvJenisKelamin = findViewById(R.id.tvJenisKelamin);
        tvGolonganDarah = findViewById(R.id.tvGolonganDarah);

        tvTempatTanggalLahir = findViewById(R.id.tvTempatTanggalLahir);

        tvAlamat = findViewById(R.id.tvAlamat);
        tvEmail = findViewById(R.id.tvEmail);
        tvNoHP = findViewById(R.id.tvNoHP);

        tvNama.setText(nama_user);
        tvNamaRole.setText(nama_role);

        tvTempatTanggalLahir.setText(tempat_lahir + ", " + tanggal_lahir);

        tvAlamat.setText(alamat);
        tvEmail.setText(email);
        tvNoHP.setText(no_hp);
    }
}
