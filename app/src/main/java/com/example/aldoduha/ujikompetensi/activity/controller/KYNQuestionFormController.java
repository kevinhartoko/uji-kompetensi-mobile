package com.example.aldoduha.ujikompetensi.activity.controller;

import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.activity.KYNQuestionFormActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNQuestionFormQuestionActivity;
import com.example.aldoduha.ujikompetensi.model.KYNIntervieweeModel;
import com.example.aldoduha.ujikompetensi.model.KYNQuestionModel;

import java.util.List;

/**
 * Created by aldoduha on 10/26/2017.
 */

public class KYNQuestionFormController {
    private KYNQuestionFormActivity activity;
    private KYNDatabaseHelper database;

    public KYNQuestionFormController(KYNQuestionFormActivity activity){
        this.activity = activity;
        this.database = new KYNDatabaseHelper(activity);
    }

    public KYNIntervieweeModel getIntervieweeModel(Long id){
        return database.getInterviewee(id);
    }
    public List<KYNQuestionModel> getListQuestion(){
        return database.getListQuestion();
    }
}
