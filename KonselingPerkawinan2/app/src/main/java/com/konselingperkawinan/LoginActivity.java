package com.konselingperkawinan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Toolbar mToolbar;
    private TextInputLayout mLoginEmail, mLoginPassword;

    private Button mLogin_btn, mLupa_btn;

    private DatabaseReference mUserDatabase;

    private ProgressDialog mLoginProgress;

    String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Log in");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLoginProgress = new ProgressDialog(this);

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mLoginEmail = (TextInputLayout) findViewById(R.id.txtInput_LoginEmail);
        mLoginPassword = (TextInputLayout) findViewById(R.id.txtInput_LoginPassword);
        mLogin_btn = (Button) findViewById(R.id.btn_Login);
        mLupa_btn = (Button) findViewById(R.id.btn_LupaPassword);

        mLogin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = mLoginEmail.getEditText().getText().toString();
                password = mLoginPassword.getEditText().getText().toString();

                if(validate())
                {
                    mLoginProgress.setTitle("Sedang Login");
                    mLoginProgress.setMessage("Harap menunggu...");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();

                    //Toast.makeText(LoginActivity.this, "email: "+email+", password: "+password, Toast.LENGTH_SHORT).show();
                    loginUser(email,password);
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Isi semua kolom untuk masuk!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mLupa_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profil_intent = new Intent(LoginActivity.this, LupaActivity.class);
                startActivity(profil_intent);
            }
        });
    }

    private boolean validate() {
        boolean valid=true;

        if(password.isEmpty())
        {
            mLoginPassword.setError("Harap mengisi kolom password");
            valid=false;
        }
        else
        {
            mLoginPassword.setError(null);
        }

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            mLoginEmail.setError("Harap mengisi kolom Email dengan format yang benar");
            valid=false;
        }
        else
            mLoginEmail.setError(null);

        return valid;
    }

    private void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this
                        , new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                           // Log.d(TAG, "signInWithEmail:success");

                            mLoginProgress.dismiss();

                            String current_uid = mAuth.getCurrentUser().getUid();
                            String deviceToken = FirebaseInstanceId.getInstance().getToken();

                            //masukin database  device token
                            mUserDatabase.child(current_uid).child("device_token").setValue(deviceToken).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent mainIntent = new Intent (LoginActivity.this, MainActivity.class);
                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(mainIntent);
                                    finish();
                                }
                            });



                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w("ERROR", "signInWithEmail:failure", task.getException());
                            //updateUI(null);

                            //mLoginProgress.hide();
                            //Toast.makeText(LoginActivity.this, "Terjadi kesalahan\nHarap periksa koneksi internet anda", Toast.LENGTH_SHORT).show();

                            String error = "";

                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e) {
                                error = "Email tidak terdaftar!";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                error = "Password anda salah!";
                            } catch (Exception e) {
                                error = "Periksa Koneksi Internet anda!";
                                e.printStackTrace();
                            }

                            mLoginProgress.hide();
                            Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });
    }
}
