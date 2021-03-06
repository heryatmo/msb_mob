package com.example.heryatmo.msb_mob.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.VolunteerMain.DetilPengungsi2Activity;
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
        holder.cardPengungsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Pengungsi dataPengungsi = daftarPengungsi.get(position);
                Intent intent = new Intent(context, DetilPengungsi2Activity.class);
                intent.putExtra("id_shelter",""+daftarPengungsi.get(position).getMIdShelter());
                intent.putExtra("id_pengungsi",""+daftarPengungsi.get(position).getMIdPengungsi());
                intent.putExtra("nama_pengungsi",""+daftarPengungsi.get(position).getMNamaPengungsi());
                intent.putExtra("tempat_lahir_pengungsi",""+daftarPengungsi.get(position).getMTempatLahirPengungsi());
                intent.putExtra("tanggal_lahir_pengungsi",""+daftarPengungsi.get(position).getMTanggalLahirPengungsi());
                intent.putExtra("jenis_kelamin_pengungsi",""+daftarPengungsi.get(position).getMJenisKelaminPengungsi());
                intent.putExtra("golongan_darah_pengungsi",""+daftarPengungsi.get(position).getMGolonganDarahPengungsi());
                intent.putExtra("alamat_pengungsi",""+daftarPengungsi.get(position).getMAlamatPengungsi());
                intent.putExtra("agama_pengungsi",""+daftarPengungsi.get(position).getMAgamaPengungsi());
                intent.putExtra("status_pengungsi",""+daftarPengungsi.get(position).getMStatusPengungsi());
                intent.putExtra("keterangan",""+daftarPengungsi.get(position).getMKeterangan());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return daftarPengungsi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_nama_pengungsi, tv_alamat_pengungsi;
        CardView cardPengungsi;

        public ViewHolder(View view) {
            super(view);
            tv_nama_pengungsi = (TextView) view.findViewById(R.id.tvViewNamePengungsi);
            tv_alamat_pengungsi = (TextView) view.findViewById(R.id.tvViewAlamatPengungsi);
            cardPengungsi = (CardView) view.findViewById(R.id.cardPengungsi);
        }
    }
}
