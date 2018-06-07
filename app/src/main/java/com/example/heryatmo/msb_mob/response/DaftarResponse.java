package com.example.heryatmo.msb_mob.response;

import com.example.heryatmo.msb_mob.model.DaftarPeran;
import com.example.heryatmo.msb_mob.model.Post;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DaftarResponse {
    //    @SerializedName("codeStatus")
//    private Long mCodeStatus;
//    @SerializedName("data")
//    private DaftarPeran mData;
//    @SerializedName("isSuccess")
//    private Boolean mIsSuccess;
//    @SerializedName("message")
//    private String mMessage;
    private Boolean isSuccess;
    private Integer codeStatus;
    private String message;
    private DaftarPeran data;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

}
