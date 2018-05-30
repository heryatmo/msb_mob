package com.example.heryatmo.msb_mob.ShelterManagerMain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.UserMain.MainActivity;
import com.example.heryatmo.msb_mob.model.DaftarPeran;
import com.example.heryatmo.msb_mob.model.Role;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.RetroClient;
import com.example.heryatmo.msb_mob.response.DaftarResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MShelterActivity extends AppCompatActivity {

    String id_user;
    Spinner id_role;
    List<Role> list;
    Button bDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mshelter);

        SharedPreferences sp = getSharedPreferences("SPLog", Context.MODE_PRIVATE);
        id_user  = sp.getString("id_user","-");
        id_role = findViewById(R.id.idrole);
        bDaftar = findViewById(R.id.daftarMShelter);
        bDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daftarMShelter();
            }
        });


        Role role = Role.builder()
                .mIdRole(Long.parseLong("2"))
                .mNamaRole("shelter manager")
                .build();


        list = new ArrayList<>();
        list.add(role);

        List<String> stringList = new ArrayList<>();
        stringList.add(role.getMNamaRole());
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.support_simple_spinner_dropdown_item, stringList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        id_role.setAdapter(spinnerArrayAdapter);
    }

    private void daftarMShelter(){
        DaftarPeran data = DaftarPeran.builder()
                .id_user(id_user)
                .id_role(id_role.getSelectedItem().toString())
                .build();

        Retrofit retrofit = RetroClient.getClient();
        Call<DaftarResponse> call = retrofit.create(APIService.class).daftarPeranRequest(data,id_user);
        call.enqueue(new Callback<DaftarResponse>() {
            @Override
            public void onResponse(Call<DaftarResponse> call, Response<DaftarResponse> response) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class );
                startActivity(intent);

                finish();
            }

            @Override
            public void onFailure(Call<DaftarResponse> call, Throwable t) {

            }
        });
    }
}
