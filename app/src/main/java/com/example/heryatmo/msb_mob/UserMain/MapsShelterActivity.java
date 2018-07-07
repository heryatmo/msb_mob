package com.example.heryatmo.msb_mob.UserMain;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.model.CalonVolunteer;
import com.example.heryatmo.msb_mob.model.DaftarPeran;
import com.example.heryatmo.msb_mob.model.SemuaShelter;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.RetroClient;
import com.example.heryatmo.msb_mob.response.DaftarResponse;
import com.example.heryatmo.msb_mob.response.ShelterResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MapsShelterActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Context context;
    private ProgressDialog progressDialog;
    private List<SemuaShelter> daftarShelter;
    String id_user;

    List<SemuaShelter> semuashelter = new ArrayList<>();
    long id_shelter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_shelter);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        SharedPreferences sp = getSharedPreferences("SPLog", Context.MODE_PRIVATE);
        id_user = sp.getString("id_user", "-");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        initMapShelter();
        // Add a marker in Sydney and move the camera
    }

    private void initMapShelter(){
        Retrofit retrofit = RetroClient.getClient();
        APIService request = retrofit.create(APIService.class);
        Call<ShelterResponse> call = request.getShelter();
        call.enqueue(new Callback<ShelterResponse>() {
            @Override
            public void onResponse(Call<ShelterResponse> call, Response<ShelterResponse> response) {
                semuashelter = response.body().getMData();
                List<String> listSpinner = new ArrayList<String>();
                for (SemuaShelter s : semuashelter) {
                    if (!(s.getMLng() == null || s.getMLat() == null)) {
                        listSpinner.add(s.getMNamaShelter());
                        LatLng shelter = new LatLng(s.getMLat(), s.getMLng());
                        mMap.addMarker(new MarkerOptions().position(shelter).title(s.getMNamaShelter()));
                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                id_shelter = 0;
                                AlertDialog.Builder builder = new AlertDialog.Builder(MapsShelterActivity.this);
                                for (int i = 0; i<semuashelter.size(); i++) {
                                    if (marker.getTitle().equalsIgnoreCase(semuashelter.get(i).getMNamaShelter())){
                                        id_shelter = semuashelter.get(i).getMIdShelter();
                                        builder.setTitle("Shelter : "+semuashelter.get(i).getMNamaShelter());
                                        builder.setMessage("Daftar pada Shelter ini?");
                                    }
                                }
                                builder.setPositiveButton("Daftar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        daftarPeran();
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
                                return false;
                            }
                        });
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(shelter,10));
                    }
                }
            }

            @Override
            public void onFailure(Call<ShelterResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void daftarPeran() {
        DaftarPeran data = DaftarPeran.builder()
                .id_shelter(id_shelter+"")
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

    private void showProgressDialog() {
        this.progressDialog = ProgressDialog.show(context, "Mohon Tunggu", ".....", true);
        //you usually don't want the user to stop the current process, and this will make sure of that
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}
