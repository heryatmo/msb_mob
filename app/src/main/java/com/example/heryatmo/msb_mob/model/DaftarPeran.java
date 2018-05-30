
package com.example.heryatmo.msb_mob.model;


import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DaftarPeran {

    //    @SerializedName("id_role")
//    private String mIdRole;
//    @SerializedName("id_shelter")
//    private String mIdShelter;
//    @SerializedName("id_user")
//    private String mIdUser;
//    @SerializedName("status_daftar")
//    private String mStatusDaftar;
    private String id_shelter;
    private String id_user;
    private String id_role;
    private String status_daftar;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
