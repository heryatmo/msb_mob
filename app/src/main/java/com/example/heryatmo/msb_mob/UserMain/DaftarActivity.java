package com.example.heryatmo.msb_mob.UserMain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.heryatmo.msb_mob.Login.LoginActivity;
import com.example.heryatmo.msb_mob.ShelterManagerMain.MShelterActivity;
import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.VolunteerMain.VolunteerActivity;

public class DaftarActivity extends AppCompatActivity {

    LinearLayout layVolu,layShel,laySM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        layVolu = findViewById(R.id.layVolunteer);
        layShel = findViewById(R.id.laySManager);
        laySM = findViewById(R.id.laySManager);

        laySM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DaftarActivity.this,MShelterActivity.class );
                startActivity(intent);
            }
        });

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
                SharedPreferences sp = getSharedPreferences("SPLog", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("email","-");
                editor.putString("password","-");
                editor.putString("id_role", "-");
                editor.commit();
                Intent intent = new Intent(DaftarActivity.this,LoginActivity.class );
                startActivity(intent);
                finish();
            }
        });


    }
}
