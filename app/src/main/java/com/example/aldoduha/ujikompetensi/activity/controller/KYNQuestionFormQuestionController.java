package com.example.aldoduha.ujikompetensi.activity.controller;

import android.view.View;

import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.KYNQuestionFormIdentityActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNQuestionFormQuestionActivity;

/**
 * Created by aldoduha on 10/26/2017.
 */

public class KYNQuestionFormQuestionController implements View.OnClickListener {
    private KYNQuestionFormQuestionActivity activity;
    private KYNDatabaseHelper database;

    public KYNQuestionFormQuestionController(KYNQuestionFormQuestionActivity activity){
        this.activity = activity;
        this.database = new KYNDatabaseHelper(activity);
    }

    private void onButtonSubmitClicked(){
        activity.onButtonSubmitClicked();
    }
    private void onButtonKembaliClicked(){
        activity.onButtonKembaliClicked();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSubmit:
                onButtonSubmitClicked();
                break;
            case R.id.btnKembali:
                onButtonKembaliClicked();
                break;
            default:
                break;
        }
    }
}
