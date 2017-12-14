package com.example.aldoduha.ujikompetensi.activity.controller;

import android.view.View;

import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.KYNIntervieweeDetailActivity;
import com.example.aldoduha.ujikompetensi.model.KYNFeedbackModel;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

import java.util.List;

/**
 * Created by aldoduha on 12/14/2017.
 */

public class KYNIntervieweeDetailController implements View.OnClickListener{
    private KYNIntervieweeDetailActivity activity;
    private KYNDatabaseHelper database;

    public KYNIntervieweeDetailController(KYNIntervieweeDetailActivity activity){
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
            case R.id.btnKirim:
                onButtonKirimClicked();
                break;
            default:
                break;
        }
    }
    private void onButtonLanjutClicked(){
        activity.setResult(KYNIntentConstant.RESULT_CODE_INTERVIEWEE_DETAIL);
        activity.finish();
    }

    private void onButtonKirimClicked(){
        String feedback = activity.getEditTextFeedback().getText().toString().trim();
        if(feedback!=null && !feedback.equals("")){
            KYNFeedbackModel model = new KYNFeedbackModel();
            model.setDescription(feedback);
            model.setIntervieweeModel(activity.getIntervieweeModel());
            model.setName("haha");
            database.insertFeedback(model);
            List<KYNFeedbackModel> feedbackModels = database.getFeedbackList(activity.getIntervieweeId());
            activity.generateFeedback(feedbackModels);
        }
    }
}
