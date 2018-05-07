
package com.example.heryatmo.msb_mob.model;


import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DaftarPeran {

    @SerializedName("id_role")
    private String mIdRole;
    @SerializedName("id_shelter")
    private String mIdShelter;
    @SerializedName("id_user")
    private String mIdUser;
    @SerializedName("status_daftar")
    private String mStatusDaftar;

}
