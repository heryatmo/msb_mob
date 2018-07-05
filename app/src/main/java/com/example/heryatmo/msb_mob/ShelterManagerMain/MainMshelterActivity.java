package com.example.heryatmo.msb_mob.ShelterManagerMain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.heryatmo.msb_mob.Login.LoginActivity;
import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.UserMain.ProfileActivity;
import com.example.heryatmo.msb_mob.VolunteerMain.VolunteerMainActivity;

public class MainMshelterActivity extends AppCompatActivity {

    LinearLayout shelter,profileMShelter,dataVol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mshelter);

        dataVol = findViewById(R.id.layDataVolunteer);
        dataVol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMshelterActivity.this,DaftarVolunteerActivity.class );
                startActivity(intent);
            }
        });

        shelter = findViewById(R.id.layShelter);
        shelter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMshelterActivity.this,FormShelterActivity.class );
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
    }
}
