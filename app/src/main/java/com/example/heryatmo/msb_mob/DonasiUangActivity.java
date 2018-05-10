package com.example.heryatmo.msb_mob;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class DonasiUangActivity extends AppCompatActivity {

    private  int RESULT_LOAD_IMAGE = 1;
    Button bUpload;
    ImageView img;
    TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donasi_uang);

        img = (ImageView) findViewById(R.id.imgView);
        txtTitle = findViewById(R.id.txtUploadImage);
        bUpload = findViewById(R.id.btnUpload);
        bUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent();
               intent.setType("image/*");
               intent.setAction(Intent.ACTION_GET_CONTENT);
               startActivityForResult(Intent.createChooser(intent,"Select picture"),RESULT_LOAD_IMAGE);
            }
        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==RESULT_LOAD_IMAGE && requestCode==RESULT_OK && data!=null)
        {
            Uri path = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                img.setImageBitmap(bitmap);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }

}
