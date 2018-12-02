package com.example.heryatmo.msb_mob.ShelterManagerMain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.heryatmo.msb_mob.LaporanActivity;
import com.example.heryatmo.msb_mob.LaporanPengungsiActivity;
import com.example.heryatmo.msb_mob.Login.LoginActivity;
import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.UserMain.ProfileActivity;
import com.example.heryatmo.msb_mob.VolunteerMain.VolunteerMainActivity;

public class MainMshelterActivity extends AppCompatActivity {

    LinearLayout shelter,profileMShelter,dataVol,layLaporan,lyPostMS, lyPeng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mshelter);

        dataVol = findViewById(R.id.layDataVolunteer);
        dataVol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMshelterActivity.this,DaftarVolunteerMainActivity.class );
                startActivity(intent);
            }
        });

        shelter = findViewById(R.id.layShelter);
        shelter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMshelterActivity.this,ShowShelterActivity.class );
                startActivity(intent);
            }
        });
        profileMShelter = findViewById(R.id.layProfileMShelter);
        profileMShelter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMshelterActivity.this,ProfileActivity.class );
                startActivity(intent);
            }
        });
        layLaporan = findViewById(R.id.layReport);
        layLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMshelterActivity.this,LaporanActivity.class );
                startActivity(intent);
            }
        });
        lyPostMS = findViewById(R.id.layPostInformasi);
        lyPostMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMshelterActivity.this,PostMShelterActivity.class );
                startActivity(intent);
            }
        });
        lyPeng = findViewById(R.id.layDataPengungsi);
        lyPeng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMshelterActivity.this,LaporanActivity.class );
                startActivity(intent);
            }
        });
    }
}
