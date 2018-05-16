
package com.example.heryatmo.msb_mob.model;


import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bencana {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("deleted_at")
    private Object mDeletedAt;
    @SerializedName("id_bencana")
    private Long mIdBencana;
    @SerializedName("id_posisi")
    private String mIdPosisi;
    @SerializedName("nama_bencana")
    private String mNamaBencana;
    @SerializedName("tanggal_kejadian")
    private String mTanggalKejadian;
    @SerializedName("updated_at")
    private String mUpdatedAt;

}
