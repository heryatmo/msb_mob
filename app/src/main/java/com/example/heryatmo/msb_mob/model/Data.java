
package com.example.heryatmo.msb_mob.model;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Data {

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
    @SerializedName("id_role")
    private String mIdRole;
    @SerializedName("id_user")
    private Long mIdUser;
    @SerializedName("jenis_kelamin")
    private String mJenisKelamin;
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
    @SerializedName("username")
    private String mUsername;

    public String getAlamat() {
        return mAlamat;
    }

    public void setAlamat(String alamat) {
        mAlamat = alamat;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public Object getDeletedAt() {
        return mDeletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        mDeletedAt = deletedAt;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getGolonganDarah() {
        return mGolonganDarah;
    }

    public void setGolonganDarah(String golonganDarah) {
        mGolonganDarah = golonganDarah;
    }

    public String getIdRole() {
        return mIdRole;
    }

    public void setIdRole(String idRole) {
        mIdRole = idRole;
    }

    public Long getIdUser() {
        return mIdUser;
    }

    public void setIdUser(Long idUser) {
        mIdUser = idUser;
    }

    public String getJenisKelamin() {
        return mJenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        mJenisKelamin = jenisKelamin;
    }

    public String getNama() {
        return mNama;
    }

    public void setNama(String nama) {
        mNama = nama;
    }

    public String getNoHp() {
        return mNoHp;
    }

    public void setNoHp(String noHp) {
        mNoHp = noHp;
    }

    public Role getRole() {
        return mRole;
    }

    public void setRole(Role role) {
        mRole = role;
    }

    public String getStatusUser() {
        return mStatusUser;
    }

    public void setStatusUser(String statusUser) {
        mStatusUser = statusUser;
    }

    public String getTanggalLahir() {
        return mTanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        mTanggalLahir = tanggalLahir;
    }

    public String getTempatLahir() {
        return mTempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        mTempatLahir = tempatLahir;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

}
