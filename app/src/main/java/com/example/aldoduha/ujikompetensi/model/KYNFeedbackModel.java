package com.example.aldoduha.ujikompetensi.model;

import com.example.aldoduha.ujikompetensi.utility.KYNJSONKey;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by aldoduha on 12/14/2017.
 */

public class KYNFeedbackModel implements Serializable {
    private Long id;
    private String serverId;
    private String description;
    private String name;
    private KYNIntervieweeModel intervieweeModel;

    public KYNFeedbackModel(){

    }

    public KYNFeedbackModel(JSONObject object){
        try {
            if(object.has(KYNJSONKey.KEY_SERVER_ID))
                setServerId(object.getString(KYNJSONKey.KEY_SERVER_ID));
            if(object.has(KYNJSONKey.KEY_FEEDBACK_FEEDBACK))
                setDescription(object.getString(KYNJSONKey.KEY_FEEDBACK_FEEDBACK));
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KYNIntervieweeModel getIntervieweeModel() {
        return intervieweeModel;
    }

    public void setIntervieweeModel(KYNIntervieweeModel intervieweeModel) {
        this.intervieweeModel = intervieweeModel;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getServerId() {
        return serverId;
    }
}
