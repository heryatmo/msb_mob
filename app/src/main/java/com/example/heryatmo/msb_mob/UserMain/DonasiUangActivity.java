package com.example.heryatmo.msb_mob.UserMain;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;

import android.os.AsyncTask;
import android.provider.MediaStore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class DonasiUangActivity extends AppCompatActivity {


    Button bUpload,bSubmitUang;
    Spinner spJenis;
    TextView txtTitle,txtJumlahUang,txtKeterangan;
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donasi_uang);

        initSpinnerJenis();

        SharedPreferences sp = getSharedPreferences("SPLog", Context.MODE_PRIVATE);
        id_user  = sp.getString("id_user","-");

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
                Intent it = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(it, 101);
                flag = true;
            }
        });

        bSubmitUang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDonasi();
            }
        });




    }

    private void initSpinnerJenis(){

        spJenis = findViewById(R.id.spJenis);
        Retrofit retrofit = RetroClient.getClient();
        APIService request = retrofit.create(APIService.class);
        Call<JenisResponse> call = request.getJenis();
        call.enqueue(new Callback<JenisResponse>() {
            @Override
            public void onResponse(Call<JenisResponse> call, Response<JenisResponse> response) {
                List<Jenis> semuaJenis = response.body().getMData();
                List<String> listSpinner = new ArrayList<String>();
                for(int i=0; i < semuaJenis.size() ; i++){
                    listSpinner.add(semuaJenis.get(i).getMNamaJenis());
                }
                if(getApplicationContext()!=null) {
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


    private void createDonasi(){
        Donasi dataDonasi = Donasi.builder()
                .mIdJenis(spJenis.getSelectedItem().toString())
                .mIdUser(id_user)
                .mJumlahDonasi(txtJumlahUang.getText().toString())
                .mBuktiTransfer(imageView.getContext().toString())
                .mKeterangan(txtKeterangan.getText().toString())
                .build();

        Retrofit retrofit = RetroClient.getClient();
        Call<DonasiResponse> call = retrofit.create(APIService.class).donasiRequest(dataDonasi,id_user);
        call.enqueue(new Callback<DonasiResponse>() {
            @Override
            public void onResponse(Call<DonasiResponse> call, Response<DonasiResponse> response) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class );
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<DonasiResponse> call, Throwable t) {
                Log.i("Failed","Insert Gagal");
                Toast.makeText(getBaseContext(), "Gagal Masuk", Toast.LENGTH_LONG).show();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent){
        super.onActivityResult(requestCode,resultCode,imageReturnedIntent);
        switch (requestCode){

            case 101:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    String filename = getRealPathFromURI(selectedImage);
                    bi = BitmapFactory.decodeFile(filename);
                    imageView.setImageURI(selectedImage);
                    imageView.setVisibility(View.VISIBLE);

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

            try {
                load(bi);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);


            imageView.setImageBitmap(bi);
            dismissDialog(0);
            isColored = true;
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
