package com.example.aldoduha.ujikompetensi.model;

import com.example.aldoduha.ujikompetensi.utility.KYNJSONKey;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by aldoduha on 12/2/2017.
 */

public class KYNTemplateQuestionModel  implements Serializable {
    @SerializedName(value = "localId")
    private Long id;
    @SerializedName(value = "id")
    private String serverId;
    private int jumlahSoal;
    private int bobot;
    private KYNTemplateModel templateModel;

    public KYNTemplateQuestionModel(){

    }

    public KYNTemplateQuestionModel(JSONObject object){
        try {
            if (object.has(KYNJSONKey.KEY_TEMPLATE_QUESITON_BANYAK_SOAL))
                setJumlahSoal(object.getInt(KYNJSONKey.KEY_TEMPLATE_QUESITON_BANYAK_SOAL));
            if(object.has(KYNJSONKey.KEY_SERVER_ID))
                setServerId(object.getString(KYNJSONKey.KEY_SERVER_ID));
            if(object.has(KYNJSONKey.KEY_TEMPLATE_QUESTION_BOBOT))
                setBobot(object.getInt(KYNJSONKey.KEY_TEMPLATE_QUESTION_BOBOT));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

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

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }
}
