package com.example.heryatmo.msb_mob.ShelterManagerMain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heryatmo.msb_mob.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class PlacePickerActivity extends AppCompatActivity {

    int PLACE_PICKER_REQUEST = 1;
    TextView txttai;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_picker);

        txttai = findViewById(R.id.tvTai);
    }

    public void  goPlacePicker(View view){



        try{
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Intent intent = builder.build(PlacePickerActivity.this);
            startActivityForResult(intent,PLACE_PICKER_REQUEST);
        }catch(GooglePlayServicesRepairableException e){
            GooglePlayServicesUtil
                    .getErrorDialog(e.getConnectionStatusCode(), PlacePickerActivity.this, 0);
        }catch (GooglePlayServicesNotAvailableException e){
            Toast.makeText(PlacePickerActivity.this, "Google Play Services is not available.",
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if(requestCode == PLACE_PICKER_REQUEST){
            if(resultCode == RESULT_OK){

                Place place = PlacePicker.getPlace(PlacePickerActivity.this,data);
                txttai.setText(place.getAddress());
            }
        }

    }
}
