package com.example.heryatmo.msb_mob.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Logistik {
    @SerializedName("id_user")
    private String mIdUser;
    @SerializedName("jumlah_logistik")
    private String mJumlahLogistik;
    @SerializedName("keterangan")
    private String mKeterangan;
    @SerializedName("nama_logistik")
    private String mNamaLogistik;
    @SerializedName("status_logistik")
    private String mStatusLogistik;

}
