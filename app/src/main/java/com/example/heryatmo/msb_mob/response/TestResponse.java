package com.example.heryatmo.msb_mob.response;

import com.example.heryatmo.msb_mob.model.Data;
import com.example.heryatmo.msb_mob.model.Post;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class TestResponse {

    @SerializedName("codeStatus")
    private Long mCodeStatus;
    @SerializedName("data")
    private List<Post> mData;
    @SerializedName("isSuccess")
    private Boolean mIsSuccess;
    @SerializedName("message")
    private String mMessage;

}
