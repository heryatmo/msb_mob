package com.example.heryatmo.msb_mob;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout lydaftar,lyinfo,lydonasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lydaftar = findViewById(R.id.layDaftar);
        lyinfo = findViewById(R.id.layInformasi);
        lydonasi = findViewById(R.id.layDonasi);

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
                Intent intent = new Intent(MainActivity.this,DonasiActivity.class );
                startActivity(intent);
            }
        });
    }
}
