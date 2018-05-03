
package com.example.heryatmo.msb_mob.model;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Post {

        @SerializedName("kebutuhan")
    private String mKebutuhan;
    @SerializedName("nama")
    private String mNama;

    public String getKebutuhan() {
        return mKebutuhan;
    }

    public void setKebutuhan(String kebutuhan) {
        mKebutuhan = kebutuhan;
    }

    public String getNama() {
        return mNama;
    }

    public void setNama(String nama) {
        mNama = nama;
    }

}
