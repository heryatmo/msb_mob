package com.konselingperkawinan;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {

    private RecyclerView mContactsList;

    private DatabaseReference mContactsDatabase, mContactsDatabaseREF, mRootRef;
    private DatabaseReference mUsersDatabase;
    private DatabaseReference mFriendReqDatabase, mFriendReqDatabaseREF, mHeaderDatabase;


    private FirebaseAuth mAuth;
    private Query mQuery;

    private String mCurrent_user_id;

    private View mMainView;

    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        //MainActivity.mViewPager.setCurrentItem(0);

        mMainView = inflater.inflate(R.layout.fragment_contact, container, false);

        mAuth = FirebaseAuth.getInstance();
        mContactsList = (RecyclerView) mMainView.findViewById(R.id.contacts_list);

        mRootRef = FirebaseDatabase.getInstance().getReference();

        mCurrent_user_id = mAuth.getCurrentUser().getUid();

        mContactsDatabase = FirebaseDatabase.getInstance().getReference().child("Friends").child(mCurrent_user_id);
        mContactsDatabase.keepSynced(true);
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabase.keepSynced(true);


        mQuery = mContactsDatabase.orderByChild("name");
        mQuery.keepSynced(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());

        mContactsList.setHasFixedSize(true);
        mContactsList.setLayoutManager(new LinearLayoutManager(getContext()));
        mContactsList.addItemDecoration(itemDecoration);

        generateListKontak();

        // Inflate the layout for this fragment
        return mMainView;


    }

    @Override
    public void onStart() {
        super.onStart();

        generateListKontak();
        //generateListRequest();
    }


    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onPause() {
        super.onPause();


    }

    private void generateListKontak() {
        //CONTACT RECYCLER
        FirebaseRecyclerAdapter<Contacts, ContactsViewHolder> contactsRecyclerViewAdapter = new FirebaseRecyclerAdapter<Contacts, ContactsViewHolder>(

                Contacts.class,
                R.layout.user_single_layout,
                ContactsViewHolder.class,
                mQuery

        ) {
            @Override
            protected void populateViewHolder(final ContactsViewHolder contactsViewHolder, final Contacts contacts, int i) {

                //contactsViewHolder.setDate(contacts.getDate());

                final String list_user_id = getRef(i).getKey();

                mUsersDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        final String userName = dataSnapshot.child("name").getValue().toString();
                        String userStatus = dataSnapshot.child("status").getValue().toString();
                        String userRole = dataSnapshot.child("role").getValue().toString();
                        String userThumb = dataSnapshot.child("thumb_image").getValue().toString();
                        //String userOnline = dataSnapshot.child("online").getValue().toString();

                        if (dataSnapshot.hasChild("online")) {

                            String userOnline = dataSnapshot.child("online").getValue().toString();
                            contactsViewHolder.setUserOnline(userOnline);

                        }

                        contactsViewHolder.setName(userName);
                        contactsViewHolder.setUserImage(userThumb, getContext());
                        contactsViewHolder.setRole(userRole);
                        contactsViewHolder.setStatus(userStatus);


                        contactsViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                CharSequence options[] = new CharSequence[]{"Buka Profil", "Kirim pesan"};

                                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                builder.setTitle("Pilih :");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        //Click Event for each item.
                                        if (i == 0) { //profil

                                            Intent profileIntent = new Intent(getContext(), ProfileActivity.class);
                                            profileIntent.putExtra("from_user_id", list_user_id);
                                            startActivity(profileIntent);

                                        }

                                        if (i == 1) { //chat

                                            Intent chatIntent = new Intent(getContext(), ChatActivity.class);
                                            chatIntent.putExtra("from_user_id", list_user_id);
                                            chatIntent.putExtra("user_name", userName);
                                            startActivity(chatIntent);

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
        };

        mContactsList.setAdapter(contactsRecyclerViewAdapter);

    }


    public static class ContactsViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public ContactsViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setStatus(String status) {

            TextView userStatusView = (TextView) mView.findViewById(R.id.user_single_status);
            userStatusView.setText(status);

        }

        public void setName(String name) {

            TextView userNameView = (TextView) mView.findViewById(R.id.user_single_name);
            userNameView.setText(name);

        }

        public void setRole(String role) {
            TextView userRoleView = (TextView) mView.findViewById(R.id.user_single_role);
            userRoleView.setText(role);
        }

        public void setUserImage(String thumb_image, Context ctx) {

            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.user_single_image);
            Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.avatar_default).into(userImageView);

        }

        public void setUserOnline(String online_status) {

            ImageView userOnlineView = (ImageView) mView.findViewById(R.id.user_single_online_icon);

            if (online_status.equals("true")) {

                userOnlineView.setVisibility(View.VISIBLE);

            } else {

                userOnlineView.setVisibility(View.INVISIBLE);

            }

        }
    }
}


