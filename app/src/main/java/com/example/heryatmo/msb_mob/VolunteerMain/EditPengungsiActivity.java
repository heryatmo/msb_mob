package com.example.heryatmo.msb_mob.VolunteerMain;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.model.SemuaShelter;
import com.example.heryatmo.msb_mob.model.UbahPengungsi;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.APIUtils;
import com.example.heryatmo.msb_mob.remote.RetroClient;
import com.example.heryatmo.msb_mob.response.ShelterResponse;
import com.example.heryatmo.msb_mob.response.UbahPengungsiResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditPengungsiActivity extends AppCompatActivity {

    List<SemuaShelter> semuashelter = new ArrayList<>();

    Calendar calendar;
   EditText tvNamaPengungsiedit,tvTemLahirPengedit,tvTglLahirPengedit,
            tvAlamatPengungsiedit,tvAgamaPengungsiedit, tvKeteranganPengungsiedit;

    Spinner spJKPengungsiedit, spGoldarPengungsiedit,spStatusPengungsiedit;

    //pengungsi
    String id_pengungsi="",nama_pengungsi="",tempat_lahir_pengungsi="",tanggal_lahir_pengungsi="",jenis_kelamin_pengungsi="",
            golongan_darah_pengungsi="",alamat_pengungsi="",agama_pengungsi="",status_pengungsi="",keterangan_pengungsi="";

    //Button
    Button btnUbah;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pengungsi);
        calendar = Calendar.getInstance();

        setPengungsi();
        btnUbah = findViewById(R.id.btnUbahPengungsi);
        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ubahPengungsi();
            }
        });

    }

    private void ubahPengungsi(){
        UbahPengungsi dataUbahPengungsi = UbahPengungsi.builder()
                .mNamaPengungsi(tvNamaPengungsiedit.getText().toString())
                .mTempatLahirPengungsi(tvTemLahirPengedit.getText().toString())
                .mTanggalLahirPengungsi(tvTglLahirPengedit.getText().toString())
                .mAlamatPengungsi(tvAlamatPengungsiedit.getText().toString())
                .mAgamaPengungsi(tvAgamaPengungsiedit.getText().toString())
                .mKeterangan(tvKeteranganPengungsiedit.getText().toString())
                .mGolonganDarahPengungsi(spGoldarPengungsiedit.getSelectedItem().toString())
                .mJenisKelaminPengungsi(spJKPengungsiedit.getSelectedItem().toString())
                .mStatusPengungsi(spStatusPengungsiedit.getSelectedItem().toString())
                .build();
        Retrofit retrofit = RetroClient.getClient();
        Call<UbahPengungsiResponse> call = retrofit.create(APIService.class).ubahPengungsi(id_pengungsi,dataUbahPengungsi);
        call.enqueue(new Callback<UbahPengungsiResponse>() {
            @Override
            public void onResponse(Call<UbahPengungsiResponse> call, Response<UbahPengungsiResponse> response) {
                Intent intent = new Intent(getApplicationContext(),PengungsiMainActivity.class );
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<UbahPengungsiResponse> call, Throwable t) {
                Log.i("Failed","Yes, ID : " + id_pengungsi);
            }
        });
    }

    private void loadPengungsi(){
        Intent intent = getIntent();
        this.id_pengungsi= intent.getStringExtra("id_pengungsi");
        this.nama_pengungsi= intent.getStringExtra("nama_pengungsi");
        this.golongan_darah_pengungsi= intent.getStringExtra("golongan_darah_pengungsi");
        this.tempat_lahir_pengungsi = intent.getStringExtra("tempat_lahir_pengungsi");
        this.tanggal_lahir_pengungsi = intent.getStringExtra("tanggal_lahir_pengungsi");
        this.jenis_kelamin_pengungsi = intent.getStringExtra("jenis_kelamin_pengungsi");
        this.golongan_darah_pengungsi = intent.getStringExtra("golongan_darah_pengungsi");
        this.alamat_pengungsi = intent.getStringExtra("alamat_pengungsi");
        this.agama_pengungsi = intent.getStringExtra("agama_pengungsi");
        this.status_pengungsi = intent.getStringExtra("status_pengungsi");
        this.keterangan_pengungsi = intent.getStringExtra("keterangan");
    }

    private void setPengungsi(){
        loadPengungsi();
        tvNamaPengungsiedit = findViewById(R.id.txtNamaPengungsiedit);
        tvTglLahirPengedit = findViewById(R.id.txtTanggalLahirPengungsiedit);
        tvTemLahirPengedit = findViewById(R.id.txtTempatLahirPengungsiedit);
        spJKPengungsiedit = findViewById(R.id.spjenis_kelaminPengungsiedit);
        spGoldarPengungsiedit = findViewById(R.id.spgolongan_darahPengungsiedit);
        tvAlamatPengungsiedit = findViewById(R.id.txtAlamatPengungsiedit);
        tvAgamaPengungsiedit = findViewById(R.id.txtAgamaPengungsiedit1);
        spStatusPengungsiedit = findViewById(R.id.spstatus_pengungsiedit);
        tvKeteranganPengungsiedit = findViewById(R.id.txtKeteranganPengungsiedit);


        tvNamaPengungsiedit.setText(nama_pengungsi);
        tvTemLahirPengedit.setText(tempat_lahir_pengungsi);
        tvTglLahirPengedit.setText(tanggal_lahir_pengungsi);
        ArrayAdapter<String> array_jk =(ArrayAdapter<String>)spJKPengungsiedit.getAdapter();
        spJKPengungsiedit.setSelection(array_jk.getPosition(jenis_kelamin_pengungsi));
        ArrayAdapter<String> array_spinner=(ArrayAdapter<String>)spGoldarPengungsiedit.getAdapter();
        spGoldarPengungsiedit.setSelection(array_spinner.getPosition(golongan_darah_pengungsi));
        tvAlamatPengungsiedit.setText(alamat_pengungsi);
        tvAgamaPengungsiedit.setText(agama_pengungsi);
        ArrayAdapter<String> array_stat = (ArrayAdapter<String>)spStatusPengungsiedit.getAdapter();
        spStatusPengungsiedit.setSelection(array_stat.getPosition(status_pengungsi));
        tvKeteranganPengungsiedit.setText(keterangan_pengungsi);

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
                tvTglLahirPengedit.setText(formatted);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        //        Toast.makeText(this, String.valueOf(list.get(id_role.getSelectedItemPosition()).getMIdRole()), Toast.LENGTH_SHORT).show();
    }
}
