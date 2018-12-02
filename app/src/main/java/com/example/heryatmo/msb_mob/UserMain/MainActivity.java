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

public class MainActivity extends AppCompatActivity {

    LinearLayout lydaftar,lyinfo,lydonasi,lyprofile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lydaftar = findViewById(R.id.layDaftar);
        lyinfo = findViewById(R.id.layInformasi);
        lydonasi = findViewById(R.id.layDonasi);
        lyprofile = findViewById(R.id.layProfile);
        final TextView tvTest = findViewById(R.id.tvTest);

        lydaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DaftarActivity.class );
                startActivity(intent);
            }
        });

        lyinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,InfoActivity.class );
                startActivity(intent);
            }
        });

        lydonasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AlurLogActivity.class );
                startActivity(intent);
            }
        });

        lyprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ProfileActivity.class );
                startActivity(intent);
            }
        });
    }
}
