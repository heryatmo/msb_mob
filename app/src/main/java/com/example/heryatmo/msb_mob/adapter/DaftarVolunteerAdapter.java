package com.example.heryatmo.msb_mob.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heryatmo.msb_mob.R;
import com.example.heryatmo.msb_mob.ShelterManagerMain.DaftarVolunteerActivity;
import com.example.heryatmo.msb_mob.model.CalonVolunteer;
import com.example.heryatmo.msb_mob.model.DaftarPeran;
import com.example.heryatmo.msb_mob.model.Post;
import com.example.heryatmo.msb_mob.model.User;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.RetroClient;
import com.example.heryatmo.msb_mob.response.DaftarCalonVolunteerResponse;
import com.example.heryatmo.msb_mob.response.LogistikResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DaftarVolunteerAdapter extends RecyclerView.Adapter<DaftarVolunteerAdapter.ViewHolder> {
    private List<CalonVolunteer> daftarVolunteer;
    private Context context;
    private ProgressDialog progressDialog;

    public DaftarVolunteerAdapter(List<CalonVolunteer> daftarVolunteer, Context context) {
        this.daftarVolunteer = daftarVolunteer;
        this.context = context;

    }

    @Override
    public DaftarVolunteerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_volunteer, parent, false);
        return new DaftarVolunteerAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_nama_volunteer.setText(daftarVolunteer.get(position).getMNama());
        holder.tv_status_volunteer.setText(daftarVolunteer.get(position).getMStatusDaftar());
        holder.cardVol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CalonVolunteer calonVolunteer = daftarVolunteer.get(position);
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
                        .setPositiveButton("Terima", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(context, "Ok", Toast.LENGTH_SHORT).show();
                                showProgressDialog();
                                terimaCalonVolunteer(calonVolunteer);
                            }
                        })
                        .setNegativeButton("Tolak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(context, "Ditolak", Toast.LENGTH_SHORT).show();
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
        private TextView tv_nama_volunteer, tv_status_volunteer;
        CardView cardVol;

        public ViewHolder(View view) {
            super(view);
            tv_nama_volunteer = (TextView) view.findViewById(R.id.txtViewNameUser);
            tv_status_volunteer = (TextView) view.findViewById(R.id.txtViewStatusDaftar);
            cardVol = (CardView) view.findViewById(R.id.cardVolunteer);
        }
    }

    private void terimaCalonVolunteer(CalonVolunteer calonVolunteer) {

        Retrofit retrofit = RetroClient.getClient();
        Call<DaftarPeran> call = retrofit.create(APIService.class).terimaVolunteer(calonVolunteer.getMIdDaftar(), calonVolunteer.getMIdUser());
        call.enqueue(new Callback<DaftarPeran>() {
            @Override
            public void onResponse(Call<DaftarPeran> call, Response<DaftarPeran> response) {
                    progressDialog.dismiss();
                    Toast.makeText(context,"Berhasil",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<DaftarPeran> call, Throwable t) {

            }
        });
    }

    private void showProgressDialog() {
        this.progressDialog = ProgressDialog.show(context, "Mohon Tunggu", ".....", true);
        //you usually don't want the user to stop the current process, and this will make sure of that
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}
