package com.example.heryatmo.msb_mob.UserMain;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.heryatmo.msb_mob.Login.LoginActivity;
import com.example.heryatmo.msb_mob.ShelterManagerMain.MShelterActivity;
import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.VolunteerMain.VolunteerActivity;
import com.example.heryatmo.msb_mob.model.DaftarPeran;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.RetroClient;
import com.example.heryatmo.msb_mob.response.DaftarResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DaftarActivity extends AppCompatActivity {

    LinearLayout layVolu,layShel,laySM;
    String id_user;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        SharedPreferences sp = getSharedPreferences("SPLog", Context.MODE_PRIVATE);
        id_user = sp.getString("id_user", "-");

        layVolu = findViewById(R.id.layVolunteer);
        layShel = findViewById(R.id.laySManager);
        laySM = findViewById(R.id.laySManager);

        laySM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DaftarActivity.this);
                builder.setMessage("Daftar Sebagai Shelter Manager ?");
                builder.setPositiveButton("Daftar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        daftarSM();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        layVolu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DaftarActivity.this,MapsShelterActivity.class );
                startActivity(intent);
            }
        });

    }

    private void logout(){
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

    private void daftarSM() {
        DaftarPeran data = DaftarPeran.builder()
                .id_user(id_user)
                .id_role("2")
                .build();

        Retrofit retrofit = RetroClient.getClient();
        Call<DaftarResponse> call = retrofit.create(APIService.class).daftarPeranRequest(data, id_user);
        call.enqueue(new Callback<DaftarResponse>() {
            @Override
            public void onResponse(Call<DaftarResponse> call, @NonNull Response<DaftarResponse> response) {
              logout();
            }

            @Override
            public void onFailure(Call<DaftarResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
