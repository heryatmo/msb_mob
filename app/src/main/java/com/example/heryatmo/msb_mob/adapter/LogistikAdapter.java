package com.example.heryatmo.msb_mob.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.model.Logistik;

import java.util.List;

public class LogistikAdapter extends RecyclerView.Adapter<LogistikAdapter.ViewHolder>{
    private List<Logistik> daftarLogistik;
    private Context context;


    public LogistikAdapter(List<Logistik> daftarLogistik, Context context) {
        this.daftarLogistik = daftarLogistik;
        this.context = context;

    }

    @Override
    public LogistikAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_logistik, parent, false);
        return new LogistikAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final LogistikAdapter.ViewHolder holder, final int position) {
        holder.tv_nama_logistik.setText(daftarLogistik.get(position).getMNamaLogistik());
        holder.tv_jumlah_logistik.setText(daftarLogistik.get(position).getMJumlahLogistik());
        holder.cardLogistik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                final Logistik dataPengungsi = daftarPengungsi.get(position);
//                Intent intent = new Intent(context, DetilPengungsi2Activity.class);
//                intent.putExtra("Nama : ",daftarPengungsi.get(position).getMNamaPengungsi());
//                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return daftarLogistik.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_nama_logistik, tv_jumlah_logistik;
        CardView cardLogistik;

        public ViewHolder(View view) {
            super(view);
            tv_nama_logistik = (TextView) view.findViewById(R.id.tvViewNameLogistik);
            tv_jumlah_logistik = (TextView) view.findViewById(R.id.tvViewJumlahLogistik);
            cardLogistik = (CardView) view.findViewById(R.id.cardLogistik);
        }
    }
}
