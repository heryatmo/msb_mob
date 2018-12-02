package com.example.heryatmo.msb_mob.model;


import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetilAlurLog {

    @SerializedName("id_user")
    private String mIdUser;
    @SerializedName("id_logistik")
    private String mIdLogistik;
}
