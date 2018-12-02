package com.konselingperkawinan;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;


public class LupaActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private Button mReset;
    private TextInputLayout mTxtReset;
    private String email;
    private ProgressDialog mLoginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa);

        mToolbar = (Toolbar) findViewById(R.id.lupa_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Lupa Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        mTxtReset = (TextInputLayout) findViewById(R.id.text_EmailLupa);
        mReset = (Button) findViewById(R.id.btn_ResetPassword);


        mLoginProgress = new ProgressDialog(this);
        mLoginProgress.setTitle("Sedang memeriksa data anda");
        mLoginProgress.setMessage("Harap menunggu...");
        mLoginProgress.setCanceledOnTouchOutside(false);


        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = mTxtReset.getEditText().getText().toString();

                if(validate())
                {
                    mLoginProgress.show();
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                Toast.makeText(LupaActivity.this, "Data untuk reset password sudah dikirim ke email anda!", Toast.LENGTH_SHORT).show();
                                mLoginProgress.hide();
                                finish();
                            }
                            else {
                                String error = "";

                                try {
                                    throw task.getException();
                                }catch (FirebaseAuthInvalidUserException e) {
                                    error = "Email tidak terdaftar!";
                                }catch (Exception e) {
                                    error = "Periksa Koneksi Internet anda!";
                                    e.printStackTrace();
                                }

                                Toast.makeText(LupaActivity.this, error, Toast.LENGTH_LONG).show();
                                mLoginProgress.hide();
                            }
                        }
                    });
                }
                else
                {
                    mLoginProgress.hide();
                    Toast.makeText(LupaActivity.this, "Kolom email masih kosong atau tidak sesuai dengan format email!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean validate() {
        boolean valid=true;

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            mTxtReset.setError("Harap mengisi kolom Email dengan format yang benar");
            valid=false;
        }
        else
            mTxtReset.setError(null);

        return valid;
    }
}
