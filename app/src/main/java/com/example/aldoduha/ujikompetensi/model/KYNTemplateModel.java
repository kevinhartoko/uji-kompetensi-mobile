package com.example.aldoduha.ujikompetensi.model;

import java.io.Serializable;

/**
 * Created by aldoduha on 11/12/2017.
 */

public class KYNTemplateModel  implements Serializable {
    private Long id;
    private Long serverId;
    private String nama;
    private int jumlahSoal;

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

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }
}
