package com.example.heryatmo.msb_mob.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.model.SemuaShelter;

import java.util.List;

public class ShelterAdapter extends  RecyclerView.Adapter<ShelterAdapter.ViewHolder> {
    private List<SemuaShelter> shelter;

    public ShelterAdapter(List<SemuaShelter> post){
        this.shelter = post;
    }


    public ShelterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_shelter, parent,false);
        return new ViewHolder(v);
    }


    public void onBindViewHolder(ShelterAdapter.ViewHolder holder, int position) {
        holder.tv_namashelter.setText(shelter.get(position).getMNamaShelter());
        holder.tv_alamatshelter.setText(shelter.get(position).getMWilayahShelter());
    }


    public int getItemCount() {
        return shelter.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        private TextView tv_namashelter, tv_alamatshelter;
        public ViewHolder(View view){
            super(view);
            tv_namashelter = (TextView) view.findViewById(R.id.txtViewNameShelter);
            tv_alamatshelter = (TextView) view.findViewById(R.id.txtAlamatShelter);
        }
    }
}
