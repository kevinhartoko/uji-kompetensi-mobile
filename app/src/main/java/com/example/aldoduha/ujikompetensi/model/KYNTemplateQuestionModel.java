package com.example.aldoduha.ujikompetensi.model;

/**
 * Created by aldoduha on 12/2/2017.
 */

public class KYNTemplateQuestionModel {
    private Long id;
    private int jumlahSoal;
    private int bobot;
    private KYNTemplateModel templateModel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getJumlahSoal() {
        return jumlahSoal;
    }

    public void setJumlahSoal(int jumlahSoal) {
        this.jumlahSoal = jumlahSoal;
    }

    public int getBobot() {
        return bobot;
    }

    public void setBobot(int bobot) {
        this.bobot = bobot;
    }

    public KYNTemplateModel getTemplateModel() {
        return templateModel;
    }

    public void setTemplateModel(KYNTemplateModel templateModel) {
        this.templateModel = templateModel;
    }
}
