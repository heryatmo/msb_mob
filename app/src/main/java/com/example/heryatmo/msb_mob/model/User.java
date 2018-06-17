
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
public class User {
    @SerializedName("nama")
    private String mNama;
    @SerializedName("role")
    private Role mRole;

    @SerializedName("jenis_kelamin")
    private String mJenisKelamin;
    @SerializedName("golongan_darah")
    private String mGolonganDarah;

    @SerializedName("tempat_lahir")
    private String mTempatLahir;
    @SerializedName("tanggal_lahir")
    private String mTanggalLahir;


    @SerializedName("alamat")
    private String mAlamat;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("no_hp")
    private String mNoHp;

    @SerializedName("status_user")
    private String mStatusUser;


    @SerializedName("id_user")
    private Long mIdUser;
    @SerializedName("id_role")
    private String mIdRole;
    @SerializedName("user")
    private User mUser;

    @SerializedName("isSuccess")
    private Boolean mIsSuccess;
    @SerializedName("codeStatus")
    private Long mCodeStatus;
    @SerializedName("message")
    private String mMessage;

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("updated_at")
    private String mUpdatedAt;
    @SerializedName("deleted_at")
    private Object mDeletedAt;


}
