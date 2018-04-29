
package com.example.heryatmo.msb_mob.model;


import com.google.gson.annotations.SerializedName;


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

    public Long getCodeStatus() {
        return mCodeStatus;
    }

    public void setCodeStatus(Long codeStatus) {
        mCodeStatus = codeStatus;
    }

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

    public Boolean getIsSuccess() {
        return mIsSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        mIsSuccess = isSuccess;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

}
