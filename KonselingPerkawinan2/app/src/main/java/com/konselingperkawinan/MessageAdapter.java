package com.konselingperkawinan;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Samuel JL on 04-May-18.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{


    private List<Messages> mMessageList;
    private DatabaseReference mUserDatabase;
    public FirebaseUser mCurrentUser;
    public String current_uid, name, image;

    public MessageAdapter(List<Messages> mMessageList) {

        this.mMessageList = mMessageList;

    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_single_layout ,parent, false);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        current_uid = mCurrentUser.getUid();

        return new MessageViewHolder(v);

    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView messageText,timeText, send_messageText,send_timeText;
        public CircleImageView profileImage;
        public TextView displayName;
        public ImageView messageImage,send_messageImage;


        public MessageViewHolder(View view) {
            super(view);

            messageText = (TextView) view.findViewById(R.id.message_text_layout);
            profileImage = (CircleImageView) view.findViewById(R.id.message_profile_layout);
            displayName = (TextView) view.findViewById(R.id.name_text_layout);
            messageImage = (ImageView) view.findViewById(R.id.message_image_layout);
            timeText = (TextView) view.findViewById(R.id.time_text_layout);

            send_messageText = (TextView) view.findViewById(R.id.send_message_text_layout);
            send_timeText = (TextView) view.findViewById(R.id.send_time_text_layout);
            send_messageImage = (ImageView) view.findViewById(R.id.send_message_image_layout);
        }
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder viewHolder, int i) {

        Messages c = mMessageList.get(i);

        Calendar cal;
        String from_user = c.getFrom();
        String message_type = c.getType();
        Long time = c.getTime();

        cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);

        String date = DateFormat.format("dd MMM yyyy\nHH:mm", cal).toString();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(from_user);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                name = dataSnapshot.child("name").getValue().toString();
                image = dataSnapshot.child("thumb_image").getValue().toString();

                viewHolder.displayName.setText(name);

                Picasso.with(viewHolder.profileImage.getContext()).load(image)
                        .placeholder(R.drawable.avatar_default).into(viewHolder.profileImage);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(TextUtils.equals(from_user,current_uid)) {
            //KIRIM
            if(message_type.equals("text")) {

                //show layout
                viewHolder.send_messageText.setVisibility(View.VISIBLE);
                //viewHolder.send_messageImage.setVisibility(View.VISIBLE);
                viewHolder.send_timeText.setVisibility(View.VISIBLE);

                viewHolder.send_messageText.setText(c.getMessage());
                viewHolder.send_timeText.setText(date);

                viewHolder.send_messageImage.setVisibility(View.GONE);

                //invis another layout
                viewHolder.messageText.setVisibility(View.GONE);
                viewHolder.timeText.setVisibility(View.GONE);
                viewHolder.messageImage.setVisibility(View.GONE);
                viewHolder.profileImage.setVisibility(View.GONE);

            } else {
                //show layout
                viewHolder.send_timeText.setVisibility(View.VISIBLE);
                viewHolder.send_messageImage.setVisibility(View.VISIBLE);

                viewHolder.send_timeText.setText(date);
                Picasso.with(viewHolder.profileImage.getContext()).load(c.getMessage())
                        .placeholder(R.drawable.image_default)
                        .into(viewHolder.send_messageImage);

                //viewHolder.send_messageText.setVisibility(View.GONE);
                viewHolder.send_messageText.setVisibility(View.GONE);

                //invis another layout
                viewHolder.messageText.setVisibility(View.GONE);
                viewHolder.timeText.setVisibility(View.GONE);
                viewHolder.messageImage.setVisibility(View.GONE);
                viewHolder.profileImage.setVisibility(View.GONE);
            }
        }
        else {
            //TERIMA
            if(message_type.equals("text")) {
                //show layout
                viewHolder.messageText.setVisibility(View.VISIBLE);
                viewHolder.timeText.setVisibility(View.VISIBLE);
                viewHolder.profileImage.setVisibility(View.VISIBLE);

                viewHolder.messageText.setText(c.getMessage());
                viewHolder.messageImage.setVisibility(View.GONE);
                viewHolder.timeText.setText(date);
                viewHolder.displayName.setText(name);

                //invis another layout
                viewHolder.send_messageText.setVisibility(View.GONE);
                viewHolder.send_timeText.setVisibility(View.GONE);
                viewHolder.send_messageImage.setVisibility(View.GONE);

            } else {
                //show layout
                viewHolder.messageText.setVisibility(View.VISIBLE);
                viewHolder.timeText.setVisibility(View.VISIBLE);
                viewHolder.profileImage.setVisibility(View.VISIBLE);

                viewHolder.timeText.setText(date);
                viewHolder.messageText.setVisibility(View.GONE);
                Picasso.with(viewHolder.profileImage.getContext()).load(c.getMessage())
                        .placeholder(R.drawable.image_default)
                        .into(viewHolder.messageImage);
                viewHolder.messageImage.setVisibility(View.VISIBLE);

                //invis another layout
                viewHolder.send_messageText.setVisibility(View.GONE);
                viewHolder.send_timeText.setVisibility(View.GONE);
                viewHolder.send_messageImage.setVisibility(View.GONE);
            }
        }



    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);


    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

}
