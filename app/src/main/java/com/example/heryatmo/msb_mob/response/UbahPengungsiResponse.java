package com.example.heryatmo.msb_mob.response;


import com.example.heryatmo.msb_mob.model.Pengungsi;
import com.example.heryatmo.msb_mob.model.UbahPengungsi;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UbahPengungsiResponse {

    @SerializedName("codeStatus")
    private Long mCodeStatus;
    @SerializedName("data")
    private UbahPengungsi mData;
    @SerializedName("isSuccess")
    private Boolean mIsSuccess;
    @SerializedName("message")
    private String mMessage;

}
