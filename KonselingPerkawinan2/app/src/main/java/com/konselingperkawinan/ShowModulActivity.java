package com.konselingperkawinan;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowModulActivity extends AppCompatActivity {

    String modul_id, judul_modul, isi_modul, sub_judul_modul;

    private DatabaseReference mModulDatabase;

    private WebView modulWebView;

    private Toolbar mToolbar;

    private DatabaseReference mUserRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_modul);

        mAuth = FirebaseAuth.getInstance();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());


        MainActivity.mViewPager.setCurrentItem(2);

        mToolbar = (Toolbar) findViewById(R.id.modul_appbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        modul_id = getIntent().getStringExtra("modul_id");

        mModulDatabase = FirebaseDatabase.getInstance().getReference().child("Modul").child(modul_id);
        mModulDatabase.keepSynced(true);

        modulWebView = (WebView) findViewById(R.id.modul_webview);

        mModulDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                judul_modul = dataSnapshot.child("judul_modul").getValue().toString();
                isi_modul = dataSnapshot.child("isi_modul").getValue().toString();

                sub_judul_modul = judul_modul.substring(0,8);
                getSupportActionBar().setTitle(sub_judul_modul);
                modulWebView.loadData(isi_modul, "text/html", null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
