package com.konselingperkawinan;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import org.w3c.dom.Text;

public class PasswordActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private TextInputLayout mLama, mBaru, mKonfirmasi, mEmail;
    private Button mGanti;

    private String current_uid, current_email;

    //firebase
    private DatabaseReference mDatabase;
    private FirebaseUser mCurrentUser;

    private DatabaseReference mUserRef;
    private FirebaseAuth mAuth;

    private ProgressDialog mLoginProgress;

    //progressdialog
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        mToolbar = (Toolbar) findViewById(R.id.password_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Ganti Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //firebase auth and database
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        current_uid = mCurrentUser.getUid();
        current_email=mCurrentUser.getEmail().toString();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        mAuth = FirebaseAuth.getInstance();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());

        //layout
        mBaru=(TextInputLayout) findViewById(R.id.password_Baru);
        mLama=(TextInputLayout) findViewById(R.id.password_Lama);
        mEmail=(TextInputLayout) findViewById(R.id.password_Email);
        mGanti= (Button) findViewById(R.id.password_BtnGantiPass);
        mKonfirmasi = (TextInputLayout) findViewById(R.id.password_Konfirmasi);

        mLoginProgress = new ProgressDialog(this);
        mLoginProgress.setTitle("Sedang memperbaharui data anda");
        mLoginProgress.setMessage("Harap menunggu...");
        mLoginProgress.setCanceledOnTouchOutside(false);

        mEmail.getEditText().setText(current_email);
        mEmail.setEnabled(false); //email tidak bisa diganti! cuman sebagai pengingat

        mGanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.equals(mBaru.getEditText().getText().toString(),mKonfirmasi.getEditText().getText().toString())){

                    mLoginProgress.show();

                    AuthCredential credential = EmailAuthProvider.getCredential(current_email, mLama.getEditText().getText().toString());

                    mCurrentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                mCurrentUser.updatePassword(mBaru.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){
                                            Toast.makeText(PasswordActivity.this, "Password berhasil diganti!", Toast.LENGTH_SHORT).show();
                                            finish();
                                            mLoginProgress.dismiss();
                                        }
                                        else {
                                            Toast.makeText(PasswordActivity.this, "Terjadi kesalahan, silahkan coba kembali", Toast.LENGTH_SHORT).show();
                                            mBaru.getEditText().setText("");
                                            mLama.getEditText().setText("");
                                            mKonfirmasi.getEditText().setText("");
                                            mLoginProgress.dismiss();
                                        }

                                    }
                                });
                            }
                            else {
                                Toast.makeText(PasswordActivity.this, "Password Lama tidak cocok, silahkan masukkan kembali", Toast.LENGTH_SHORT).show();
                                mBaru.getEditText().setText("");
                                mLama.getEditText().setText("");
                                mKonfirmasi.getEditText().setText("");
                                mLoginProgress.dismiss();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(PasswordActivity.this, "Password Baru dan Konfirmasi tidak sama!", Toast.LENGTH_SHORT).show();
                    mBaru.getEditText().setText("");
                    mLama.getEditText().setText("");
                    mKonfirmasi.getEditText().setText("");
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mUserRef.child("online").setValue(ServerValue.TIMESTAMP);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUserRef.child("online").setValue("true");
    }
}
