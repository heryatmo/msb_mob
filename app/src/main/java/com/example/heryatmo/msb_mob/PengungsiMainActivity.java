package com.example.heryatmo.msb_mob;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PengungsiMainActivity extends AppCompatActivity {

    Button btnTambahPenngungsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengungsi_main);

        btnTambahPenngungsi = findViewById(R.id.btnTambahPengungsi);
        btnTambahPenngungsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PengungsiMainActivity.this,PengungsiActivity.class );
                startActivity(intent);
            }
        });
    }
}
