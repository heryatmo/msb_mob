
package com.example.heryatmo.msb_mob.response;



import com.example.heryatmo.msb_mob.model.Donasi;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DonasiResponse {

    @SerializedName("codeStatus")
    private Long mCodeStatus;
    @SerializedName("donasi")
    private Donasi mDonasi;
    @SerializedName("isSuccess")
    private Boolean mIsSuccess;
    @SerializedName("message")
    private String mMessage;

}
