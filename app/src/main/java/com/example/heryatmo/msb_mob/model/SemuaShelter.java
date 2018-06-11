
package com.example.heryatmo.msb_mob.model;

import java.util.List;


import com.example.heryatmo.msb_mob.response.ShelterResponse;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class SemuaShelter {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("deleted_at")
    private Object mDeletedAt;
    @SerializedName("id_bencana")
    private String mIdBencana;
    @SerializedName("id_shelter")
    private Long mIdShelter;
    @SerializedName("id_user")
    private String mIdUser;
    @SerializedName("nama_shelter")
    private String mNamaShelter;
    @SerializedName("status_shelter")
    private String mStatusShelter;
    @SerializedName("lat")
    private Double mLat;
    @SerializedName("lng")
    private Double mLng;
    @SerializedName("status_posisi")
    private String mStatusPosisi;
    @SerializedName("wilayah_shelter")
    private String mWilayahShelter;
    @SerializedName("updated_at")
    private String mUpdatedAt;


}
