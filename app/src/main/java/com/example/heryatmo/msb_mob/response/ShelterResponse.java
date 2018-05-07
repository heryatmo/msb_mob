package com.example.heryatmo.msb_mob.response;

import com.example.heryatmo.msb_mob.model.SemuaShelter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ShelterResponse {


    @SerializedName("codeStatus")
    private Long mCodeStatus;
    @SerializedName("data")
    private List<SemuaShelter> mData;
    @SerializedName("isSuccess")
    private Boolean mIsSuccess;
    @SerializedName("message")
    private String mMessage;



}
