package com.example.heryatmo.msb_mob;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.example.heryatmo.msb_mob.adapter.PostAdapter;
import com.example.heryatmo.msb_mob.model.Post;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.PostResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoActivity extends AppCompatActivity {

   private RecyclerView recyclerView;
   private ArrayList<Post> data;
   private PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


    }
//    private void view(){
//        recyclerView = findViewById(R.id.rv_post_list);
//        recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(layoutManager);
//        loadJSON();
//    }
//
//    private void loadJSON(){
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://msb.bluebox2.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        APIService request = retrofit.create(APIService.class);
//        Call<PostResponse> call = request.getPost();
//        call.enqueue(new Callback<PostResponse>() {
//            @Override
//            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
//                PostResponse postResponse = response.body();
//                data = new ArrayList<>(Arrays.asList(postResponse.getPost()));
//                adapter = new PostAdapter(data);
//                recyclerView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onFailure(Call<PostResponse> call, Throwable t) {
//                Log.d("Error", t.getMessage());
//            }
//        });
//    }
}
