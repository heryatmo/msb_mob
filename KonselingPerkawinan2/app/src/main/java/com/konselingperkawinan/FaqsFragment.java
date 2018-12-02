package com.konselingperkawinan;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class FaqsFragment extends Fragment {


    private RecyclerView mFaqList;

    private DatabaseReference mFaqDatabase;

    public FaqsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faqs, container, false);

        //MainActivity.mViewPager.setCurrentItem(3);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        mFaqDatabase= FirebaseDatabase.getInstance().getReference().child("FAQ");
        mFaqDatabase.keepSynced(true);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());
        mFaqList = (RecyclerView) view.findViewById(R.id.faq_list_v2);
        mFaqList.setHasFixedSize(true);
        mFaqList.setLayoutManager(new LinearLayoutManager(getContext()));
        mFaqList.addItemDecoration(itemDecoration);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Faq, FaqsFragment.FaqViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<Faq, FaqsFragment.FaqViewHolder>(

                Faq.class,
                R.layout.faq_single_layout,
                FaqsFragment.FaqViewHolder.class,
                mFaqDatabase
        ) {

            @Override
            protected void populateViewHolder(FaqsFragment.FaqViewHolder viewHolder, Faq model, int position) {
                //viewHolder.setJawaban(model.getJawaban());
                viewHolder.setPertanyaan(model.getPertanyaan());

                final String faq_id = getRef(position).getKey();

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent profileIntent = new Intent(getContext(), ShowFaqActivity.class);
                        profileIntent.putExtra("faq_id",faq_id);
                        startActivity(profileIntent);
                    }
                });
            }
        };

        mFaqList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class FaqViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public FaqViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setPertanyaan(String pertanyaan){
            TextView mUsersName = (TextView) mView.findViewById(R.id.text_Pertanyaan);

            mUsersName.setText(pertanyaan);
        }

//        public void setJawaban(String jawaban) {
//            TextView mUserStatus = (TextView) mView.findViewById(R.id.text_Jawaban);
//            Spanned htmltext = Html.fromHtml(jawaban);
//            mUserStatus.setText(htmltext);
//        }


    }
}
