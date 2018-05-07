package com.example.heryatmo.msb_mob;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.heryatmo.msb_mob.model.User;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.APIUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    Button btnSignup;
    EditText edemail;
    EditText edpassword;
    SessionManager session;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadPreferences();

        session = new SessionManager(getApplicationContext());
        edemail= findViewById(R.id.txtEmail);
        edpassword = findViewById(R.id.txtPassword);
        btnLogin =  findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignUp);

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
        Call<User> call = APIUtils.getUserService().loginRequest(email, password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful())
                {
                    User user = response.body();
                    savePreferences(email,password,user.getMData().getMIdRole());
                    Toast.makeText(getBaseContext(), "Selamat Datang", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class );
                    startActivity(intent);

                    finish();
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Error! Silahkan coba lagi.", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("Failed","Login Gagal");
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

    private void loadPreferences(){
        sp = getSharedPreferences("SPLog", Context.MODE_PRIVATE);
        String email = sp.getString("email","-");
        String password = sp.getString("password","-");
        String id_role = sp.getString("id_role","-");
        if(!email.equals("-") && !password.equals("-") && !id_role.equals("-")){
            doLogin(email,password);
        }
    }

    private  void savePreferences(String email, String password, String id_role){
        sp = getSharedPreferences("SPLog", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("email",email);
        editor.putString("password",password);
        editor.putString("id_role", id_role);
        editor.commit();
    }

}
