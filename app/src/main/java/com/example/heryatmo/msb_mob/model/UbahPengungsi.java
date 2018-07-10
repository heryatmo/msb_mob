package com.example.heryatmo.msb_mob.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UbahPengungsi {
    @SerializedName("agama_pengungsi")
    private String mAgamaPengungsi;
    @SerializedName("id_pengungsi")
    private String mIdPengungsi;
    @SerializedName("alamat_pengungsi")
    private String mAlamatPengungsi;
    @SerializedName("golongan_darah_pengungsi")
    private String mGolonganDarahPengungsi;
    @SerializedName("jenis_kelamin_pengungsi")
    private String mJenisKelaminPengungsi;
    @SerializedName("keterangan")
    private String mKeterangan;
    @SerializedName("nama_pengungsi")
    private String mNamaPengungsi;
    @SerializedName("status_pengungsi")
    private String mStatusPengungsi;
    @SerializedName("tanggal_lahir_pengungsi")
    private String mTanggalLahirPengungsi;
    @SerializedName("tempat_lahir_pengungsi")
    private String mTempatLahirPengungsi;

}
