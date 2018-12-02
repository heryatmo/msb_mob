package com.konselingperkawinan;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class StatusActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private TextInputLayout mStatus, mNama, mEmail;
    private Button mSimpan;

    private String current_uid,current_email, name_profil, status_profil;

    //firebase
    private DatabaseReference mDatabase;
    private FirebaseUser mCurrentUser;

    private DatabaseReference mUserRef;
    private FirebaseAuth mAuth;

    //progressdialog
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        mToolbar = (Toolbar) findViewById(R.id.status_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Ubah Profil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //progress dialog



        //firebase auth and database
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        current_uid = mCurrentUser.getUid();
        current_email=mCurrentUser.getEmail().toString();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        mAuth = FirebaseAuth.getInstance();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());



        //layout
        mStatus=(TextInputLayout) findViewById(R.id.txt_UbahStatus);
        mNama=(TextInputLayout) findViewById(R.id.txt_UbahNama);
        mEmail=(TextInputLayout) findViewById(R.id.txt_UbahEmail);
        mSimpan = (Button) findViewById(R.id.btn_UbahSimpan);

        status_profil = getIntent().getStringExtra("status_profil");
        name_profil = getIntent().getStringExtra("name_profil");

        mStatus.getEditText().setText(status_profil);
        mNama.getEditText().setText(name_profil);

        mEmail.getEditText().setText(current_email);
        mEmail.setEnabled(false); //email tidak bisa diganti! cuman sebagai pengingat


        //ambil data dari firebase
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String nameDulu = dataSnapshot.child("name").getValue().toString();
//                String statusDulu = dataSnapshot.child("status").getValue().toString();
//
//                mStatus.getEditText().setText(statusDulu);
//                mNama.getEditText().setText(nameDulu);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        mSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String simpan_status = mStatus.getEditText().getText().toString();
                final String simpan_nama=mNama.getEditText().getText().toString();

                if(simpan_nama.isEmpty())
                {
                    mNama.setError("Kolom Nama tidak boleh Kosong!!");
                }
                else
                {
                    mNama.setError(null);
                    mProgress = new ProgressDialog(StatusActivity.this);
                    mProgress.setTitle("Menyimpan Perubahan");
                    mProgress.setMessage("Harap menunggu");
                    mProgress.setCanceledOnTouchOutside(false);
                    mProgress.show();


                    //simpan status
                    mDatabase.child("status").setValue(simpan_status).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //simpan nama
                                mDatabase.child("name").setValue(simpan_nama).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            mProgress.dismiss();
                                            finish();
                                        } else {
                                            Toast.makeText(StatusActivity.this, "Terjadi kesalahan\nPeriksa Koneksi Internet anda!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(StatusActivity.this, "Terjadi kesalahan\nPeriksa Koneksi Internet anda!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
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
