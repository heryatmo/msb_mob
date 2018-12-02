package com.konselingperkawinan;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mUserList;

    private FirebaseUser mCurrentUser;
    String current_uid;

    private DatabaseReference mUserRef;
    private FirebaseAuth mAuth;

    private DatabaseReference mUserDatabase;
    private Query mQuery;

    private String role2;

    private DatabaseReference mFriendReqDatabase, mRootRef;
    private DatabaseReference mHeaderDatabase;
    private DatabaseReference mFriendReqDatabaseREF;
    private DatabaseReference mContactsDatabaseREF;
    private TextView headerReq, headerKontak;
    private RecyclerView mRequestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        mAuth = FirebaseAuth.getInstance();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mToolbar = (Toolbar) findViewById(R.id.users_appbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Tambah Kontak");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        current_uid = mCurrentUser.getUid();

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mRootRef.keepSynced(true);

        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
        mUserRef.keepSynced(true);

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUserDatabase.keepSynced(true);

        mFriendReqDatabase = FirebaseDatabase.getInstance().getReference().child("Friend_req").child(current_uid);
        mFriendReqDatabase.keepSynced(true);

        mHeaderDatabase = FirebaseDatabase.getInstance().getReference().child("Friend_req");
        mHeaderDatabase.keepSynced(true);

        mContactsDatabaseREF=FirebaseDatabase.getInstance().getReference().child("Friends");
        mContactsDatabaseREF.keepSynced(true);

        mFriendReqDatabaseREF=FirebaseDatabase.getInstance().getReference().child("Friend_req");
        mFriendReqDatabaseREF.keepSynced(true);

        role2 = MainActivity.role.toString();
        //role2 = getIntent().getStringExtra("roles");

        if(role2.equalsIgnoreCase("konselor"))
        {
            mQuery = mUserDatabase.orderByChild("role").equalTo("Konseli");
        }
        else
        {
            mQuery = mUserDatabase.orderByChild("role").equalTo("Konselor");
        }




        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        mUserList = (RecyclerView) findViewById(R.id.users_list_v2);
        mUserList.setHasFixedSize(true);
        mUserList.setLayoutManager(new LinearLayoutManager(this));
        mUserList.addItemDecoration(itemDecoration);
        mUserList.setNestedScrollingEnabled(false);

        mRequestList=(RecyclerView) findViewById(R.id.request_list);

        mRequestList.setHasFixedSize(true);
        mRequestList.setLayoutManager(new LinearLayoutManager(this));
        mRequestList.addItemDecoration(itemDecoration);
        mRequestList.setNestedScrollingEnabled(false);

        headerKontak = (TextView) findViewById(R.id.txt_headerKontak);
        headerReq = (TextView) findViewById(R.id.txt_headerReq);


        generateListUsers();
        generateListRequest();
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    private void generateListUsers() {
        //untuk masukin ke dalam view holdernya, jadi masukin satu2 sesuai class yang mengatur semuanya, layout yang masukin ke recyclernya, view holder, dan database referencenya
        FirebaseRecyclerAdapter<Users, UsersViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(

                Users.class,
                R.layout.user_lastseen_single_layout,
                UsersViewHolder.class,
                mQuery
        ) {

            @Override
            protected void populateViewHolder(final UsersViewHolder viewHolder, Users model, int position) {

                final String user_id1 = getRef(position).getKey();

                mUserDatabase.child(user_id1).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("online")) {

                            String userOnline = dataSnapshot.child("online").getValue().toString();
                            viewHolder.setUserOnline(userOnline);
                            viewHolder.setLastSeen(userOnline, getApplicationContext());

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                viewHolder.setName(model.getName());
                viewHolder.setStatus(model.getStatus());
                viewHolder.setRole(model.getRole());
                viewHolder.setImageThumb(model.getThumb_image(),getApplicationContext());

                if(!user_id1.equals(current_uid)) {
                    viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent profileIntent = new Intent(UsersActivity.this, ProfileActivity.class);
                            profileIntent.putExtra("from_user_id", user_id1);
                            startActivity(profileIntent);
                        }
                    });
                }
                else {
                    viewHolder.mView.setVisibility(View.GONE);
                    viewHolder.mView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                    viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent settingsIntent = new Intent(UsersActivity.this, SettingsActivity.class);
                            startActivity(settingsIntent);
                            Toast.makeText(UsersActivity.this, "Ini Akun Anda!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }


            }
        };

        mUserList.setAdapter(firebaseRecyclerAdapter);
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


    private void generateListRequest() {
        //REQUEST RECYCLER
        Query mQueryReq;
        mQueryReq = mFriendReqDatabase.orderByChild("request_type");

        FirebaseRecyclerAdapter<Request,RequestViewHolder> firebaseRecyclerAdapterReq
                = new FirebaseRecyclerAdapter<Request, RequestViewHolder>
                (
                        Request.class,
                        R.layout.request_single_layout,
                        RequestViewHolder.class,
                        mFriendReqDatabase

                )
        {
            @Override
            protected void populateViewHolder(final RequestViewHolder viewHolder, Request model, int position)
            {
                final String list_users_id = getRef(position).getKey();

                DatabaseReference get_type_ref = getRef(position).child("request_type").getRef();

                get_type_ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            String request_type = dataSnapshot.getValue().toString();

                            if(request_type.equals("received")){
                                mUserDatabase.child(list_users_id).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final String userName = dataSnapshot.child("name").getValue().toString();
                                        String userRole = dataSnapshot.child("role").getValue().toString();
                                        String userThumb = dataSnapshot.child("thumb_image").getValue().toString();
                                        String userStatus = dataSnapshot.child("status").getValue().toString();

                                        viewHolder.setNameReq(userName);
                                        viewHolder.setUserImageReq(userThumb, getApplicationContext());
                                        viewHolder.setRoleReq("MENERIMA");
                                        viewHolder.setStatusReq(userStatus);
                                        // viewHolder.setNewTextReq("Diterima");

                                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                CharSequence options[] = new CharSequence[]{"Terima permintaan konseling", "Tolak permintaan konseling"};

                                                final AlertDialog.Builder builder = new AlertDialog.Builder(UsersActivity.this);

                                                builder.setTitle("Pilih :");
                                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                        //Click Event for each item.
                                                        if(i == 0){ //terima

                                                            final String currentDate = DateFormat.getDateTimeInstance().format(new Date());

                                                            Map friendsMap = new HashMap();
                                                            friendsMap.put("Friends/" + current_uid + "/" + list_users_id + "/date", currentDate);
                                                            friendsMap.put("Friends/" + list_users_id + "/"  + current_uid + "/date", currentDate);


                                                            friendsMap.put("Friend_req/" + current_uid + "/" + list_users_id, null);
                                                            friendsMap.put("Friend_req/" + list_users_id + "/" + current_uid, null);


                                                            mRootRef.updateChildren(friendsMap, new DatabaseReference.CompletionListener() {
                                                                @Override
                                                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {


                                                                    if(databaseError == null){

                                                                        Toast.makeText(UsersActivity.this, "Permintaan Penerimaan Konseling Berhasil", Toast.LENGTH_LONG).show();

                                                                    } else {

                                                                        String error = databaseError.getMessage();

                                                                        Toast.makeText(UsersActivity.this, error, Toast.LENGTH_LONG).show();

                                                                    }

                                                                }
                                                            });

                                                        }

                                                        if(i == 1){ //tolak

                                                            mFriendReqDatabaseREF.child(current_uid).child(list_users_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {

                                                                    mFriendReqDatabaseREF.child(list_users_id).child(current_uid).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {

                                                                            Toast.makeText(UsersActivity.this, "Penolakan Permintaan Konseling Berhasil!", Toast.LENGTH_LONG).show();
                                                                        }
                                                                    });

                                                                }
                                                            });
                                                        }

                                                    }
                                                });

                                                builder.show();

                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                            else if(request_type.equals("sent")) {

                                mUserDatabase.child(list_users_id).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final String userName = dataSnapshot.child("name").getValue().toString();
                                        String userRole = dataSnapshot.child("role").getValue().toString();
                                        String userThumb = dataSnapshot.child("thumb_image").getValue().toString();
                                        String userStatus = dataSnapshot.child("status").getValue().toString();

                                        viewHolder.setNameReq(userName);
                                        viewHolder.setUserImageReq(userThumb, getApplicationContext());
                                        viewHolder.setRoleReq("Terkirim");
                                        viewHolder.setStatusReq(userStatus);
                                        //viewHolder.setNewTextReq("Terkirim");

                                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                CharSequence options[] = new CharSequence[]{"Batalkan Permintaan Konseling"};

                                                final AlertDialog.Builder builder = new AlertDialog.Builder(UsersActivity.this);

                                                builder.setTitle("Pilih :");
                                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                        //Click Event for each item.
                                                        if(i == 0){ //terima

                                                            mFriendReqDatabaseREF.child(current_uid).child(list_users_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {

                                                                    mFriendReqDatabaseREF.child(list_users_id).child(current_uid).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {

                                                                            Toast.makeText(UsersActivity.this, "Pembatalan Permintaan Konseling Berhasil!", Toast.LENGTH_LONG).show();
                                                                        }
                                                                    });

                                                                }
                                                            });

                                                        }
                                                    }
                                                });

                                                builder.show();

                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        };

        mRequestList.setAdapter(firebaseRecyclerAdapterReq);

        mHeaderDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(current_uid)){
                    mRequestList.setVisibility(View.VISIBLE);
                    //headerKontak.setVisibility(View.VISIBLE);
                    headerReq.setVisibility(View.VISIBLE);
                }
                else {
                    mRequestList.setVisibility(View.GONE);
                    //headerKontak.setVisibility(View.GONE);
                    headerReq.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(role2.equals("Konselor")) {
            headerKontak.setText("Daftar Konseli");
        }
        else{
            headerKontak.setText("Daftar Konselor");
        }

    }


    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setName(String name){
            TextView mUsersName = (TextView) mView.findViewById(R.id.user_lastseen_single_name);
            mUsersName.setText(name);
        }

        public void setStatus(String status) {
            TextView mUserStatus = (TextView) mView.findViewById(R.id.user_lastseen_single_status);
            mUserStatus.setText(status);
        }

        public void setRole(String role) {
            TextView mUsersRole = (TextView) mView.findViewById(R.id.user_lastseen_single_role);
            mUsersRole.setText(role);
        }

        public void setImageThumb(String thumb_image, Context context) {
            CircleImageView usersThumbImage = (CircleImageView) mView.findViewById(R.id.user_lastseen_single_image);

            Picasso.with(context).load(thumb_image).placeholder(R.drawable.avatar_default).into(usersThumbImage);
        }

        public void setUserOnline(String online_status) {

            ImageView userOnlineView = (ImageView) mView.findViewById(R.id.user_lastseen_single_online_icon);

            if(online_status.equals("true")){

                userOnlineView.setVisibility(View.VISIBLE);

            } else {

                userOnlineView.setVisibility(View.INVISIBLE);

            }

        }

        public void setLastSeen(String last_seen, Context ctx) {
            TextView mUsersLastSeen = (TextView) mView.findViewById(R.id.user_lastseen_single_time);

            if(last_seen.equals("true")){

                mUsersLastSeen.setText("online");

            } else {

                GetTimeAgo get_TimeAgo =new GetTimeAgo();
                long lastTime = Long.parseLong(last_seen);

                String lastSeenTime = get_TimeAgo.getTimeAgo(lastTime, ctx);

                mUsersLastSeen.setText("  "+lastSeenTime);

            }
        }
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder
    {
        View mView;

        public RequestViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setStatusReq(String status){

            TextView userNameView = (TextView) mView.findViewById(R.id.req_single_status);
            userNameView.setText(status);

        }

        public void setNameReq(String name){

            TextView userNameView = (TextView) mView.findViewById(R.id.req_single_name);
            userNameView.setText(name);

        }

        public void setRoleReq(String role){
            TextView userRoleView = (TextView) mView.findViewById(R.id.req_single_role);
            userRoleView.setBackgroundResource(R.drawable.rounded_rectangle_req);
            userRoleView.setTextColor(Color.parseColor("#FFFFFF"));
            userRoleView.setText(role);
        }

        public void setUserImageReq(String thumb_image, Context ctx){

            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.req_single_image);
            Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.avatar_default).into(userImageView);

        }

//        public void setNewTextReq(String newtext){
//            TextView userRoleView = (TextView) mView.findViewById(R.id.req_single_new);
//            userRoleView.setText(newtext);
//        }
    }
}

