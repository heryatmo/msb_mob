package com.example.heryatmo.msb_mob.VolunteerMain;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heryatmo.msb_mob.Login.LoginActivity;
import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.model.Pengungsi;
import com.example.heryatmo.msb_mob.model.SemuaShelter;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.RetroClient;
import com.example.heryatmo.msb_mob.response.PengungsiResponse;
import com.example.heryatmo.msb_mob.response.ShelterResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PengungsiActivity extends AppCompatActivity {

    Button btnSubmit;
    TextView  namaPengungsi, tempat_lahirPengungsi, tanggal_lahirPengungsi, alamatPengungsi, agamaPengungsi, keteranganPengungsi;
    Spinner golongandarahPengungsi, jeniskelaminPengungsi, statusPengungsi, sShelter;
    Calendar calendar;

    List<SemuaShelter> semuashelter = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengungsi);
        calendar = Calendar.getInstance();

        namaPengungsi = findViewById(R.id.txtNamaPengungsi);
        tempat_lahirPengungsi = findViewById(R.id.txtTempatLahirPengungsi);
        tanggal_lahirPengungsi = findViewById(R.id.txtTanggalLahirPengungsi);
        alamatPengungsi = findViewById(R.id.txtAlamat);
        agamaPengungsi = findViewById(R.id.txtAgamaPengungsi);
        keteranganPengungsi = findViewById(R.id.txtKeteranganPengungsi);
        golongandarahPengungsi = findViewById(R.id.spgolongan_darahPengungsi);
        jeniskelaminPengungsi = findViewById(R.id.spjenis_kelaminPengungsi);
        statusPengungsi = findViewById(R.id.spstatus_pengungsi);

        btnSubmit = findViewById(R.id.btnSubmitPengungsi);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PengungsiActivity.this,VolunteerMainActivity.class );
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

    private void registerPengungsi(){
        String idShelter = String.valueOf(semuashelter.get(sShelter.getSelectedItemPosition()).getMIdShelter());
        Pengungsi dataPengungsi = Pengungsi.builder()
                .mIdShelter(idShelter)
                .mNamaPengungsi(namaPengungsi.getText().toString())
                .mTempatLahirPengungsi(tempat_lahirPengungsi.getText().toString())
                .mTanggalLahirPengungsi(tanggal_lahirPengungsi.getText().toString())
                .mAlamatPengungsi(alamatPengungsi.getText().toString())
                .mAgamaPengungsi(agamaPengungsi.getText().toString())
                .mKeterangan(keteranganPengungsi.getText().toString())
                .mGolonganDarahPengungsi(golongandarahPengungsi.getSelectedItem().toString())
                .mJenisKelaminPengungsi(jeniskelaminPengungsi.getSelectedItem().toString())
                .mStatusPengungsi(statusPengungsi.getSelectedItem().toString())
                .build();
        Retrofit retrofit = RetroClient.getClient();
        Call<PengungsiResponse> call = retrofit.create(APIService.class).pengungsiRequest(dataPengungsi);
        call.enqueue(new Callback<PengungsiResponse>() {
            @Override
            public void onResponse(Call<PengungsiResponse> call, Response<PengungsiResponse> response) {
                Intent intent = new Intent(getApplicationContext(),VolunteerMainActivity.class );
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<PengungsiResponse> call, Throwable t) {

            }
        });

    }

    public void datePengungsi(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Date date = calendar.getTime();

                String formatted = new SimpleDateFormat("yyyy-MM-dd").format(date);
                tanggal_lahirPengungsi.setText(formatted);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
//        Toast.makeText(this, String.valueOf(list.get(id_role.getSelectedItemPosition()).getMIdRole()), Toast.LENGTH_SHORT).show();
    }
}
