package com.example.heryatmo.msb_mob.response;

import com.example.heryatmo.msb_mob.model.AlurLog;
import com.example.heryatmo.msb_mob.model.Logistik;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlurLogResponse {
    @SerializedName("codeStatus")
    private Long mCodeStatus;
    @SerializedName("data")
    private List<Logistik> mData;
    @SerializedName("isSuccess")
    private Boolean mIsSuccess;
    @SerializedName("message")
    private String mMessage;
}
