package com.example.heryatmo.msb_mob.VolunteerMain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.ShelterManagerMain.FormShelterActivity;
import com.example.heryatmo.msb_mob.UserMain.MainActivity;
import com.example.heryatmo.msb_mob.UserMain.MapsShelterActivity;
import com.example.heryatmo.msb_mob.model.DaftarPeran;
import com.example.heryatmo.msb_mob.model.Jenis;
import com.example.heryatmo.msb_mob.model.Role;
import com.example.heryatmo.msb_mob.model.SemuaShelter;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.RetroClient;
import com.example.heryatmo.msb_mob.response.DaftarResponse;
import com.example.heryatmo.msb_mob.response.ShelterResponse;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VolunteerActivity extends AppCompatActivity {


    List<SemuaShelter> semuashelter = new ArrayList<>();

    List<Role> list;
    Spinner id_role, sShelter;
    String id_user;
    Button bDaftar, bPilShel;
    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer);

        id_role = (Spinner) findViewById(R.id.id);
        SharedPreferences sp = getSharedPreferences("SPLog", Context.MODE_PRIVATE);
        id_user = sp.getString("id_user", "-");
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

        bPilShel = findViewById(R.id.pickShelter);
        bPilShel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VolunteerActivity.this, MapsShelterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initSpinnerShelter() {

        sShelter = findViewById(R.id.spShelter);
        Retrofit retrofit = RetroClient.getClient();
        APIService request = retrofit.create(APIService.class);
        Call<ShelterResponse> call = request.getShelter();
        call.enqueue(new Callback<ShelterResponse>() {
            @Override
            public void onResponse(Call<ShelterResponse> call, Response<ShelterResponse> response) {
                semuashelter = response.body().getMData();
                List<String> listSpinner = new ArrayList<String>();
                for (int i = 0; i < semuashelter.size(); i++) {
                    listSpinner.add(semuashelter.get(i).getMNamaShelter());
                }
                if (getApplicationContext() != null) {
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

    private void daftarPeran() {
        String idShelter = String.valueOf(semuashelter.get(sShelter.getSelectedItemPosition()).getMIdShelter());
        DaftarPeran data = DaftarPeran.builder()
                .id_shelter(idShelter)
                .id_user(id_user)
                .id_role("3")
                .build();

        Retrofit retrofit = RetroClient.getClient();
        Call<DaftarResponse> call = retrofit.create(APIService.class).daftarPeranRequest(data, id_user);
        call.enqueue(new Callback<DaftarResponse>() {
            @Override
            public void onResponse(Call<DaftarResponse> call, @NonNull Response<DaftarResponse> response) {
                Log.d("wewwew", response.body().getMessage());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                finish();
            }

            @Override
            public void onFailure(Call<DaftarResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goPickShelter(View view){
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Intent intent = builder.build(VolunteerActivity.this);
            startActivityForResult(intent, PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            GooglePlayServicesUtil
                    .getErrorDialog(e.getConnectionStatusCode(), VolunteerActivity.this, 0);
        } catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(VolunteerActivity.this, "Google Play Services is not available.",
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {

            }
        }

    }
}
