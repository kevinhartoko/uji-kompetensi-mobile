package com.example.aldoduha.ujikompetensi.model;

import java.io.Serializable;

/**
 * Created by aldoduha on 10/22/2017.
 */

public class KYNQuestionModel  implements Serializable {
    private Long id;
    private Long serverId;
    private String name;
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String intervieweeAnswer;
    private String keyAnswer;
    private int bobot;
    private KYNIntervieweeModel intervieweeModel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getIntervieweeAnswer() {
        return intervieweeAnswer;
    }

    public void setIntervieweeAnswer(String intervieweeAnswer) {
        this.intervieweeAnswer = intervieweeAnswer;
    }

    public String getKeyAnswer() {
        return keyAnswer;
    }

    public void setKeyAnswer(String keyAnswer) {
        this.keyAnswer = keyAnswer;
    }

    public KYNIntervieweeModel getIntervieweeModel() {
        return intervieweeModel;
    }

    public void setIntervieweeModel(KYNIntervieweeModel intervieweeModel) {
        this.intervieweeModel = intervieweeModel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBobot() {
        return bobot;
    }

    public void setBobot(int bobot) {
        this.bobot = bobot;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }
}
