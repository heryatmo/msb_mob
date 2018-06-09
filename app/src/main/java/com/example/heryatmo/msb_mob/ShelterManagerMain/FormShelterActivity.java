package com.example.heryatmo.msb_mob.ShelterManagerMain;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.UserMain.MainActivity;
import com.example.heryatmo.msb_mob.model.Bencana;
import com.example.heryatmo.msb_mob.model.Posisi;
import com.example.heryatmo.msb_mob.model.SemuaShelter;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.RetroClient;
import com.example.heryatmo.msb_mob.response.BencanaResponse;
import com.example.heryatmo.msb_mob.response.LogistikResponse;
import com.example.heryatmo.msb_mob.response.PosisiResponse;
import com.example.heryatmo.msb_mob.response.ShelterResponse;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FormShelterActivity extends AppCompatActivity {


    int PLACE_PICKER_REQUEST = 1;
    TextView tvNamaShelter, tvAlamatShelter;
    Button btnSimpan;
    Intent test;
    Spinner sBencana;
    Double lat, lng;
    String id_user;
    Place place;
    List<Bencana> semuaBencana = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_shelter);

        tvNamaShelter = findViewById(R.id.txtNamaShelter);
        tvAlamatShelter = findViewById(R.id.txtWilayahShelter);

        SharedPreferences sp = getSharedPreferences("SPLog", Context.MODE_PRIVATE);
        id_user = sp.getString("id_user", "-");

        lat = 0.0;
        lng = 0.0;
        test = new Intent();
        initSpinnerBencana();

        btnSimpan = findViewById(R.id.simpanShelter);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createShelter();
            }
        });
    }

    protected void goPlacePicker(View view) {
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Intent intent = builder.build(FormShelterActivity.this);
            startActivityForResult(intent, PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            GooglePlayServicesUtil
                    .getErrorDialog(e.getConnectionStatusCode(), FormShelterActivity.this, 0);
        } catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(FormShelterActivity.this, "Google Play Services is not available.",
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                place = PlacePicker.getPlace(FormShelterActivity.this, data);
                Toast.makeText(FormShelterActivity.this, place.getLatLng().latitude
                        + " # "
                        + place.getLatLng().longitude, Toast.LENGTH_SHORT).show();
                tvAlamatShelter.setText(place.getAddress());

            }
        }

    }

    private void initSpinnerBencana() {

        sBencana = findViewById(R.id.spBencana);
        Retrofit retrofit = RetroClient.getClient();
        APIService request = retrofit.create(APIService.class);
        Call<BencanaResponse> call = request.getBencana();
        call.enqueue(new Callback<BencanaResponse>() {
            @Override
            public void onResponse(Call<BencanaResponse> call, Response<BencanaResponse> response) {
                semuaBencana = response.body().getMData();
                List<String> listSpinner = new ArrayList<String>();
                for (int i = 0; i < semuaBencana.size(); i++) {
                    listSpinner.add(semuaBencana.get(i).getMNamaBencana());
                }
                if (getApplicationContext() != null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                            R.layout.support_simple_spinner_dropdown_item, listSpinner);
                    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    sBencana.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<BencanaResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void createShelter(){
        String idBencana = String.valueOf(semuaBencana.get(sBencana.getSelectedItemPosition()).getMIdBencana());

        Posisi dataPosisi = Posisi.builder()
                .mIdBencana(idBencana)
                .mIdUser(id_user)
                .mLat(place.getLatLng().latitude)
                .mLng(place.getLatLng().longitude)
                .mNamaShelter(tvNamaShelter.getText().toString())
                .mWilayahShelter(tvAlamatShelter.getText().toString())
                .build();
        Log.d("POSISI", dataPosisi.toString());
        Retrofit retrofit = RetroClient.getClient();
        Call<PosisiResponse> call = retrofit.create(APIService.class).shelterRequest(dataPosisi);
        call.enqueue(new Callback<PosisiResponse>() {
            @Override
            public void onResponse(Call<PosisiResponse> call, Response<PosisiResponse> response) {
                Intent intent = new Intent(getApplicationContext(),MainMshelterActivity.class );
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<PosisiResponse> call, Throwable t) {
                Log.i("Failed",t.toString());
                Toast.makeText(getBaseContext(), " Gagal Masuk", Toast.LENGTH_LONG).show();
            }
        });
    }


}
