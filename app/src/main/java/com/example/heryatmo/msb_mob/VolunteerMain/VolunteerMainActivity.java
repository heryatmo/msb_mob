package com.example.heryatmo.msb_mob.VolunteerMain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.heryatmo.msb_mob.Login.LoginActivity;
import com.example.heryatmo.msb_mob.R;

public class VolunteerMainActivity extends AppCompatActivity {

    LinearLayout layPengungsi,logout,layLogistik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_main);

        layPengungsi = findViewById(R.id.layPengungsi);
        layPengungsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VolunteerMainActivity.this,PengungsiMainActivity.class );
                startActivity(intent);
            }
        });

        logout = findViewById(R.id.logoutVolunteer);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("SPLog", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("email","-");
                editor.putString("password","-");
                editor.putString("id_role", "-");
                editor.commit();
                Intent intent = new Intent(VolunteerMainActivity.this,LoginActivity.class );
                startActivity(intent);
                finish();
            }
        });

        layLogistik = findViewById(R.id.layDisLogistik);
        layLogistik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VolunteerMainActivity.this,TampilLogistikActivity.class );
                startActivity(intent);
            }
        });
    }
}
