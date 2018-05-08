package com.example.heryatmo.msb_mob;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.heryatmo.msb_mob.model.Logistik;

public class DonasiActivity extends AppCompatActivity {

    LinearLayout lyLogistik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donasi);

        lyLogistik = findViewById(R.id.layLogistik);
        lyLogistik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DonasiActivity.this, LogistikActivity.class );
                startActivity(intent);
            }
        });
    }
}
