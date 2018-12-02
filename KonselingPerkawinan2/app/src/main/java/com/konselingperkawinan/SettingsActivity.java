package com.konselingperkawinan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class SettingsActivity extends AppCompatActivity {

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    private DatabaseReference mUserRef;
    private FirebaseAuth mAuth;

    //layout
    private CircleImageView mImage;
    private TextView mName, mStatus, mRole;
    private Button mProfil_btn, mImage_Btn, mGanti_Btn;
    String name, image, role, status, thumb_image,current_uid,download_url;
    private ProgressDialog mProgress,mImageProgress;
    private ProgressBar mProgressBar;
    private Bitmap thumb_bitmap;

    private static final int gallery_pick=1;

    //firebase Storage
    private StorageReference mImageStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mImage = (CircleImageView) findViewById(R.id.circleImageView);
        mName = (TextView) findViewById(R.id.settings_txtNama);
        mStatus = (TextView) findViewById(R.id.settings_txtStatus);
        mRole = (TextView) findViewById(R.id.settings_txtKonsel);

        mProfil_btn=(Button) findViewById(R.id.settings_btnGantiNamaStatus);
        mImage_Btn=(Button) findViewById(R.id.settings_btnGantiFoto);
        mGanti_Btn=(Button) findViewById(R.id.settings_btnGantiPassword);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        current_uid = mCurrentUser.getUid();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
        mUserDatabase.keepSynced(true); //buat offline capabilies.

        mAuth = FirebaseAuth.getInstance();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);


        mImageStorage = FirebaseStorage.getInstance().getReference();

        mProgress = new ProgressDialog(SettingsActivity.this);


        mProgress.setTitle("Mencari data anda");
        mProgress.setMessage("Harap menunggu");
        mProgress.setCanceledOnTouchOutside(false);
        mProgress.show();

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //untuk ngubah data
                //datasnapshot ngambil data dari child nya itu

                name = dataSnapshot.child("name").getValue().toString();
                image = dataSnapshot.child("image").getValue().toString();
                role = dataSnapshot.child("role").getValue().toString();
                status = dataSnapshot.child("status").getValue().toString();
                thumb_image = dataSnapshot.child("thumb_image").getValue().toString();

                mName.setText(name);
                mStatus.setText(status);
                mRole.setText(role);

                if(!image.equals("default")) {

                    //update data download dan upload
                    //mProgressBar.setVisibility(View.VISIBLE);
                    Picasso.with(SettingsActivity.this)
                            .load(image)
                            .placeholder(R.drawable.avatar_default)
                            .into(mImage, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            //Success image already loaded into the view
                            //mProgress.dismiss();
                           // if (mProgressBar != null) {
                                //mProgressBar.setVisibility(View.GONE);
                           // }

                            //Toast.makeText(SettingsActivity.this, "BERHASIL", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onError() {
                            //Error placeholder image already loaded into the view, do further handling of this situation here

                            Toast.makeText(SettingsActivity.this, "ERROR saat mendownload gambar", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //handling data error atau enggak valid
            }
        });

        mName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                mProgress.dismiss();
            }
        });

        mProfil_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profil_intent = new Intent(SettingsActivity.this, StatusActivity.class);
                profil_intent.putExtra("status_profil",status);
                profil_intent.putExtra("name_profil",name);
                startActivity(profil_intent);
            }
        });

        mImage_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /************intent buka galery khusus image
                Intent gallery_intent =  new Intent();
                gallery_intent.setType("image/*");
                gallery_intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery_intent,"PILIH FOTO"), gallery_pick);
                 */

                //Menggunakan library
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(SettingsActivity.this);
            }
        });

        mGanti_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profil_intent = new Intent(SettingsActivity.this, PasswordActivity.class);
                startActivity(profil_intent);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                mImageProgress = new ProgressDialog(SettingsActivity.this);
                mImageProgress.setTitle("Sedang mengunggah foto anda...");
                mImageProgress.setMessage("Harap menunggu");
                mImageProgress.setCanceledOnTouchOutside(false);
                mImageProgress.show();

                Uri imageUri = result.getUri();
                //ubah ke bitmap untuk thumbnails
                final File thumb_filePath = new File(imageUri.getPath());

                try {
                    thumb_bitmap = new Compressor(this)
                            .setMaxWidth(200)
                            .setMaxHeight(200)
                            .setQuality(75)
                            .compressToBitmap(thumb_filePath);
                }
                catch(IOException e) {}

                //upload bitmap ke firebase,  di arraykan
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                final byte[] thumb_byte = baos.toByteArray();

                //upload ke firebase
                StorageReference filepath =  mImageStorage.child("profile_images").child("Foto-"+current_uid+".jpg");
                final StorageReference thumb_storagePath = mImageStorage.child("profile_images").child("thumbs").child("Thumb-"+current_uid+".jpg");

                filepath.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if(task.isSuccessful())
                        {

                            download_url=task.getResult().getDownloadUrl().toString();

                            UploadTask uploadTask = thumb_storagePath.putBytes(thumb_byte);
                            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> thumb_task) {

                                    final String thumb_downloadUrl = thumb_task.getResult().getDownloadUrl().toString();

                                    if(thumb_task.isSuccessful()){

                                        Map update_hashMap = new HashMap();
                                        update_hashMap.put("image", download_url);
                                        update_hashMap.put("thumb_image", thumb_downloadUrl);

                                        mUserDatabase.updateChildren(update_hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful())
                                                {
                                                    mImageProgress.dismiss();
                                                    Toast.makeText(SettingsActivity.this, "Berhasil mengunggah foto anda!", Toast.LENGTH_SHORT).show();
                                                }
                                                else
                                                {

                                                }
                                            }
                                        });
                                    }
                                    else {
                                        Toast.makeText(SettingsActivity.this, "Gagal mengunggah foto thumbnail anda\nPeriksa koneksi internet anda", Toast.LENGTH_SHORT).show();
                                        mImageProgress.dismiss();
                                    }

                                }
                            });



                        }
                        else
                        {
                            mImageProgress.dismiss();
                            Toast.makeText(SettingsActivity.this, "Gagal mengunggah foto anda\nPeriksa koneksi internet anda", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //Toast.makeText(SettingsActivity.this, "image uri: \n"+imageUri, Toast.LENGTH_LONG).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }
    }


}
