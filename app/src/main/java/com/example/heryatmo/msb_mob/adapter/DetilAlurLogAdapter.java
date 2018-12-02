package com.example.heryatmo.msb_mob.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.UserMain.DetilAlurLogActivity;
import com.example.heryatmo.msb_mob.model.DataDetilAlurLog;
import com.example.heryatmo.msb_mob.model.Logistik;

import java.util.List;

public class DetilAlurLogAdapter extends RecyclerView.Adapter<DetilAlurLogAdapter.ViewHolder>  {
    private List<DataDetilAlurLog> daftarLogistik;
    private Context context;


    public DetilAlurLogAdapter(List<DataDetilAlurLog> daftarLogistik, Context context) {
        this.daftarLogistik = daftarLogistik;
        this.context = context;

    }

    @Override
    public DetilAlurLogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detil_alur_log, parent, false);
        return new DetilAlurLogAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final DetilAlurLogAdapter.ViewHolder holder, final int position) {
        Log.i("daftarLogistik",daftarLogistik.get(position).toString());
        holder.tv_nama_volunteer_alur.setText(daftarLogistik.get(position).getMNama());
        holder.tv_nama_shelter.setText(daftarLogistik.get(position).getMNamaShelter());
        holder.tv_jumlah_logistik_alur.setText(daftarLogistik.get(position).getMJumlahLogistik() + "");
        holder.tv_tanggal.setText(daftarLogistik.get(position).getMUpdatedAt());
//        holder.cardDetilAlurLogistik.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final DataDetilAlurLog dataLogistik = daftarLogistik.get(position);
//                Intent intent = new Intent(context, DetilAlurLogActivity.class);
//                intent.putExtra("id_logistik",""+daftarLogistik.get(position).getMIdLogistik());
//                context.startActivity(intent);
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return daftarLogistik.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_nama_volunteer_alur, tv_jumlah_logistik_alur, tv_nama_shelter, tv_tanggal;
        CardView cardDetilAlurLogistik;

        public ViewHolder(View view) {
            super(view);
            tv_nama_volunteer_alur = (TextView) view.findViewById(R.id.tvNamaVolunteerDetilAlur);
            tv_nama_shelter = (TextView) view.findViewById(R.id.tvNamaShelterDetilAlur);
            tv_jumlah_logistik_alur = (TextView) view.findViewById(R.id.tvJumlahDetilAlur);
            tv_tanggal = (TextView) view.findViewById(R.id.tvTanggalDetilAlur);
            cardDetilAlurLogistik = (CardView) view.findViewById(R.id.cardDetilAlurLogistik);
        }
    }
}
