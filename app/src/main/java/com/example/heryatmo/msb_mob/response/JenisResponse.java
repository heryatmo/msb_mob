
package com.example.heryatmo.msb_mob.response;

import java.util.List;


import com.example.heryatmo.msb_mob.model.Jenis;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class JenisResponse {

    @SerializedName("codeStatus")
    private Long mCodeStatus;
    @SerializedName("isSuccess")
    private Boolean mIsSuccess;
    @SerializedName("data")
    private List<Jenis> mData;
    @SerializedName("message")
    private String mMessage;

}
