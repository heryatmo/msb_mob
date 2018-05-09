
package com.example.heryatmo.msb_mob.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Donasi {

    @SerializedName("bukti_transfer")
    private String mBuktiTransfer;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("id_donasi")
    private Long mIdDonasi;
    @SerializedName("id_jenis")
    private String mIdJenis;
    @SerializedName("id_user")
    private String mIdUser;
    @SerializedName("jumlah_donasi")
    private String mJumlahDonasi;
    @SerializedName("keterangan")
    private String mKeterangan;
    @SerializedName("status_donasi")
    private String mStatusDonasi;
    @SerializedName("updated_at")
    private String mUpdatedAt;

}
