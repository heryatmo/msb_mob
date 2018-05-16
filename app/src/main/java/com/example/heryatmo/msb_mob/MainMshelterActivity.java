package com.example.heryatmo.msb_mob;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainMshelterActivity extends AppCompatActivity {

    LinearLayout shelter,logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mshelter);

        shelter = findViewById(R.id.layShelter);
        shelter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMshelterActivity.this,ShowShelterActivity.class );
                startActivity(intent);
            }
        });
        logout = findViewById(R.id.logoutMShelter);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("SPLog", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("email","-");
                editor.putString("password","-");
                editor.putString("id_role", "-");
                editor.commit();
                Intent intent = new Intent(MainMshelterActivity.this,LoginActivity.class );
                startActivity(intent);
                finish();
            }
        });
    }
}
