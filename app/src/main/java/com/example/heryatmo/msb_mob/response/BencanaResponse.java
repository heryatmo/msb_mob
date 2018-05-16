
package com.example.heryatmo.msb_mob.response;

import java.util.List;

import com.example.heryatmo.msb_mob.model.Bencana;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BencanaResponse {

    @SerializedName("codeStatus")
    private Long mCodeStatus;
    @SerializedName("data")
    private List<Bencana> mData;
    @SerializedName("isSuccess")
    private Boolean mIsSuccess;
    @SerializedName("message")
    private String mMessage;


}
