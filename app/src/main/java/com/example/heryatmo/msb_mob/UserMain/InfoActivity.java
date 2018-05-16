package com.example.heryatmo.msb_mob.UserMain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.adapter.PostAdapter;
import com.example.heryatmo.msb_mob.model.Post;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.response.TestResponse;
import com.example.heryatmo.msb_mob.remote.RetroClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InfoActivity extends AppCompatActivity {

   private RecyclerView recyclerView;
   private List<Post> data;
   private PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        recyclerView = findViewById(R.id.rv_post_list);
        loadJSON();
    }

    private void recycleList(TestResponse testResponse){
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PostAdapter(testResponse.getMData());
        recyclerView.setAdapter(adapter);
    }

    private void loadJSON(){
        Retrofit retrofit = RetroClient.getClient();

        APIService request = retrofit.create(APIService.class);
        Call<TestResponse> call = request.getPost();
        call.enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                recycleList(response.body());
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}
