package com.example.heryatmo.msb_mob.VolunteerMain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.adapter.DaftarVolunteerAdapter;
import com.example.heryatmo.msb_mob.adapter.PengungsiAdapter;

public class PengungsiMainActivity extends AppCompatActivity {

    Button btnTambahPengungsi;
    private RecyclerView recyclerView;
    private PengungsiAdapter adapter;
    String id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengungsi_main);

        btnTambahPengungsi = findViewById(R.id.btnTambahPengungsi);
        btnTambahPengungsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PengungsiMainActivity.this,PengungsiActivity.class );
                startActivity(intent);
            }
        });
    }
}
