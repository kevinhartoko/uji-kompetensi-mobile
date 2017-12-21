package com.example.aldoduha.ujikompetensi.model;

import java.io.Serializable;

/**
 * Created by aldoduha on 11/12/2017.
 */

public class KYNTemplateModel  implements Serializable {
    Long id;
    String nama;
    int jumlahSoal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getJumlahSoal() {
        return jumlahSoal;
    }

    public void setJumlahSoal(int jumlahSoal) {
        this.jumlahSoal = jumlahSoal;
    }
}
