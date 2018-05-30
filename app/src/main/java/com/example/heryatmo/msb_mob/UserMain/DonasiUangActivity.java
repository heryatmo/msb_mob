package com.example.heryatmo.msb_mob.UserMain;

import android.Manifest;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;

import android.os.AsyncTask;
import android.provider.MediaStore;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.model.Donasi;
import com.example.heryatmo.msb_mob.model.Jenis;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.RetroClient;
import com.example.heryatmo.msb_mob.response.DonasiResponse;
import com.example.heryatmo.msb_mob.response.JenisResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class DonasiUangActivity extends AppCompatActivity {


    List<Jenis> jenis = new ArrayList<>();

    Button bUpload, bSubmitUang;
    Spinner spJenis;
    TextView txtTitle, txtJumlahUang, txtKeterangan;
    ImageView imageView;
    boolean flag;

    String id_user;
    Bitmap bi = null;

    boolean isColored;

    private int SIZE = 256;
    // Red, Green, Blue
    private int NUMBER_OF_COLOURS = 3;

    public final int RED = 0;
    public final int GREEN = 1;
    public final int BLUE = 2;

    private int[][] colourBins;
    private volatile boolean loaded = false;

    float offset = 1;
    private String imageString = "";

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donasi_uang);

        initSpinnerJenis();

        SharedPreferences sp = getSharedPreferences("SPLog", Context.MODE_PRIVATE);
        id_user = sp.getString("id_user", "-");

        spJenis = findViewById(R.id.spJenis);
        txtJumlahUang = findViewById(R.id.txtJumlahDonasi);
        txtKeterangan = findViewById(R.id.txtKeteranganDon);

        imageView = (ImageView) findViewById(R.id.imgView);
        txtTitle = findViewById(R.id.txtUploadImage);
        bUpload = findViewById(R.id.btnUpload);
        bSubmitUang = findViewById(R.id.btnSubmitUang);


        bUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(DonasiUangActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getBaseContext(), "Permission is not granted44", Toast.LENGTH_LONG).show();
                    if (ActivityCompat.shouldShowRequestPermissionRationale(DonasiUangActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                        Toast.makeText(getBaseContext(), "wekwkekw", Toast.LENGTH_LONG).show();

                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(DonasiUangActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1
                        );

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Permission is not granted", Toast.LENGTH_LONG).show();
                    Intent it = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(it, 101);
                    flag = true;
                }


            }
        });


        bSubmitUang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDonasi();
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void initSpinnerJenis() {

        spJenis = findViewById(R.id.spJenis);
        Retrofit retrofit = RetroClient.getClient();
        APIService request = retrofit.create(APIService.class);
        Call<JenisResponse> call = request.getJenis();
        call.enqueue(new Callback<JenisResponse>() {
            @Override
            public void onResponse(Call<JenisResponse> call, Response<JenisResponse> response) {
                jenis = response.body().getMData();
                List<String> listSpinner = new ArrayList<String>();
                for (int i = 0; i < jenis.size(); i++) {
                    listSpinner.add(jenis.get(i).getMNamaJenis());
                }
                if (getApplicationContext() != null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                            R.layout.support_simple_spinner_dropdown_item, listSpinner);
                    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spJenis.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<JenisResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void createDonasi() {
        String idJenis = String.valueOf(jenis.get(spJenis.getSelectedItemPosition()).getMIdJenis());
        Donasi dataDonasi = Donasi.builder()
                .mIdJenis(idJenis)
                .mIdUser(id_user)
                .mJumlahDonasi(txtJumlahUang.getText().toString())
                .mBuktiTransfer(imageString)
                .mKeterangan(txtKeterangan.getText().toString())
                .build();
        Log.d("jancuk", dataDonasi.getMIdJenis() + "#" + dataDonasi.getMIdUser() + "#" + jenis.get(spJenis.getSelectedItemPosition()));
        Retrofit retrofit = RetroClient.getClient();
        Call<DonasiResponse> call = retrofit.create(APIService.class).donasiRequest(dataDonasi, id_user);
        call.enqueue(new Callback<DonasiResponse>() {
            @Override
            public void onResponse(Call<DonasiResponse> call, Response<DonasiResponse> response) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<DonasiResponse> call, Throwable t) {
                Log.i("Failed", "Insert Gagal");
                Toast.makeText(getBaseContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {

            case 101:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    String filename = getRealPathFromURI(selectedImage);
                    bi = BitmapFactory.decodeFile(filename);
                    imageView.setImageURI(selectedImage);
                    imageView.setVisibility(View.VISIBLE);


                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bi.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                    byte[] imageBytes = baos.toByteArray();
                    imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                    Log.d("IMG STRING", imageString);
                    if (bi != null) {
                        try {
                            new MyAsync().execute();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        Log.e("TEST", "GetRealPath : " + contentUri);

        try {
            if (contentUri.toString().contains("video")) {
                String[] proj = {MediaStore.Video.Media.DATA};
                Cursor cursor = managedQuery(contentUri, proj, null, null, null);
                int column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            } else {
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(contentUri, proj, null, null, null);
                int column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            }
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    class MyAsync extends AsyncTask {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            showDialog(0);
        }

        @Override
        protected Object doInBackground(Object... params) {
            // TODO Auto-generated method stub
//
//            try {
//                load(bi);
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }

            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);


//            imageView.setImageBitmap(bi);
//            dismissDialog(0);
//            isColored = true;
        }

    }

    public void load(Bitmap bi) throws IOException {

        if (bi != null) {
            // Reset all the bins
            for (int i = 0; i < NUMBER_OF_COLOURS; i++) {
                for (int j = 0; j < SIZE; j++) {
                    colourBins[i][j] = 0;
                }
            }

            for (int x = 0; x < bi.getWidth(); x++) {
                for (int y = 0; y < bi.getHeight(); y++) {

                    int pixel = bi.getPixel(x, y);

                    colourBins[RED][Color.red(pixel)]++;
                    colourBins[GREEN][Color.green(pixel)]++;
                    colourBins[BLUE][Color.blue(pixel)]++;
                }
            }

            int maxY = 0;

            for (int i = 0; i < NUMBER_OF_COLOURS; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (maxY < colourBins[i][j]) {
                        maxY = colourBins[i][j];
                    }
                }
            }
            loaded = true;
        } else {
            loaded = false;
        }
    }

}
