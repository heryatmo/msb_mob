package com.konselingperkawinan;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class ModulsFragment extends Fragment {


    private RecyclerView mModulList;

    private DatabaseReference mModulDatabase;


    public ModulsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_moduls, container, false);

        //MainActivity.mViewPager.setCurrentItem(2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        mModulDatabase= FirebaseDatabase.getInstance().getReference().child("Modul");
        mModulDatabase.keepSynced(true);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());
        mModulList = (RecyclerView) view.findViewById(R.id.modul_list_v2);
        mModulList.setHasFixedSize(true);
        mModulList.setLayoutManager(new LinearLayoutManager(getContext()));
        mModulList.addItemDecoration(itemDecoration);



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Modul, ModulViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<Modul, ModulViewHolder>(

                Modul.class,
                R.layout.modul_single_layout,
                ModulsFragment.ModulViewHolder.class,
                mModulDatabase
        ) {

            @Override
            protected void populateViewHolder(ModulViewHolder viewHolder, Modul model, int position) {
                viewHolder.setIsiModul(model.getIsi_modul());
                viewHolder.setJudulModul(model.getJudul_modul());

                final String modul_id = getRef(position).getKey();

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent profileIntent = new Intent(getContext(), ShowModulActivity.class);
                        profileIntent.putExtra("modul_id",modul_id);
                        startActivity(profileIntent);
                    }
                });
            }
        };

        mModulList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ModulViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public ModulViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setIsiModul(String isiModul){
//            TextView mUsersName = (TextView) mView.findViewById(R.id.text_Isi_Modul);
//            Spanned htmltext = Html.fromHtml(isiModul);
//            mUsersName.setText(htmltext);
        }

        public void setJudulModul(String judulModul) {
            TextView mUserStatus = (TextView) mView.findViewById(R.id.text_Judul_Modul);
            mUserStatus.setText(judulModul);
        }


    }

}
