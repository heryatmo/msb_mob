package com.example.heryatmo.msb_mob.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.heryatmo.msb_mob.UserMain.MainActivity;
import com.example.heryatmo.msb_mob.ShelterManagerMain.MainMshelterActivity;
import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.VolunteerMain.VolunteerMainActivity;

public class SplashScreen extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadPreferences();
            }
        },3000L);
    }

    private void loadPreferences(){
        SharedPreferences sp = getSharedPreferences("SPLog", Context.MODE_PRIVATE);
        String email = sp.getString("email","-");
        String password = sp.getString("password","-");
        String id_role = sp.getString("id_role","-");
        if(!email.equals("-") && !password.equals("-") && !id_role.equals("-")){
            if(id_role.equalsIgnoreCase("3")){
                Intent intent = new Intent(getApplicationContext(),VolunteerMainActivity.class );
                startActivity(intent);
                finish();
            }else if(id_role.equalsIgnoreCase("4")){
                Intent intent = new Intent(getApplicationContext(),MainActivity.class );
                startActivity(intent);
                finish();
            }else if(id_role.equalsIgnoreCase("2")){
                Intent intent = new Intent(getApplicationContext(),MainMshelterActivity.class );
                startActivity(intent);
                finish();
            }
        }
        else{
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }


    }


}
