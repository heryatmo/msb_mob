package com.example.heryatmo.msb_mob.VolunteerMain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.heryatmo.msb_mob.R;

public class PengungsiActivity extends AppCompatActivity {

    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengungsi);

        btnSubmit = findViewById(R.id.btnSubmitPengungsi);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PengungsiActivity.this,VolunteerMainActivity.class );
                startActivity(intent);
            }
        });
    }
}
