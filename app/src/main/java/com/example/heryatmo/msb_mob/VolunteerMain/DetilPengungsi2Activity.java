package com.example.heryatmo.msb_mob.VolunteerMain;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.ShelterManagerMain.MainMshelterActivity;
import com.example.heryatmo.msb_mob.UserMain.InfoActivity;
import com.example.heryatmo.msb_mob.UserMain.MainActivity;
import com.example.heryatmo.msb_mob.UserMain.MapsShelterActivity;
import com.example.heryatmo.msb_mob.UserMain.ProfileActivity;
import com.example.heryatmo.msb_mob.model.HapusPengungsi;
import com.example.heryatmo.msb_mob.model.Pengungsi;
import com.example.heryatmo.msb_mob.model.UbahPassword;
import com.example.heryatmo.msb_mob.model.UbahPengungsi;
import com.example.heryatmo.msb_mob.remote.APIUtils;
import com.example.heryatmo.msb_mob.response.HapusPengungsiResponse;
import com.example.heryatmo.msb_mob.response.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetilPengungsi2Activity extends AppCompatActivity {

    //TextView
    TextView tvNamaPengungsi,tvTemTglLahirPeng, tvJKPengungsi, tvGoldarPengungsi,
                tvAlamatPengungsi,tvAgamaPengungsi,tvStatusPengungsi, tvKeteranganPengungsi;
    LinearLayout layUbahPengungsi, layHapusPengungsi;

    //pengungsi
    String id_pengungsi="",nama_pengungsi="",tempat_lahir_pengungsi="",tanggal_lahir_pengungsi="",jenis_kelamin_pengungsi="",
            golongan_darah_pengungsi="",alamat_pengungsi="",agama_pengungsi="",status_pengungsi="",keterangan_pengungsi="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil_pengungsi2);
        setPengungsi();
        layUbahPengungsi = findViewById(R.id.layUbahPengungsi);
        layUbahPengungsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetilPengungsi2Activity.this,EditPengungsiActivity.class );
                intent.putExtra("nama_pengungsi",nama_pengungsi);
                intent.putExtra("golongan_darah_pengungsi",golongan_darah_pengungsi);
                startActivity(intent);
            }
        });
        layHapusPengungsi = findViewById(R.id.layHapusPengungsi);
        layHapusPengungsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetilPengungsi2Activity.this);
                builder.setTitle("Hapus Pengungsi");
                builder.setMessage("Hapus Pengungsi Ini?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        hapusPengungsi();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void loadPengungsi(){
        Intent intent = getIntent();
        this.id_pengungsi= intent.getStringExtra("id_pengungsi");
        this.nama_pengungsi= intent.getStringExtra("nama_pengungsi");
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

        tvNamaPengungsi = findViewById(R.id.tvNamaPengungsi);
        tvTemTglLahirPeng = findViewById(R.id.tvTempatTanggalLahirPengungsi);
        tvJKPengungsi = findViewById(R.id.tvJenisKelaminPengungsi);
        tvGoldarPengungsi = findViewById(R.id.tvGolonganDarahPengungsi);
        tvAlamatPengungsi = findViewById(R.id.tvAlamatPengungsi1);
        tvAgamaPengungsi = findViewById(R.id.tvAgamaPengungsi1);
        tvStatusPengungsi = findViewById(R.id.tvStatusPengungsi1);
        tvKeteranganPengungsi = findViewById(R.id.tvKetPengungsi);


        tvNamaPengungsi.setText(nama_pengungsi);
        tvTemTglLahirPeng.setText(tempat_lahir_pengungsi + ", " + tanggal_lahir_pengungsi);
        tvJKPengungsi.setText(jenis_kelamin_pengungsi);
        tvGoldarPengungsi.setText(golongan_darah_pengungsi);
        tvAlamatPengungsi.setText(alamat_pengungsi);
        tvAgamaPengungsi.setText(agama_pengungsi);
        tvStatusPengungsi.setText(status_pengungsi);
        tvKeteranganPengungsi.setText(keterangan_pengungsi);

    }

    private void hapusPengungsi()
    {
        Call<HapusPengungsiResponse> call = APIUtils.getUserService().deletePengungsi(id_pengungsi);
        call.enqueue(new Callback<HapusPengungsiResponse>() {
            @Override
            public void onResponse(Call<HapusPengungsiResponse> call, Response<HapusPengungsiResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Intent intent = new Intent(DetilPengungsi2Activity.this,PengungsiMainActivity.class );
                        startActivity(intent);
                        finish();
                    } else {
                        Log.i("Failed", "Hapus Pengungsi Gagal [Else in Try-Catch]");
                        Toast.makeText(getBaseContext(), "Terjadi Kesalahan Ketika Hapus Pengungsi", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e) {
                    Log.i("Failed", "Hapus Pengungsi Gagal [Try-Catch]");
                    Toast.makeText(getBaseContext(), "Terjadi Kesalahan Ketika Hapus Pengungsi", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<HapusPengungsiResponse> call, Throwable t) {
                Intent intent = new Intent(DetilPengungsi2Activity.this,PengungsiMainActivity.class );
                startActivity(intent);
                finish();
            }
        });
    }
}
