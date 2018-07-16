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

import java.util.List;

public class DaftarVolunteerKeterimaAdapter extends RecyclerView.Adapter<DaftarVolunteerKeterimaAdapter.ViewHolder> {
    private List<CalonVolunteer> daftarVolunteer;
    private Context context;
    private ProgressDialog progressDialog;

    String id_daftar;
    public DaftarVolunteerKeterimaAdapter(List<CalonVolunteer> daftarVolunteer, Context context) {
        this.daftarVolunteer = daftarVolunteer;
        this.context = context;

    }

    @Override
    public DaftarVolunteerKeterimaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_volunteer_diterima, parent, false);
        return new DaftarVolunteerKeterimaAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final DaftarVolunteerKeterimaAdapter.ViewHolder holder, final int position) {
        holder.tv_nama_volunteer1.setText(daftarVolunteer.get(position).getMNama());
        holder.tv_status_volunteer1.setText(daftarVolunteer.get(position).getMStatusDaftar());
        holder.cardVol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CalonVolunteer calonVolunteer = daftarVolunteer.get(position);
                id_daftar = calonVolunteer.getMIdDaftar();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(
                        "Nama : " + calonVolunteer.getMNama() + "\n" +
                                "Tempat/Tanggal Lahir : " + calonVolunteer.getMTempatLahir() + "/" + calonVolunteer.getMTanggalLahir() + "\n" +
                                "Jenis Kelamin : " + calonVolunteer.getMJenisKelamin() + "\n" +
                                "Alamat : " + calonVolunteer.getMAlamat() + "\n" +
                                "No HP : " + calonVolunteer.getMNoHp() + "\n" +
                                "Email : " + calonVolunteer.getMEmail() + "\n" +
                                "")
                        .setTitle("Terima Calon Volunteer ?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(context, "Ok", Toast.LENGTH_SHORT).show();
                            }
                        });

                builder.create().show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return daftarVolunteer.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_nama_volunteer1, tv_status_volunteer1;
        CardView cardVol;

        public ViewHolder(View view) {
            super(view);
            tv_nama_volunteer1 = (TextView) view.findViewById(R.id.txtViewNameUserKeterima);
            tv_status_volunteer1 = (TextView) view.findViewById(R.id.txtViewStatusDaftarKeterima);
            cardVol = (CardView) view.findViewById(R.id.cardVolunteerKeterima);
        }
    }
}
