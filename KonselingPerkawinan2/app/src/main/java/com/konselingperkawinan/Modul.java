package com.konselingperkawinan;

/**
 * Created by Samuel JL on 29-Apr-18.
 */

public class Modul {

    public Modul()
    {}


    public Modul(String isi_modul, String judul_modul) {
        this.isi_modul = isi_modul;
        this.judul_modul = judul_modul;
    }

    public String getIsi_modul() {
        return isi_modul;
    }

    public void setIsi_modul(String isi_modul) {
        this.isi_modul = isi_modul;
    }

    public String getJudul_modul() {
        return judul_modul;
    }

    public void setJudul_modul(String judul_modul) {
        this.judul_modul = judul_modul;
    }

    String isi_modul, judul_modul;
}
