package com.example.heryatmo.msb_mob.VolunteerMain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.heryatmo.msb_mob.R;

public class DetilPengungsi2Activity extends AppCompatActivity {

    //TextView
    TextView tvNamaPengungsi,tvTemLahirPeng, tvTanLahirPeng, tvJKPengungsi, tvGoldarPengungsi,
                tvAlamatPengungsi,tvAgamaPengungsi,tvStatusPengungsi, tvKeteranganPengungsi;

    //pengungsi
    String nama_pengungsi="",tempat_lahir_pengungsi="",tanggal_lahir_pengungsi="",jenis_kelamin_pengungsi="",
            golongan_darah_pengungsi="",alamat_pengungsi="",agama_pengungsi="",status_pengungsi="",keterangan_pengungsi="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil_pengungsi2);
        setPengungsi();
    }

    private void loadPengungsi(){
        Intent intent = getIntent();
        this.nama_pengungsi= intent.getStringExtra("nama_pengungsi");
        this.tempat_lahir_pengungsi = intent.getStringExtra("tempat_lahir_pengungsi");
        this.tanggal_lahir_pengungsi = intent.getStringExtra("tanggal_lahir_pengungsi");
        this.jenis_kelamin_pengungsi = intent.getStringExtra("jenis_kelamin_pengungsi");
        this.golongan_darah_pengungsi = intent.getStringExtra("golongan_darah_pengungsi");
        this.alamat_pengungsi = intent.getStringExtra("alamat_pengungsi");
        this.agama_pengungsi = intent.getStringExtra("agama_pengungsi");
        this.status_pengungsi = intent.getStringExtra("status_pengungsi");
        this.keterangan_pengungsi = intent.getStringExtra("keterangan_pengungsi");
    }

    private void setPengungsi(){
       loadPengungsi();

        tvNamaPengungsi = findViewById(R.id.txtNamaPengungsi);
        tvTemLahirPeng = findViewById(R.id.txtTanggalLahirPengungsi);
        tvTanLahirPeng = findViewById(R.id.txtTanggalLahirPengungsi);
        tvJKPengungsi = findViewById(R.id.tvJenisKelaminPengungsi);
        tvGoldarPengungsi = findViewById(R.id.tvGolonganDarahPengungsi);
        tvAlamatPengungsi = findViewById(R.id.tvAlamatPengungsi1);
        tvAgamaPengungsi = findViewById(R.id.tvAgamaPengungsi1);
        tvStatusPengungsi = findViewById(R.id.tvStatusPengungsi1);
        tvKeteranganPengungsi = findViewById(R.id.tvKetPengungsi);


        tvNamaPengungsi.setText(nama_pengungsi);
        tvTemLahirPeng.setText(tempat_lahir_pengungsi);
        tvTanLahirPeng.setText(tanggal_lahir_pengungsi);
        tvJKPengungsi.setText(jenis_kelamin_pengungsi);
        tvGoldarPengungsi.setText(golongan_darah_pengungsi);
        tvAlamatPengungsi.setText(alamat_pengungsi);
        tvAgamaPengungsi.setText(agama_pengungsi);
        tvStatusPengungsi.setText(status_pengungsi);
        tvKeteranganPengungsi.setText(keterangan_pengungsi);

    }
}
