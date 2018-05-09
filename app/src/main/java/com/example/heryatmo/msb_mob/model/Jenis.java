
package com.example.heryatmo.msb_mob.model;


import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@SuppressWarnings("unused")
public class Jenis {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("deleted_at")
    private Object mDeletedAt;
    @SerializedName("id_jenis")
    private Long mIdJenis;
    @SerializedName("nama_jenis")
    private String mNamaJenis;
    @SerializedName("status_jenis")
    private String mStatusJenis;
    @SerializedName("updated_at")
    private String mUpdatedAt;

}
