package com.konselingperkawinan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class ShowFaqActivity extends AppCompatActivity {

    String faq_id, pertanyaan, jawaban, sub_pertanyaan;

    private DatabaseReference mFaqDatabase;

    private WebView faqWebView;

    private Toolbar mToolbar;
    private TextView text_Pertanyaan;

    private DatabaseReference mUserRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_faq);

        mAuth = FirebaseAuth.getInstance();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());


        MainActivity.mViewPager.setCurrentItem(3);

        text_Pertanyaan = (TextView) findViewById(R.id.text_Pertanyaan);
        mToolbar = (Toolbar) findViewById(R.id.faq_appbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        faq_id = getIntent().getStringExtra("faq_id");

        mFaqDatabase = FirebaseDatabase.getInstance().getReference().child("FAQ").child(faq_id);
        mFaqDatabase.keepSynced(true);

        faqWebView = (WebView) findViewById(R.id.faq_webview);

        mFaqDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pertanyaan = dataSnapshot.child("pertanyaan").getValue().toString();
                jawaban = dataSnapshot.child("jawaban").getValue().toString();


                Spanned htmltext = Html.fromHtml(pertanyaan);
                text_Pertanyaan.setText(htmltext);

                sub_pertanyaan = pertanyaan.substring(0,8);
                getSupportActionBar().setTitle("FAQ");
                faqWebView.loadData(jawaban, "text/html", null);
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
