package com.example.heryatmo.msb_mob.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.model.CalonVolunteer;
import com.example.heryatmo.msb_mob.model.Pengungsi;

import java.util.List;

public class PengungsiAdapter extends RecyclerView.Adapter<PengungsiAdapter.ViewHolder> {
    private List<Pengungsi> daftarPengungsi;
    private Context context;
    private ProgressDialog progressDialog;


    public PengungsiAdapter(List<Pengungsi> daftarPengungsi, Context context) {
        this.daftarPengungsi = daftarPengungsi;
        this.context = context;

    }

    @Override
    public PengungsiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pengungsi, parent, false);
        return new PengungsiAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PengungsiAdapter.ViewHolder holder, final int position) {
      holder.tv_nama_pengungsi.setText(daftarPengungsi.get(position).getMNamaPengungsi());
        holder.tv_alamat_pengungsi.setText(daftarPengungsi.get(position).getMAlamatPengungsi());
        holder.cardVol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return daftarPengungsi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_nama_pengungsi, tv_alamat_pengungsi;
        CardView cardVol;

        public ViewHolder(View view) {
            super(view);
            tv_nama_pengungsi = (TextView) view.findViewById(R.id.tvViewNamePengungsi);
            tv_alamat_pengungsi = (TextView) view.findViewById(R.id.tvViewAlamatPengungsi);
            cardVol = (CardView) view.findViewById(R.id.cardVolunteer);
        }
    }
}
