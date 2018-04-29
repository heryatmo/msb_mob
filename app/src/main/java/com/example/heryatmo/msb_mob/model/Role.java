
package com.example.heryatmo.msb_mob.model;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Role {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("deleted_at")
    private Object mDeletedAt;
    @SerializedName("id_role")
    private Long mIdRole;
    @SerializedName("nama_role")
    private String mNamaRole;
    @SerializedName("status_role")
    private String mStatusRole;
    @SerializedName("updated_at")
    private String mUpdatedAt;

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

    public Long getIdRole() {
        return mIdRole;
    }

    public void setIdRole(Long idRole) {
        mIdRole = idRole;
    }

    public String getNamaRole() {
        return mNamaRole;
    }

    public void setNamaRole(String namaRole) {
        mNamaRole = namaRole;
    }

    public String getStatusRole() {
        return mStatusRole;
    }

    public void setStatusRole(String statusRole) {
        mStatusRole = statusRole;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

}
