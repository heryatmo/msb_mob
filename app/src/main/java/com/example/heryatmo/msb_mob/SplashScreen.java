package com.example.heryatmo.msb_mob;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.heryatmo.msb_mob.response.UserResponse;

public class SplashScreen extends AppCompatActivity {

    PermissionHelper permissionHelper;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        permissionHelper = new PermissionHelper(this);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkAndRequestPermissions();
                finish();
            }
        },3000L);
    }

//    private void loadPreferences(){
//
//
//
//    }

    private boolean checkAndRequestPermissions() {
        permissionHelper.permissionListener(new PermissionHelper.PermissionListener() {
            @Override
            public void onPermissionCheckDone() {
                SharedPreferences sp = getSharedPreferences("SPLog", Context.MODE_PRIVATE);
                String email = sp.getString("email","-");
                String password = sp.getString("password","-");
                String id_role = sp.getString("id_role","-");
                if(!email.equals("-") && !password.equals("-") && !id_role.equals("-")){
                    if(id_role == "3"){
                        Intent intent = new Intent(getApplicationContext(),VolunteerMainActivity.class );
                        startActivity(intent);

                        finish();
                    }else if(id_role == "4"){
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class );
                        startActivity(intent);

                        finish();
                    }
                }
                else{
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    finish();
                }
            }
        });

        permissionHelper.checkAndRequestPermissions();

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionHelper.onRequestCallBack(requestCode, permissions, grantResults);
    }

}
