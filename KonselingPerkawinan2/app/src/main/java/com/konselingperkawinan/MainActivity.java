package com.konselingperkawinan;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    public static String role;

    public static ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout mTabLayout;

    private DatabaseReference mUserRef;

    private int[] tabIcons = {
            R.drawable.ic_contacts,
            R.drawable.ic_chats,
            R.drawable.ic_moduls,
            R.drawable.ic_faq
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();


        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Konseling Perkawinan");

        //tabs
        mViewPager = (ViewPager) findViewById(R.id.main_tabPager);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mTabLayout = (TabLayout) findViewById(R.id.main_tabs);

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        setupTabIcons();
        mViewPager.setCurrentItem(1);

        if (mAuth.getCurrentUser() != null) {
            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());

            mUserRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    role = dataSnapshot.child("role").getValue().toString();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


}

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);

        if (currentUser == null){
            sendToStart();
        }
        else {

            mUserRef.child("online").setValue("true");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            mUserRef.child("online").setValue("true");
        }
    }

//        @Override
//    protected void onStop() {
//        super.onStop();
//
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        if (currentUser != null) {
//            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);
//        }
//    }

    private void sendToStart() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);
        }

        Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(item.getItemId() == R.id.main_logout_btn)
        {
            builder.setMessage("Apakah anda ingin Log out?")
                    .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            mUserRef.child("device_token").setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    FirebaseUser currentUser = mAuth.getCurrentUser();

                                    if (currentUser != null) {
                                        mUserRef.child("online").setValue(ServerValue.TIMESTAMP);
                                    }

                                    FirebaseAuth.getInstance().signOut();
                                    sendToStart();
                                }
                            });



                        }
                    })
                    .setNegativeButton("TIDAK",null);

            AlertDialog alert = builder.create();
            alert.show();
        }
        if(item.getItemId()== R.id.main_profil_btn)
        {
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        }
//        if(item.getItemId() == R.id.main_konseling_btn);
//        {
//
//        }

        if(item.getItemId() == R.id.main_users_btn)
        {
            Intent settingsIntent = new Intent(MainActivity.this, UsersActivity.class);
            settingsIntent.putExtra("roles",role);
            startActivity(settingsIntent);
        }
        return true;
    }

    private void setupTabIcons()
    {
        mTabLayout.getTabAt(0).setIcon(tabIcons[0]);
        mTabLayout.getTabAt(1).setIcon(tabIcons[1]);
        mTabLayout.getTabAt(2).setIcon(tabIcons[2]);
        mTabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }
}
