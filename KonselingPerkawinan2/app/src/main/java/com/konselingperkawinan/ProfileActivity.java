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
import com.google.firebase.database.ServerValue;
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
    private DatabaseReference mChatDatabase;
    private DatabaseReference mMsgDatabase;

    private DatabaseReference mUserRef;
    private FirebaseAuth mAuth;

    private DatabaseReference mRootRef;

    private FirebaseUser mCurrent_user;

    private String mCurrent_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user_id = getIntent().getStringExtra("from_user_id");

//        mDisplayID = (TextView) findViewById(R.id.profile_name);
//        mDisplayID.setText("User: \n"+user_id);

        mRootRef = FirebaseDatabase.getInstance().getReference();


        //ambil user_id disini
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

        //tambah instance baru yaitu friend_req
        mFriendReqDatabase = FirebaseDatabase.getInstance().getReference().child("Friend_req");

        //buat hapus chat
        mChatDatabase = FirebaseDatabase.getInstance().getReference().child("Chat");
        mMsgDatabase = FirebaseDatabase.getInstance().getReference().child("messages");

        mFriendDatabase = FirebaseDatabase.getInstance().getReference().child("Friends");
        mNotificationDatabase = FirebaseDatabase.getInstance().getReference().child("notifications");
        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();

        mAuth = FirebaseAuth.getInstance();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());


        mProfileImage = (ImageView) findViewById(R.id.profile_image);
        mProfileName = (TextView) findViewById(R.id.profile_displayName);
        mProfileStatus = (TextView) findViewById(R.id.profile_status);
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
        builder.setMessage("Apakah anda yakin?\nAnda tidak bisa mengirim pesan pada kontak tersebut");

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

                //--------------- FRIENDS LIST / REQUEST FEATURE -----

                mFriendReqDatabase.child(mCurrent_user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.hasChild(user_id)){

                            String req_type = dataSnapshot.child(user_id).child("request_type").getValue().toString();

                            if(req_type.equals("received")){

                                mCurrent_state = "req_received";
                                mProfileSendReqBtn.setText("TERIMA PERMINTAAN KONSELING");

                                mDeclineBtn.setVisibility(View.VISIBLE);
                                mDeclineBtn.setEnabled(true);


                            } else if(req_type.equals("sent")) {

                                mCurrent_state = "req_sent";
                                mProfileSendReqBtn.setText("BATALKAN PERMINTAAN KONSELING");

                                mDeclineBtn.setVisibility(View.INVISIBLE);
                                mDeclineBtn.setEnabled(false);

                            }

                            mProgressDialog.dismiss();

                        } else {

                            mFriendDatabase.child(mCurrent_user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    if(dataSnapshot.hasChild(user_id)){

                                        mCurrent_state = "friends";
                                        mProfileSendReqBtn.setText("BERHENTI KONSELING");

                                        mDeclineBtn.setVisibility(View.INVISIBLE);
                                        mDeclineBtn.setEnabled(false);

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

                // --------------- NOT FRIENDS STATE ------------

                if(mCurrent_state.equals("not_friends")){


                    DatabaseReference newNotificationref = mRootRef.child("notifications").child(user_id).push();
                    String newNotificationId = newNotificationref.getKey();

                    HashMap<String, String> notificationData = new HashMap<>();
                    notificationData.put("from", mCurrent_user.getUid());
                    notificationData.put("type", "request");

                    Map requestMap = new HashMap();
                    requestMap.put("Friend_req/" + mCurrent_user.getUid() + "/" + user_id + "/request_type", "sent");
                    requestMap.put("Friend_req/" + user_id + "/" + mCurrent_user.getUid() + "/request_type", "received");
                    requestMap.put("notifications/" + user_id + "/" + newNotificationId, notificationData);

                    mRootRef.updateChildren(requestMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                            if(databaseError != null){

                                Toast.makeText(ProfileActivity.this, "Error\nSaat mengirm permintaan", Toast.LENGTH_LONG).show();

                            } else {

                                mCurrent_state = "req_sent";
                                mProfileSendReqBtn.setText("BATALKAN PERMINTAAN KONSELING");
                                Toast.makeText(ProfileActivity.this, "Permintaan Konseling Terkirim!", Toast.LENGTH_LONG).show();

                            }

                            mProfileSendReqBtn.setEnabled(true);


                        }
                    });

                }


                // - -------------- CANCEL REQUEST STATE ------------

                if(mCurrent_state.equals("req_sent")){

                    mFriendReqDatabase.child(mCurrent_user.getUid()).child(user_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            mFriendReqDatabase.child(user_id).child(mCurrent_user.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {


                                    mProfileSendReqBtn.setEnabled(true);
                                    mCurrent_state = "not_friends";
                                    mProfileSendReqBtn.setText("Kirim Permintaan Konseling");

                                    mDeclineBtn.setVisibility(View.INVISIBLE);
                                    mDeclineBtn.setEnabled(false);

                                    Toast.makeText(ProfileActivity.this, "Pembatalan Permintaan Konseling Berhasil!", Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    });



                }


                // ------------ REQ RECEIVED STATE ----------

                if(mCurrent_state.equals("req_received")){

                    final String currentDate = DateFormat.getDateTimeInstance().format(new Date());

                    Map friendsMap = new HashMap();
                    friendsMap.put("Friends/" + mCurrent_user.getUid() + "/" + user_id + "/date", currentDate);
                    friendsMap.put("Friends/" + user_id + "/"  + mCurrent_user.getUid() + "/date", currentDate);


                    friendsMap.put("Friend_req/" + mCurrent_user.getUid() + "/" + user_id, null);
                    friendsMap.put("Friend_req/" + user_id + "/" + mCurrent_user.getUid(), null);


                    mRootRef.updateChildren(friendsMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {


                            if(databaseError == null){

                                mProfileSendReqBtn.setEnabled(true);
                                mCurrent_state = "friends";
                                mProfileSendReqBtn.setText("BERHENTI KONSELING");

                                mDeclineBtn.setVisibility(View.INVISIBLE);
                                mDeclineBtn.setEnabled(false);

                                Toast.makeText(ProfileActivity.this, "Permintaan Penerimaan Konseling Berhasil", Toast.LENGTH_LONG).show();

                            } else {

                                String error = databaseError.getMessage();

                                Toast.makeText(ProfileActivity.this, error, Toast.LENGTH_LONG).show();

                            }

                        }
                    });

                }


                // ------------ UNFRIENDS ---------

                if(mCurrent_state.equals("friends")){

                    builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            //ngebuat null nilainya maka akan otomatis terhapus
                            Map unfriendMap = new HashMap();
                            unfriendMap.put("Friends/" + mCurrent_user.getUid() + "/" + user_id, null);
                            unfriendMap.put("Friends/" + user_id + "/" + mCurrent_user.getUid(), null);

                            mRootRef.updateChildren(unfriendMap, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {


                                    if(databaseError == null){

                                        mChatDatabase.child(mCurrent_user.getUid()).child(user_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {

                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                mChatDatabase.child(user_id).child(mCurrent_user.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {

                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        mCurrent_state = "not_friends";
                                                        mProfileSendReqBtn.setText("KIRIM PERMINTAAN KONSELING");

                                                        mDeclineBtn.setVisibility(View.INVISIBLE);
                                                        mDeclineBtn.setEnabled(false);

                                                        Toast.makeText(ProfileActivity.this, "Konseling telah Berhenti", Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                            }
                                        });
                                    } else {

                                        String error = databaseError.getMessage();

                                        Toast.makeText(ProfileActivity.this, error, Toast.LENGTH_LONG).show();

                                    }

                                    mProfileSendReqBtn.setEnabled(true);

                                }
                            });
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

        mDeclineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFriendReqDatabase.child(mCurrent_user.getUid()).child(user_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        mFriendReqDatabase.child(user_id).child(mCurrent_user.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {


                                mProfileSendReqBtn.setEnabled(true);
                                mCurrent_state = "not_friends";
                                mProfileSendReqBtn.setText("Kirim Permintaan Konseling");

                                mDeclineBtn.setVisibility(View.INVISIBLE);
                                mDeclineBtn.setEnabled(false);

                                Toast.makeText(ProfileActivity.this, "Permintaan Konseling ditolak!", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                });
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
