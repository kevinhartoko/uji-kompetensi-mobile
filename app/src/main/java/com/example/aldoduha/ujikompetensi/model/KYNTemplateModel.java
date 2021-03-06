package com.example.aldoduha.ujikompetensi.model;

import com.example.aldoduha.ujikompetensi.utility.KYNJSONKey;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aldoduha on 11/12/2017.
 */

public class KYNTemplateModel  implements Serializable {
    @SerializedName(value = "localId")
    private Long id;
    @SerializedName(value = "id")
    private String serverId;
    private String nama;
    private int jumlahSoal;

    //untuk pengiriman data
    private List<KYNTemplateQuestionModel> templateQuestionModels;

    public KYNTemplateModel(){

    }

    public KYNTemplateModel(JSONObject object){
        try {
            if(object.has(KYNJSONKey.KEY_TEMPLATE_DESCRIPTION))
                setNama(object.getString(KYNJSONKey.KEY_TEMPLATE_DESCRIPTION));
            if(object.has(KYNJSONKey.KEY_SERVER_ID))
                setServerId(object.getString(KYNJSONKey.KEY_SERVER_ID));
            if(object.has(KYNJSONKey.KEY_TEMPLATE_TOTAL_QUESTION))
                setJumlahSoal(object.getInt(KYNJSONKey.KEY_TEMPLATE_TOTAL_QUESTION));
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

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public List<KYNTemplateQuestionModel> getTemplateQuestionModels() {
        return templateQuestionModels;
    }

    public void setTemplateQuestionModels(List<KYNTemplateQuestionModel> templateQuestionModels) {
        this.templateQuestionModels = templateQuestionModels;
    }
}
