package com.example.heryatmo.msb_mob.Login;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.model.RegisterResponse;
import com.example.heryatmo.msb_mob.model.Role;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.RetroClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity {

    TextView emailRegister, passwordRegister, namaRegister, tempat_lahir, tanggal_lahir, alamat, no_hp;
    Spinner golongandarah, jeniskelamin, id_role;
    Button bSubmit, bTanggal;
    Calendar calendar;
    List<Role> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        calendar = Calendar.getInstance();

        emailRegister = findViewById(R.id.txtEmailRegister);
        passwordRegister = findViewById(R.id.txtPasswordRegister);
        namaRegister = findViewById(R.id.txtNama);
        tempat_lahir = findViewById(R.id.txtTempatLahir);
        tanggal_lahir = findViewById(R.id.txtTanggalLahir);
        alamat = findViewById(R.id.txtAlamat);
        no_hp = findViewById(R.id.txtTelepon);
        golongandarah = findViewById(R.id.spgolongan_darah);
        jeniskelamin = findViewById(R.id.spjenis_kelamin);
        bTanggal = findViewById(R.id.btnTanggal);
        bSubmit = findViewById(R.id.btnSubmit);
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailRegister.getText().toString();
                String password = passwordRegister.getText().toString();
                String namReg = namaRegister.getText().toString();
                String temLahir = tempat_lahir.getText().toString();
                String tglLahir = tanggal_lahir.getText().toString();
                String alamReg = alamat.getText().toString();
                String nopereg = no_hp.getText().toString();

                if(validateRegister(email,password,namReg,temLahir,tglLahir,alamReg,nopereg)) {
                    register();
                }
            }
        });

    }

    private void register() {
        RegisterResponse dataUser = RegisterResponse.builder()
                .mEmail(emailRegister.getText().toString())
                .mPassword(passwordRegister.getText().toString())
                .mNama(namaRegister.getText().toString())
                .mTempatLahir(tempat_lahir.getText().toString())
                .mTanggalLahir(tanggal_lahir.getText().toString())
                .mJenisKelamin(jeniskelamin.getSelectedItem().toString())
                .mGolonganDarah(golongandarah.getSelectedItem().toString())
                .mAlamat(alamat.getText().toString())
                .mNoHp(no_hp.getText().toString())
                .build();
        Retrofit retrofit = RetroClient.getClient();
        Call<com.example.heryatmo.msb_mob.response.RegisterResponse> call = retrofit.create(APIService.class).registerRequest(dataUser);
        call.enqueue(new Callback<com.example.heryatmo.msb_mob.response.RegisterResponse>() {
            @Override
            public void onResponse(Call<com.example.heryatmo.msb_mob.response.RegisterResponse> call, Response<com.example.heryatmo.msb_mob.response.RegisterResponse> response) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class );
                startActivity(intent);

                finish();
            }

            @Override
            public void onFailure(Call<com.example.heryatmo.msb_mob.response.RegisterResponse> call, Throwable t) {

            }
        });
    }

    public void dateWow(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Date date = calendar.getTime();

                String formatted = new SimpleDateFormat("yyyy-MM-dd").format(date);
                tanggal_lahir.setText(formatted);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
//        Toast.makeText(this, String.valueOf(list.get(id_role.getSelectedItemPosition()).getMIdRole()), Toast.LENGTH_SHORT).show();
    }

    private boolean validateRegister(String email, String password, String nama, String tptlahir, String tgllahir, String ala, String nope){
        if(emailRegister == null || email.trim().length() == 0){
            Toast.makeText(this,"Email Required",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(passwordRegister == null || password.trim().length() < 6){
            Toast.makeText(this,"Password Kurang dari Enam Digit",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(namaRegister == null || nama.trim().length() == 0){
            Toast.makeText(this,"Nama Required",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tempat_lahir == null || tptlahir.trim().length() == 0){
            Toast.makeText(this,"tempat lahir Required",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tanggal_lahir == null || tgllahir.trim().length() == 0){
            Toast.makeText(this,"tanggal lahir Required",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(alamat == null || ala.trim().length() == 0){
            Toast.makeText(this,"alamat Required",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(no_hp == null || nope.trim().length() == 0){
            Toast.makeText(this,"nomor hp Required",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
