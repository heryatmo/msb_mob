package com.konselingperkawinan;

/**
 * Created by Samuel JL on 29-Apr-18.
 */

public class Faq {

    public Faq(){}

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }

    public Faq(String pertanyaan, String jawaban) {
        this.pertanyaan = pertanyaan;
        this.jawaban = jawaban;
    }

    String pertanyaan,jawaban;
}
