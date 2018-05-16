package com.example.heryatmo.msb_mob;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.heryatmo.msb_mob.model.DaftarPeran;
import com.example.heryatmo.msb_mob.model.Role;
import com.example.heryatmo.msb_mob.model.SemuaShelter;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.RetroClient;
import com.example.heryatmo.msb_mob.response.DaftarResponse;
import com.example.heryatmo.msb_mob.response.ShelterResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VolunteerActivity extends AppCompatActivity {

    List<Role> list;
    Spinner id_role,sShelter;
    String  id_user;
    Button bDaftar,bCek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer);

        id_role = (Spinner) findViewById(R.id.id);
        SharedPreferences sp = getSharedPreferences("SPLog", Context.MODE_PRIVATE);
        id_user  = sp.getString("id_user","-");
        initSpinnerShelter();

        sShelter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedName = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Role role = Role.builder()
                .mIdRole(Long.parseLong("3"))
                .mNamaRole("volunteer")
                .build();


        list = new ArrayList<>();
        list.add(role);

        List<String> stringList = new ArrayList<>();
        stringList.add(role.getMNamaRole());
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.support_simple_spinner_dropdown_item, stringList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        id_role.setAdapter(spinnerArrayAdapter);


        bDaftar = findViewById(R.id.daftarPeran);
        bDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daftarPeran();
            }
        });

        bCek = findViewById(R.id.btncekid);
        bCek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Id User :" + id_user, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initSpinnerShelter(){

        sShelter = findViewById(R.id.spShelter);
        Retrofit retrofit = RetroClient.getClient();
        APIService request = retrofit.create(APIService.class);
        Call<ShelterResponse> call = request.getShelter();
        call.enqueue(new Callback<ShelterResponse>() {
            @Override
            public void onResponse(Call<ShelterResponse> call, Response<ShelterResponse> response) {
                List<SemuaShelter> semuaShelter = response.body().getMData();
                List<String> listSpinner = new ArrayList<String>();
                for(int i=0; i < semuaShelter.size() ; i++){
                    listSpinner.add(semuaShelter.get(i).getMNamaShelter());
                }
                if(getApplicationContext()!=null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                            R.layout.support_simple_spinner_dropdown_item, listSpinner);
                    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    sShelter.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<ShelterResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void daftarPeran(){
        DaftarPeran data = DaftarPeran.builder()
                .mIdShelter(sShelter.getSelectedItem().toString())
                .mIdUser(id_user)
                .mIdRole(id_role.getSelectedItem().toString())
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
                Toast.makeText(getApplicationContext(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
