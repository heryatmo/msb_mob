package com.example.heryatmo.msb_mob.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LupaPassword {
    @SerializedName("email")
    private String mEmail;
}