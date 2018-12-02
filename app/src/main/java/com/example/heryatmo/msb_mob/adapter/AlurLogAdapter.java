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
import com.example.heryatmo.msb_mob.VolunteerMain.DistribusiLogistikActivity;
import com.example.heryatmo.msb_mob.model.Logistik;

import java.util.List;

public class AlurLogAdapter extends RecyclerView.Adapter<AlurLogAdapter.ViewHolder> {
    private List<Logistik> daftarLogistik;
    private Context context;


    public AlurLogAdapter(List<Logistik> daftarLogistik, Context context) {
        this.daftarLogistik = daftarLogistik;
        this.context = context;

    }

    @Override
    public AlurLogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_alur_log, parent, false);
        return new AlurLogAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AlurLogAdapter.ViewHolder holder, final int position) {
        Log.i("daftarLogistik",daftarLogistik.get(position).toString());
        holder.tv_nama_logistik_alur.setText(daftarLogistik.get(position).getMNamaLogistik());
        holder.tv_jumlah_logistik_alur.setText(daftarLogistik.get(position).getMJumlahLogistik());
        holder.cardAlurLogistik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Logistik dataLogistik = daftarLogistik.get(position);
                Intent intent = new Intent(context, DetilAlurLogActivity.class);
                intent.putExtra("id_logistik",""+daftarLogistik.get(position).getMIdLogistik());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return daftarLogistik.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_nama_logistik_alur, tv_jumlah_logistik_alur;
        CardView cardAlurLogistik;

        public ViewHolder(View view) {
            super(view);
            tv_nama_logistik_alur = (TextView) view.findViewById(R.id.tvViewNameLogistikAlur);
            tv_jumlah_logistik_alur = (TextView) view.findViewById(R.id.tvViewJumlahLogistikAlur);
            cardAlurLogistik = (CardView) view.findViewById(R.id.cardAlurLogistik);
        }
    }
}
