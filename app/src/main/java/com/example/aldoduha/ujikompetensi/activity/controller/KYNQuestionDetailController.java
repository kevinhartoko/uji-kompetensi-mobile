package com.example.aldoduha.ujikompetensi.activity.controller;

import android.view.View;

import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.KYNQuestionDetailActivity;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

/**
 * Created by aldoduha on 11/26/2017.
 */

public class KYNQuestionDetailController implements View.OnClickListener {
    private KYNQuestionDetailActivity activity;
    private KYNDatabaseHelper database;

    public KYNQuestionDetailController(KYNQuestionDetailActivity activity){
        this.activity = activity;
        this.database = new KYNDatabaseHelper(activity);
        activity.setValuesToView();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLanjut:
                onButtonLanjutClicked();
                break;
            default:
                break;
        }
    }
    private void onButtonLanjutClicked(){
        if(activity.validate()){
            activity.setValueToModel();
            activity.setResult(KYNIntentConstant.RESULT_CODE_QUESTION_DETAIL);
            activity.finish();
        }
    }
}
