
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
public class Role {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("deleted_at")
    private Object mDeletedAt;
    @SerializedName("id_role")
    private Long mIdRole;
    @SerializedName("nama_role")
    private String mNamaRole;
    @SerializedName("status_role")
    private String mStatusRole;
    @SerializedName("updated_at")
    private String mUpdatedAt;


}
