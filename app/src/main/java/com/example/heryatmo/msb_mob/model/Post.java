
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
public class Post {

    @SerializedName("kebutuhan")
    private String mKebutuhan;
    @SerializedName("nama")
    private String mNama;
}
