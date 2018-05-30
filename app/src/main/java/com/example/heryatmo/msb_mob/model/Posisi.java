
package com.example.heryatmo.msb_mob.model;


import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Posisi {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("deleted_at")
    private Object mDeletedAt;
    @SerializedName("id_bencana")
    private String mIdBencana;
    @SerializedName("id_posisi")
    private Long mIdShelter;
    @SerializedName("id_user")
    private String mIdUser;
    @SerializedName("nama_shelter")
    private String mNamaShelter;
    @SerializedName("status_shelter")
    private String mStatusShelter;
    @SerializedName("id_posisi")
    private Long mIdPosisi;
    @SerializedName("lat")
    private String mLat;
    @SerializedName("lng")
    private String mLng;
    @SerializedName("status_posisi")
    private String mStatusPosisi;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("wilayah_shelter")
    private String mWilayahShelter;
    @SerializedName("updated_at")
    private String mUpdatedAt;


}
