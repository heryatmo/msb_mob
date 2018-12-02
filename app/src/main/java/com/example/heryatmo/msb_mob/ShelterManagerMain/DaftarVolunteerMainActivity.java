package com.example.heryatmo.msb_mob.ShelterManagerMain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.UserMain.DaftarActivity;
import com.example.heryatmo.msb_mob.UserMain.MapsShelterActivity;
import com.example.heryatmo.msb_mob.adapter.DaftarVolunteerKeterimaAdapter;

public class DaftarVolunteerMainActivity extends AppCompatActivity {

    LinearLayout layCalVolu, layDatVol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_volunteer_main);

        layCalVolu = findViewById(R.id.layCalonVolunteer);
        layCalVolu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DaftarVolunteerMainActivity.this,DaftarVolunteerActivity.class );
                startActivity(intent);
            }
        });

        layDatVol = findViewById(R.id.layVolunteerTerkini);
        layDatVol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DaftarVolunteerMainActivity.this,DaftarValonteerKeterimaActivity.class );
                startActivity(intent);
            }
        });
    }
}
