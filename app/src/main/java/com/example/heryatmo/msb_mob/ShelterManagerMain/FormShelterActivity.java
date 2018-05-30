package com.example.heryatmo.msb_mob.ShelterManagerMain;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.UserMain.MainActivity;
import com.example.heryatmo.msb_mob.model.Posisi;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.RetroClient;
import com.example.heryatmo.msb_mob.response.LogistikResponse;
import com.example.heryatmo.msb_mob.response.PosisiResponse;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FormShelterActivity extends AppCompatActivity {


    int PLACE_PICKER_REQUEST = 1;
    TextView txttai;
    Intent test;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_shelter);
    }

    protected void  goPlacePicker(View view){




        try{
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Intent intent = builder.build(FormShelterActivity.this);
            startActivityForResult(intent,PLACE_PICKER_REQUEST);
        }catch(GooglePlayServicesRepairableException e){
            GooglePlayServicesUtil
                    .getErrorDialog(e.getConnectionStatusCode(), FormShelterActivity.this, 0);
        }catch (GooglePlayServicesNotAvailableException e){
            Toast.makeText(FormShelterActivity.this, "Google Play Services is not available.",
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if(requestCode == PLACE_PICKER_REQUEST){
            if(resultCode == RESULT_OK){

                Place place = PlacePicker.getPlace(FormShelterActivity.this,data);
                txttai.setText(place.getAddress());
            }
        }

    }

//    protected void createShelter(){
//        Place place = PlacePicker.getPlace(FormShelterActivity.this,test);
//        Posisi dataPosisi = Posisi.builder()
//                .mIdBencana()
//                .mIdUser()
//                .mTitle()
//                .mLat(place.getLatLng().latitude)
//                .mLng(place.getLatLng().longitude)
//                .mNamaShelter()
//                .mWilayahShelter()
//                .build();
//        Retrofit retrofit = RetroClient.getClient();
//        Call<PosisiResponse> call = retrofit.create(APIService.class).posisiRequest(dataPosisi);
//        call.enqueue(new Callback<PosisiResponse>() {
//            @Override
//            public void onResponse(Call<PosisiResponse> call, Response<PosisiResponse> response) {
//                Intent intent = new Intent(getApplicationContext(),MainActivity.class );
//                startActivity(intent);
//                finish();
//            }
//
//            @Override
//            public void onFailure(Call<PosisiResponse> call, Throwable t) {
//                Log.i("Failed","Insert Gagal");
//                Toast.makeText(getBaseContext(), " Gagal Masuk", Toast.LENGTH_LONG).show();
//            }
//        });
//    }


}
