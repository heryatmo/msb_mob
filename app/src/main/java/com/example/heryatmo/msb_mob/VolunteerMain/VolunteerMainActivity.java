package com.example.heryatmo.msb_mob.VolunteerMain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.heryatmo.msb_mob.Login.LoginActivity;
import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.UserMain.InfoActivity;
import com.example.heryatmo.msb_mob.UserMain.ProfileActivity;

public class VolunteerMainActivity extends AppCompatActivity {

    LinearLayout layPengungsi,profilVolunteer,layLogistik,layinformasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_main);

        layPengungsi = findViewById(R.id.layPengungsi);
        layPengungsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VolunteerMainActivity.this,PengungsiMainActivity.class );
                startActivity(intent);
            }
        });

        profilVolunteer = findViewById(R.id.layProfileVolunteer);
        profilVolunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VolunteerMainActivity.this,ProfileActivity.class );
                startActivity(intent);
            }
        });

        layLogistik = findViewById(R.id.layDisLogistik);
        layLogistik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VolunteerMainActivity.this,TampilLogistikActivity.class );
                startActivity(intent);
            }
        });

        layinformasi = findViewById(R.id.layInformasi);
        layinformasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VolunteerMainActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });
    }
}
