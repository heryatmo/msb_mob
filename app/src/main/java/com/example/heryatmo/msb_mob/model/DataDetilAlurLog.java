package com.example.heryatmo.msb_mob.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class DataDetilAlurLog {
    @SerializedName("nama")
    private String mNama;
    @SerializedName("nama_shelter")
    private String mNamaShelter;
    @SerializedName("jumlah_logistik")
    private Long mJumlahLogistik;
    @SerializedName("updated_at")
    private String mUpdatedAt;
}
