package com.example.heryatmo.msb_mob.response;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class RegisterResponse {

    @SerializedName("codeStatus")
    private Long mCodeStatus;
    @SerializedName("data")
    private com.example.heryatmo.msb_mob.model.RegisterResponse mData;
    @SerializedName("isSuccess")
    private Boolean mIsSuccess;
    @SerializedName("message")
    private String mMessage;

}
