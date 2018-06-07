package com.example.heryatmo.msb_mob.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.model.CalonVolunteer;
import com.example.heryatmo.msb_mob.model.DaftarPeran;
import com.example.heryatmo.msb_mob.model.Post;
import com.example.heryatmo.msb_mob.model.User;

import java.util.List;

public class DaftarVolunteerAdapter extends RecyclerView.Adapter<DaftarVolunteerAdapter.ViewHolder>  {
    private List<CalonVolunteer> daftarVolunteer;

    public DaftarVolunteerAdapter(List<CalonVolunteer> daftarVolunteer){
        this.daftarVolunteer =  daftarVolunteer;
    }

    @Override
    public DaftarVolunteerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_volunteer, parent,false);
        return new DaftarVolunteerAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_nama_volunteer.setText(daftarVolunteer.get(position).getMNama());
        holder.tv_status_volunteer.setText(daftarVolunteer.get(position).getMStatusDaftar());
    }


    @Override
    public int getItemCount() {
        return daftarVolunteer.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        private TextView tv_nama_volunteer, tv_status_volunteer;
        public ViewHolder(View view){
            super(view);
            tv_nama_volunteer = (TextView) view.findViewById(R.id.txtViewNameUser);
            tv_status_volunteer = (TextView) view.findViewById(R.id.txtViewStatusDaftar);
        }
    }
}
