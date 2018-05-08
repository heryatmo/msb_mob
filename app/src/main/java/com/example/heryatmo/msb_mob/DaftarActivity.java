package com.example.heryatmo.msb_mob;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class DaftarActivity extends AppCompatActivity {

    LinearLayout layVolu,layShel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        layVolu = findViewById(R.id.layVolunteer);
        layShel = findViewById(R.id.laySManager);

        layVolu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DaftarActivity.this,VolunteerActivity.class );
                startActivity(intent);
            }
        });

        layShel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
    }
}
