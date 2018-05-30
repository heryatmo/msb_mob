package com.example.heryatmo.msb_mob.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistribusiDonasi {
    @SerializedName("id_donasi")
    private String mId_donasi;
    @SerializedName("id_user")
    private String mId_user;
    @SerializedName("id_shelter")
    private String mId_shelter;
    @SerializedName("jumlah_penarikan")
    private String mJumlah_penarikan;
    @SerializedName("keterangan")
    private String mKeterangan;
    @SerializedName("created_by")
    private String mCreated_by;
    @SerializedName("updated_by")
    private String mUpdated_by;
    @SerializedName("deleted_by")
    private String mDeleted_by;
}
