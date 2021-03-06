package com.example.heryatmo.msb_mob.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heryatmo.msb_mob.UserMain.MainActivity;
import com.example.heryatmo.msb_mob.ShelterManagerMain.MainMshelterActivity;
import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.VolunteerMain.VolunteerMainActivity;
import com.example.heryatmo.msb_mob.response.UserResponse;
import com.example.heryatmo.msb_mob.remote.APIUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    Button btnSignup;
    EditText edemail;
    EditText edpassword;
    TextView edlupapass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edemail= findViewById(R.id.txtEmail);
        edpassword = findViewById(R.id.txtPassword);
        btnLogin =  findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignUp);
        edlupapass = findViewById(R.id.txtlupapass);

        edlupapass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,LupaPassActivity.class );
                startActivity(intent);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = edemail.getText().toString();
                String password = edpassword.getText().toString();

                if(validateLogin(email,password)){
                    doLogin(email,password);
                }

            }

        });

        btnSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class );
                startActivity(intent);
            }
        });
    }

    private void doLogin(final String email, final String password)
    {
        Call<UserResponse> call = APIUtils.getUserService().loginRequest(email, password);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        UserResponse user = response.body();

                        if (user.getMData().getMIdRole().toString().equalsIgnoreCase("3")) {
                            savePreferences(email, password, user.getMData().getMIdRole(), user.getMData().getMIdUser().toString());
                            saveUser(user);
                            Intent intent = new Intent(getApplicationContext(), VolunteerMainActivity.class);
                            startActivity(intent);

                            finish();
                        } else if (user.getMData().getMIdRole().toString().equalsIgnoreCase("4")) {
                            savePreferences(email, password, user.getMData().getMIdRole(), user.getMData().getMIdUser().toString());
                            saveUser(user);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);

                            finish();
                        } else if (user.getMData().getMIdRole().toString().equalsIgnoreCase("2")) {
                            savePreferences(email, password, user.getMData().getMIdRole(), user.getMData().getMIdUser().toString());
                            saveUser(user);
                            Intent intent = new Intent(getApplicationContext(), MainMshelterActivity.class);
                            startActivity(intent);

                            finish();
                        }

                    } else {
                        Log.i("Failed", "Login Gagal");
                        Toast.makeText(getBaseContext(), "Username atau Password Salah", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e) {
                    Log.i("Failed", "Login Gagal");
                    Toast.makeText(getBaseContext(), "Username atau Password Salah", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.i("Failed", "Login Gagal");
                Toast.makeText(getBaseContext(), "Username atau Password Salah", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validateLogin(String email, String password){
        if(edemail == null || email.trim().length() == 0){
            Toast.makeText(this,"Email Required",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(edpassword == null || password.trim().length() == 0){
            Toast.makeText(this,"Password Required",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private  void savePreferences(String email, String password, String id_role, String id_user){
        SharedPreferences sp = getSharedPreferences("SPLog", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("email",email);
        editor.putString("password",password);
        editor.putString("id_role", id_role);
        editor.putString("id_user", id_user);
        editor.commit();
    }

    private  void saveUser(UserResponse user){
        SharedPreferences sp = getSharedPreferences("SPUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("id_user",user.getMData().getMIdUser()+"");
        editor.putString("nama_user",user.getMData().getMNama());
        editor.putString("id_role",user.getMData().getMIdRole()+"");
        editor.putString("nama_role",user.getMData().getMRole().getMNamaRole());

        editor.putString("jenis_kelamin", user.getMData().getMJenisKelamin());
        editor.putString("golongan_darah", user.getMData().getMGolonganDarah());

        editor.putString("tempat_lahir", user.getMData().getMTempatLahir());
        editor.putString("tanggal_lahir", user.getMData().getMTanggalLahir());

        editor.putString("alamat", user.getMData().getMAlamat());
        editor.putString("email", user.getMData().getMEmail());
        editor.putString("no_hp", user.getMData().getMNoHp());

        editor.putString("id_shelter", "1");
        editor.putString("nama_shelter", "1");
        editor.commit();
    }
}
