package com.konselingperkawinan;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private ImageView mProfileImage;
    private TextView mProfileName, mProfileStatus, mProfileFriendsCount, mProfileRole,mDisplayID;

    private String user_id;

    private Button mProfileSendReqBtn, mDeclineBtn;

    private DatabaseReference mUsersDatabase;

    private ProgressDialog mProgressDialog;

    private DatabaseReference mFriendReqDatabase;
    private DatabaseReference mFriendDatabase;
    private DatabaseReference mNotificationDatabase;

    private DatabaseReference mRootRef;

    private FirebaseUser mCurrent_user;

    private String mCurrent_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user_id = getIntent().getStringExtra("user_id");

//        mDisplayID = (TextView) findViewById(R.id.profile_name);
//        mDisplayID.setText("User: \n"+user_id);

        mRootRef = FirebaseDatabase.getInstance().getReference();

        //ambil user_id disini
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

        //tambah instance baru yaitu friend_req
        mFriendReqDatabase = FirebaseDatabase.getInstance().getReference().child("Friend_req");

        mFriendDatabase = FirebaseDatabase.getInstance().getReference().child("Friends");
        mNotificationDatabase = FirebaseDatabase.getInstance().getReference().child("notifications");
        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();

        mProfileImage = (ImageView) findViewById(R.id.profile_image);
        mProfileName = (TextView) findViewById(R.id.profile_displayName);
        mProfileStatus = (TextView) findViewById(R.id.profile_status);
        mProfileFriendsCount = (TextView) findViewById(R.id.profile_totalFriends);
        mProfileSendReqBtn = (Button) findViewById(R.id.profile_send_req_btn);
        mDeclineBtn = (Button) findViewById(R.id.profile_decline_btn);
        mProfileRole = (TextView) findViewById(R.id.profile_role);


        mCurrent_state = "not_friends";

        mDeclineBtn.setVisibility(View.INVISIBLE);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Memuat profil pengguna");
        mProgressDialog.setMessage("Harap menunggu...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Batalkan Konseling");
        builder.setMessage("Apakah anda yakin?");

        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String display_name = dataSnapshot.child("name").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();
                String role = dataSnapshot.child("role").getValue().toString();

                mProfileName.setText(display_name);
                mProfileStatus.setText(status);
                mProfileRole.setText(role);

                Picasso.with(ProfileActivity.this).load(image).placeholder(R.drawable.avatar_default).into(mProfileImage);

                //____________________ REQUEST FRIEND FEATURE _______________
                mFriendReqDatabase.child(mCurrent_user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(user_id))
                        {
                            String req_type = dataSnapshot.child(user_id).child("request_type").getValue().toString();

                            if(req_type.equals("received"))
                            {
                                mProfileSendReqBtn.setEnabled(true);
                                mCurrent_state="req_received";
                                mProfileSendReqBtn.setText("TERIMA PERMINTAAN KONSELING");

                                mDeclineBtn.setVisibility(View.VISIBLE);
                                mDeclineBtn.setEnabled(true);
                            }
                            else if(!req_type.equals("sent"))
                            {
                                mCurrent_state="req_sent";
                                mProfileSendReqBtn.setText("BATAL PERMINTAAN KONSELING");

                                mDeclineBtn.setVisibility(View.INVISIBLE);
                                mDeclineBtn.setEnabled(false);
                            }

                            mProgressDialog.dismiss();
                        }
                        else
                        {
                            mFriendDatabase.child(mCurrent_user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild(user_id)){


                                        mCurrent_state = "friends";
                                        mProfileSendReqBtn.setText("BERHENTI KONSELING");
                                    }
                                    mProgressDialog.dismiss();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    mProgressDialog.dismiss();
                                }
                            });
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mProgressDialog.dismiss();
            }
        });

        mProfileSendReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mProfileSendReqBtn.setEnabled(false);

                //___________________ NOT FRIENDS STATE  __________________

                if(mCurrent_state.equals("not_friends")){
                     mFriendReqDatabase.child(mCurrent_user.getUid()).child(user_id)
                             .child("request_type").setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             if(task.isSuccessful())
                             {
                                    mFriendReqDatabase.child(user_id).child(mCurrent_user.getUid())
                                            .child("request_type").setValue("received").addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            HashMap<String, String> notificationData = new HashMap<>();
                                            notificationData.put("from", mCurrent_user.getUid());
                                            notificationData.put("type", "request");

                                            mNotificationDatabase.child(user_id).push().setValue(notificationData)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    HashMap<String, String > notificationData = new HashMap<>();
                                                    notificationData.put("from", mCurrent_user.getUid());
                                                    notificationData.put("type","request");

                                                    mNotificationDatabase.child(user_id).push().setValue(notificationData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            mCurrent_state="req_sent";
                                                            mProfileSendReqBtn.setText("BATALKAN PERMINTAAN KONSELING");
                                                            //mProfileSendReqBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));

                                                            mDeclineBtn.setVisibility(View.INVISIBLE);
                                                            mDeclineBtn.setEnabled(false);

                                                            Toast.makeText(ProfileActivity.this, "Permintaan konseling berhasil!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });


                                                }
                                            });


                                        }
                                    });
                             }
                             else
                             {
                                 Toast.makeText(ProfileActivity.this, "Gagal meminta konseling", Toast.LENGTH_SHORT).show();
                             }
                             mProfileSendReqBtn.setEnabled(true);
                         }
                     });
                }

                //___________________ CANCEL REQUEST STATE __________________

                if(mCurrent_state.equals("req_sent")){

                    mFriendReqDatabase.child(mCurrent_user.getUid()).child(user_id).removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mFriendReqDatabase.child(user_id).child(mCurrent_user.getUid()).removeValue()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            mProfileSendReqBtn.setEnabled(true);
                                            mCurrent_state = "not_friends";
                                            mProfileSendReqBtn.setText("KIRIM PERMINTAAN KONSELING");
                                           // mProfileSendReqBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                                            mDeclineBtn.setVisibility(View.INVISIBLE);
                                            mDeclineBtn.setEnabled(false);
                                        }
                                    });
                        }
                    });
                }

                //___________________ACCEPT REQUEST RECEIVED STATE __________________

                if(mCurrent_state.equals("req_received")){

                    final String currentDate = DateFormat.getDateTimeInstance().format(new Date());

                    mFriendDatabase.child(mCurrent_user.getUid()).child(user_id).setValue(currentDate)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            mFriendDatabase.child(user_id).child(mCurrent_user.getUid()).setValue(currentDate)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    //BECAUSE IT ALREADY ACCEPTED FRIEND, THE REQUEST HAS TO BE REMOVED!

                                    mFriendReqDatabase.child(mCurrent_user.getUid()).child(user_id).removeValue()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    mFriendReqDatabase.child(user_id).child(mCurrent_user.getUid()).removeValue()
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    mProfileSendReqBtn.setEnabled(true);
                                                                    mCurrent_state = "friends";
                                                                    mProfileSendReqBtn.setText("BERHENTI KONSELING");
                                                                    // mProfileSendReqBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                                                                    mDeclineBtn.setVisibility(View.INVISIBLE);
                                                                    mDeclineBtn.setEnabled(false);
                                                                }
                                                            });
                                                }
                                            });
                                }
                            });
                        }
                    });
                }

                //TIDAK BERTEMAN LAGI!!
                if(mCurrent_state.equals("friends"))
                {


                    builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                            mFriendDatabase.child(mCurrent_user.getUid()).child(user_id).removeValue()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            mFriendDatabase.child(user_id).child(mCurrent_user.getUid()).removeValue()
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            mProfileSendReqBtn.setEnabled(true);
                                                            mCurrent_state = "not_friends";
                                                            mProfileSendReqBtn.setText("KIRIM PERMINTAAN KONSELING");
                                                            // mProfileSendReqBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                                        }
                                                    });
                                        }
                                    });

                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // Do nothing
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();

                }
            }
        });
    }
}

TIDAK ADA MDECLINEBUTON HARAP DIPERHATIKAN !!!
