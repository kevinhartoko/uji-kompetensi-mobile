package com.example.aldoduha.ujikompetensi.model;

import com.example.aldoduha.ujikompetensi.utility.KYNJSONKey;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by aldoduha on 10/22/2017.
 */

public class KYNQuestionModel  implements Serializable {
    @SerializedName(value = "localId")
    private Long id;
    @SerializedName(value = "id")
    private String serverId;
    @SerializedName(value = "code")
    private String name;
    @SerializedName(value = "description")
    private String question;
    @SerializedName(value = "answer_a")
    private String answer1;
    @SerializedName(value = "answer_b")
    private String answer2;
    @SerializedName(value = "answer_c")
    private String answer3;
    @SerializedName(value = "answer_d")
    private String answer4;
    private String intervieweeAnswer;
    @SerializedName(value = "trueAnswer")
    private String keyAnswer;
    private int bobot;
    private String category;
    private KYNIntervieweeModel intervieweeModel;

    public KYNQuestionModel(){

    }

    public KYNQuestionModel(JSONObject object){
        try {
            if(object.has(KYNJSONKey.KEY_SERVER_ID))
                setServerId(object.getString(KYNJSONKey.KEY_SERVER_ID));
            if(object.has(KYNJSONKey.KEY_QUESTION_CODE))
                setName(object.getString(KYNJSONKey.KEY_QUESTION_CODE));
            if(object.has(KYNJSONKey.KEY_QUESTION_DESCRIPTION))
                setQuestion(object.getString(KYNJSONKey.KEY_QUESTION_DESCRIPTION));
            if(object.has(KYNJSONKey.KEY_QUESTION_ANSWER_A))
                setAnswer1(object.getString(KYNJSONKey.KEY_QUESTION_ANSWER_A));
            if(object.has(KYNJSONKey.KEY_QUESTION_ANSWER_B))
                setAnswer2(object.getString(KYNJSONKey.KEY_QUESTION_ANSWER_B));
            if(object.has(KYNJSONKey.KEY_QUESTION_ANSWER_C))
                setAnswer3(object.getString(KYNJSONKey.KEY_QUESTION_ANSWER_C));
            if(object.has(KYNJSONKey.KEY_QUESTION_ANSWER_D))
                setAnswer4(object.getString(KYNJSONKey.KEY_QUESTION_ANSWER_D));
            if(object.has(KYNJSONKey.KEY_QUESTION_TRUE_ANSWER))
                setKeyAnswer(object.getString(KYNJSONKey.KEY_QUESTION_TRUE_ANSWER));
            if(object.has(KYNJSONKey.KEY_QUESTION_BOBOT))
                setBobot(object.getInt(KYNJSONKey.KEY_QUESTION_BOBOT));
            if(object.has(KYNJSONKey.KEY_QUESTION_CATEGORY))
                setCategory(object.getString(KYNJSONKey.KEY_QUESTION_CATEGORY));
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

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
