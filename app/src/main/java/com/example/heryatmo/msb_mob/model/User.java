
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

    @SerializedName("alamat")
    private String mAlamat;
    @SerializedName("codeStatus")
    private Long mCodeStatus;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("deleted_at")
    private Object mDeletedAt;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("golongan_darah")
    private String mGolonganDarah;
    @SerializedName("id_role")
    private String mIdRole;
    @SerializedName("id_user")
    private Long mIdUser;
    @SerializedName("isSuccess")
    private Boolean mIsSuccess;
    @SerializedName("jenis_kelamin")
    private String mJenisKelamin;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("nama")
    private String mNama;
    @SerializedName("no_hp")
    private String mNoHp;
    @SerializedName("role")
    private Role mRole;
    @SerializedName("status_user")
    private String mStatusUser;
    @SerializedName("tanggal_lahir")
    private String mTanggalLahir;
    @SerializedName("tempat_lahir")
    private String mTempatLahir;
    @SerializedName("updated_at")
    private String mUpdatedAt;
    @SerializedName("user")
    private User mUser;


}
