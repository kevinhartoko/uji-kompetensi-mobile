package com.example.aldoduha.ujikompetensi.model;

import java.io.Serializable;

/**
 * Created by aldoduha on 12/14/2017.
 */

public class KYNFeedbackModel implements Serializable {
    Long id;
    String description;
    String name;
    KYNIntervieweeModel intervieweeModel;

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
}
