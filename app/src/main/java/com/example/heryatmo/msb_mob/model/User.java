
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
public class User {

    @SerializedName("codeStatus")
    private Long mCodeStatus;
    @SerializedName("data")
    private Data mData;
    @SerializedName("isSuccess")
    private Boolean mIsSuccess;
    @SerializedName("message")
    private String mMessage;


}
