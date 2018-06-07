
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
public class CalonVolunteer {

    @SerializedName("alamat")
    private String mAlamat;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("deleted_at")
    private Object mDeletedAt;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("golongan_darah")
    private String mGolonganDarah;
    @SerializedName("id_daftar")
    private String mIdDaftar;
    @SerializedName("id_role")
    private String mIdRole;
    @SerializedName("id_shelter")
    private String mIdShelter;
    @SerializedName("id_user")
    private String mIdUser;
    @SerializedName("jenis_kelamin")
    private String mJenisKelamin;
    @SerializedName("nama")
    private String mNama;
    @SerializedName("no_hp")
    private String mNoHp;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("remember_token")
    private Object mRememberToken;
    @SerializedName("status_daftar")
    private String mStatusDaftar;
    @SerializedName("status_user")
    private String mStatusUser;
    @SerializedName("tanggal_lahir")
    private String mTanggalLahir;
    @SerializedName("tempat_lahir")
    private String mTempatLahir;
    @SerializedName("updated_at")
    private String mUpdatedAt;

}
