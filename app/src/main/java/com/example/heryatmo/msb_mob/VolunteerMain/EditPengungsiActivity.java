package com.example.heryatmo.msb_mob.VolunteerMain;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.heryatmo.msb_mob.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditPengungsiActivity extends AppCompatActivity {


    Calendar calendar;
   EditText tvNamaPengungsiedit,tvTemLahirPengedit,tvTglLahirPengedit,
            tvAlamatPengungsiedit,tvAgamaPengungsiedit, tvKeteranganPengungsiedit;

    Spinner spJKPengungsiedit, spGoldarPengungsiedit,spStatusPengungsiedit;

    //pengungsi
    String id_pengungsi="",nama_pengungsi="",tempat_lahir_pengungsi="",tanggal_lahir_pengungsi="",jenis_kelamin_pengungsi="",
            golongan_darah_pengungsi="",alamat_pengungsi="",agama_pengungsi="",status_pengungsi="",keterangan_pengungsi="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pengungsi);

        setPengungsi();

    }

    private void loadPengungsi(){
        Intent intent = getIntent();
        this.nama_pengungsi= intent.getStringExtra("nama_pengungsi");
        this.golongan_darah_pengungsi= intent.getStringExtra("golongan_darah_pengungsi");

        Log.i("GolDar", this.golongan_darah_pengungsi);

//        this.tempat_lahir_pengungsi = intent.getStringExtra("tempat_lahir_pengungsi");
//        this.tanggal_lahir_pengungsi = intent.getStringExtra("tanggal_lahir_pengungsi");
//        this.jenis_kelamin_pengungsi = intent.getStringExtra("jenis_kelamin_pengungsi");
//        this.golongan_darah_pengungsi = intent.getStringExtra("golongan_darah_pengungsi");
//        this.alamat_pengungsi = intent.getStringExtra("alamat_pengungsi");
//        this.agama_pengungsi = intent.getStringExtra("agama_pengungsi");
//        this.status_pengungsi = intent.getStringExtra("status_pengungsi");
//        this.keterangan_pengungsi = intent.getStringExtra("keterangan");
    }

    private void setPengungsi(){
        loadPengungsi();

        tvNamaPengungsiedit = findViewById(R.id.txtNamaPengungsiedit);
//        tvTglLahirPengedit = findViewById(R.id.txtTanggalLahirPengungsiedit);
//        tvTemLahirPengedit = findViewById(R.id.txtTempatLahirPengungsiedit);
//        spJKPengungsiedit = findViewById(R.id.spjenis_kelaminPengungsiedit);
        spGoldarPengungsiedit = findViewById(R.id.spgolongan_darahPengungsiedit);
//        tvAlamatPengungsiedit = findViewById(R.id.txtAlamatPengungsiedit);
//        tvAlamatPengungsiedit = findViewById(R.id.txtAgamaPengungsiedit);
//        spStatusPengungsiedit = findViewById(R.id.spstatus_pengungsiedit);
//        tvKeteranganPengungsiedit = findViewById(R.id.txtKeteranganPengungsiedit);


        tvNamaPengungsiedit.setText(nama_pengungsi);
//        tvTemLahirPengedit.setText(tempat_lahir_pengungsi);
//        tvTglLahirPengedit.setText(tanggal_lahir_pengungsi);
//        spJKPengungsiedit.getSelectedItem().toString();
        ArrayAdapter<String> array_spinner=(ArrayAdapter<String>)spGoldarPengungsiedit.getAdapter();
        spGoldarPengungsiedit.setSelection(array_spinner.getPosition(golongan_darah_pengungsi));

//        tvAlamatPengungsiedit.setText(alamat_pengungsi);
//        tvAgamaPengungsiedit.setText(agama_pengungsi);
//        spStatusPengungsiedit.getSelectedItem().toString();
//        tvKeteranganPengungsiedit.setText(keterangan_pengungsi);

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
