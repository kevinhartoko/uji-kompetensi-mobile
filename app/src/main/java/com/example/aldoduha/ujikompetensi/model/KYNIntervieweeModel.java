package com.example.aldoduha.ujikompetensi.model;

import com.example.aldoduha.ujikompetensi.utility.KYNJSONKey;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by aldoduha on 10/9/2017.
 */

public class KYNIntervieweeModel  implements Serializable {
    private Long id;
    private String serverId;
    private String nama;
    private String email;
    private String handphone;
    private String address;
    private Date dob;
    private String gender;
    private int score;
    private String category;
    //untuk kirim data
    private List<KYNQuestionModel> questionModels;

    public KYNIntervieweeModel(){

    }

    public KYNIntervieweeModel(JSONObject object){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {
            if(object.has(KYNJSONKey.KEY_SERVER_ID))
                setServerId(object.getString(KYNJSONKey.KEY_SERVER_ID));
            if(object.has(KYNJSONKey.KEY_INTERVIEWEE_NAME))
                setNama(object.getString(KYNJSONKey.KEY_INTERVIEWEE_NAME));
            if(object.has(KYNJSONKey.KEY_INTERVIEWEE_EMAIL))
                setEmail(object.getString(KYNJSONKey.KEY_INTERVIEWEE_EMAIL));
            if(object.has(KYNJSONKey.KEY_INTERVIEWEE_PHONE))
                setHandphone(object.getString(KYNJSONKey.KEY_INTERVIEWEE_PHONE));
//            if(object.has(KYNJSONKey.KEY_INTERVIEWEE_ADDRESS))
//                setAddress(object.getString(KYNJSONKey.KEY_INTERVIEWEE_ADDRESS));
            if(object.has(KYNJSONKey.KEY_INTERVIEWEE_DOB)) {
                String date = object.getString(KYNJSONKey.KEY_INTERVIEWEE_DOB);
                if(date != null && !date.equals("")) {
                    try {
                        setDob(dateFormat.parse(date));
                    }catch (ParseException e){
                        e.printStackTrace();
                    }
                }
            }
            if(object.has(KYNJSONKey.KEY_INTERVIEWEE_GENDER))
                setGender(object.getString(KYNJSONKey.KEY_INTERVIEWEE_GENDER));
            if(object.has(KYNJSONKey.KEY_INTERVIEWEE_SCORE))
                setScore(object.getInt(KYNJSONKey.KEY_INTERVIEWEE_SCORE));
            if(object.has(KYNJSONKey.KEY_INTERVIEWEE_CATEGORY))
                setCategory(object.getString(KYNJSONKey.KEY_INTERVIEWEE_CATEGORY));
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHandphone() {
        return handphone;
    }

    public void setHandphone(String handphone) {
        this.handphone = handphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public List<KYNQuestionModel> getQuestionModels() {
        return questionModels;
    }

    public void setQuestionModels(List<KYNQuestionModel> questionModels) {
        this.questionModels = questionModels;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
