package com.example.heryatmo.msb_mob.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Upload {

    @SerializedName("id_upload")
    private String mId_upload;
    @SerializedName("nama_file")
    private String mNama_file;
    @SerializedName("path")
    private String mPath;
    @SerializedName("created_by")
    private String mCreated_by;
    @SerializedName("updated_by")
    private String mUpdated_by;
    @SerializedName("deleted_by")
    private String mDeleted_by;
}
