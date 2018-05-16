package com.example.heryatmo.msb_mob.ShelterManagerMain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.heryatmo.msb_mob.R;

public class ShowShelterActivity extends AppCompatActivity {

    Button btnTambahShelter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_shelter);

        btnTambahShelter = findViewById(R.id.btnTambahShelter);
        btnTambahShelter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowShelterActivity.this,FormShelterActivity.class );
                startActivity(intent);
            }
        });
    }
}
