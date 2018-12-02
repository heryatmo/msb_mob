package com.konselingperkawinan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout mNama;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword,mKonfirmasiPassword;
    private Button mDaftarAkunBtn,mCekEmail;

    private RadioGroup mRG;
    private RadioButton mRB,mRBkonseli;

    private Toolbar mToolbar;

    Toast toast;

    String namaAkun, emailAkun, roleAkun, passwordAkun, konfirmasiPasswordAkun;

    //progress dialog
    private ProgressDialog mRegProgress;
    //

    //firebase auth
    private FirebaseAuth mAuth;
    //

    //firebase database
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //toolbar
        mToolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Buat Akun");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        //

        //Progress Dialog
        mRegProgress = new ProgressDialog(this);


        mNama= (TextInputLayout) findViewById(R.id.txtInput_nama);
        mEmail= (TextInputLayout) findViewById(R.id.txtInput_Email);
        mPassword = (TextInputLayout) findViewById(R.id.txtInput_Password);
        mKonfirmasiPassword = (TextInputLayout) findViewById(R.id.txtInput_Password2);
        mDaftarAkunBtn = (Button) findViewById(R.id.btn_DaftarBaru);
       // mCekEmail = (Button) findViewById(R.id.btn_cekEmail);
        mRG = (RadioGroup) findViewById(R.id.rGroup);
        mRBkonseli = (RadioButton) findViewById(R.id.rBtn_Konseli);

        int radioID = mRG.getCheckedRadioButtonId();
        mRB = (RadioButton) findViewById(radioID);

        //mDaftarAkunBtn.setEnabled(false);


        mDaftarAkunBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                namaAkun = mNama.getEditText().getText().toString();
                emailAkun = mEmail.getEditText().getText().toString();
                passwordAkun = mPassword.getEditText().getText().toString();
                konfirmasiPasswordAkun = mKonfirmasiPassword.getEditText().getText().toString();


                if(validate()){
                    //progress dialog
                    mRegProgress.setTitle("Mendaftarkan Akun anda");
                    mRegProgress.setMessage("Harap Menunggu...");
                    mRegProgress.setCanceledOnTouchOutside(false); //ga bisa pencet diluar
                    mRegProgress.show();
                    //daftar akun
                    register_user(namaAkun, roleAkun, emailAkun, passwordAkun);
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "      Tidak bisa mendaftar\nHarap periksa semua kolom!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean validate() {
        boolean valid=true;

        if(namaAkun.isEmpty())
        {
            mNama.setError("Harap mengisi kolom Nama");
            valid=false;
        }
        else
        {
            mNama.setError(null);
        }

        if(emailAkun.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailAkun).matches())
        {
            mEmail.setError("Harap mengisi kolom Email dengan format yang benar");
            valid=false;
        }
        else
            mEmail.setError(null);

        if(mRG.getCheckedRadioButtonId()==-1)
        {
            mRBkonseli.setError("Harap memilih peran");
            valid=false;
        }
        else
        {
            roleAkun = mRB.getText().toString();
            mRBkonseli.setError(null);
        }

        if(passwordAkun.isEmpty() || passwordAkun.length()<6)
        {
            mPassword.setError("Harap mengisi kolom Password minimal 6 karakter");
            valid=false;
        }
        else
            mPassword.setError(null);

        if(konfirmasiPasswordAkun.isEmpty() || konfirmasiPasswordAkun.length()<6)
        {
            mKonfirmasiPassword.setError("Harap mengisi kolom Konfirmasi Password minimal 6 karakter");
            valid=false;
        }
        else
            mKonfirmasiPassword.setError(null);

        if(!passwordAkun.equalsIgnoreCase(konfirmasiPasswordAkun))
        {
            mKonfirmasiPassword.setError("Konfirmasi Password tidak sama");
            valid=false;
        }


        return valid;
    }

    public void checkButton(View v)
    {
        int radioID = mRG.getCheckedRadioButtonId();

        mRB = (RadioButton) findViewById(radioID);

        if(toast != null)
        {
            toast.cancel();
        }
        toast = Toast.makeText(this, "           Anda memilih: "+mRB.getText().toString()+"\nAnda tidak dapat mengubahnya nanti.", Toast.LENGTH_LONG);
        toast.show();

    }

    private void register_user(final String nama, final String roles, String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = current_user.getUid();
                            String device_token = FirebaseInstanceId.getInstance().getToken();

                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("name", nama);
                            userMap.put("status","Hai! Saya adalah seorang "+roles);
                            userMap.put("role", roles);
                            userMap.put("image","default");
                            userMap.put("thumb_image","default");
                            userMap.put("device_token", device_token);

                            //masuk ke database
                            mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        //dismiss progress dialog
                                        mRegProgress.dismiss();

                                        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(mainIntent);
                                        finish();

                                        //Log.d(Tag , "createUserWithEmail:success");
                                        //FirebaseUser user = mAuth.getCurrentUser();
                                        //updateUI(user);

                                    }
                                    else{
                                        Toast.makeText(RegisterActivity.this, "Terjadi kesalahan\nHarap periksa koneksi internet anda",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });


                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(Tag, "createUserWithEmail:failure", task.getException());

                            //hide progress dialog

                            //updateUI(null);

                            mRegProgress.hide();

                            String error = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                error = "Password terlalu lemah!\nGanti Password baru";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                error = "Email tidak dalam format yang benar!";
                            } catch (FirebaseAuthUserCollisionException e) {
                                error = "Email sudah digunakan!";
                            } catch (Exception e) {
                                error = "Error tidak diketahui";
                                e.printStackTrace();
                            }
                            Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });
    }

    /* fail check button
    public void checkEmail(View v)
    {
        mAuth.fetchProvidersForEmail(mEmail.getEditText().toString())
                .addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                boolean check =  !task.getResult().getProviders().isEmpty();

                if(!check)
                {
                    Toast.makeText(RegisterActivity.this, "Anda dapat menggunakan Email tersebut", Toast.LENGTH_SHORT).show();
                    mDaftarAkunBtn.setEnabled(true);
                    mCekEmail.setEnabled(false);
                    mEmail.setEnabled(false);
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Anda tidak dapat menggunakan Email tersebut\nGunakan email yang lain", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    */
}
