package com.example.aldoduha.ujikompetensi.activity.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.KYNIntervieweeDetailActivity;
import com.example.aldoduha.ujikompetensi.connection.KYNSMPUtilities;
import com.example.aldoduha.ujikompetensi.model.KYNFeedbackModel;
import com.example.aldoduha.ujikompetensi.model.KYNUserModel;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

import java.util.List;

/**
 * Created by aldoduha on 12/14/2017.
 */

public class KYNIntervieweeDetailController implements View.OnClickListener{
    private KYNIntervieweeDetailActivity activity;
    private KYNDatabaseHelper database;
    private KYNFeedbackModel feedbackModel;

    private BroadcastReceiver localBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            activity.dismisLoadingDialog();
            if (action.equals(KYNIntentConstant.ACTION_SUBMIT_FEEDBACK)) {
                Bundle bundle = intent.getExtras();
                int code = bundle.getInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_FAILED);
                String message = bundle .getString(KYNIntentConstant.BUNDLE_KEY_MESSAGE);

                if(code==KYNIntentConstant.CODE_FAILED ||
                        code==KYNIntentConstant.CODE_SUBMIT_FEEDBACK_FAILED){
                    if(message!=null && !message.equals(""))
                        activity.showAlertDialog("Error", message);
                    else
                        activity.showAlertDialog("Error", "Gagal Submit Feedback");
                }else if(code==KYNIntentConstant.CODE_FAILED_TOKEN){
                    activity.showErrorTokenDialog();
                }else if(code==KYNIntentConstant.CODE_SUBMIT_FEEDBACK_SUCCESS){
                    if(activity.isDeleteFeedback()){
                        database.deleteFeedback(activity.getFeedbackId());
                    }else {
                        database.insertFeedback(feedbackModel);
                    }
                    activity.getEditTextFeedback().setText("");
                    List<KYNFeedbackModel> feedbackModels = database.getFeedbackList(activity.getIntervieweeId());
                    activity.generateFeedback(feedbackModels);
                }
            }
        }
    };

    public KYNIntervieweeDetailController(KYNIntervieweeDetailActivity activity){
        this.activity = activity;
        this.database = new KYNDatabaseHelper(activity);
        activity.setValuesToView();
        registerLocalBroadCastReceiver();
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
            case R.id.btnQuestion:
                onButtonQuestionClicked();
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
            KYNUserModel session = database.getSession();
            feedbackModel = new KYNFeedbackModel();
            feedbackModel.setDescription(feedback);
            feedbackModel.setIntervieweeModel(activity.getIntervieweeModel());
            feedbackModel.setName(session.getUsername());
            if(KYNSMPUtilities.isConnectServer){
                activity.setDeleteFeedback(false);
                activity.submitFeedback(feedbackModel);
            }else {
                database.insertFeedback(feedbackModel);
                activity.getEditTextFeedback().setText("");
                List<KYNFeedbackModel> feedbackModels = database.getFeedbackList(activity.getIntervieweeId());
                activity.generateFeedback(feedbackModels);
            }
        }
    }

    private void onButtonQuestionClicked(){
        if(activity.getLinearLayoutQuestion().getVisibility() == View.VISIBLE){
            activity.getLinearLayoutQuestion().setVisibility(View.GONE);
            activity.getBtnQuestion().setText("Tampilkan");
        }else{
            activity.getLinearLayoutQuestion().setVisibility(View.VISIBLE);
            activity.getBtnQuestion().setText("Sembunyikan");
        }
    }

    public void registerLocalBroadCastReceiver(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(KYNIntentConstant.ACTION_SUBMIT_FEEDBACK);
        intentFilter.addCategory(KYNIntentConstant.CATEGORY_SUBMIT_FEEDBACK);
        LocalBroadcastManager.getInstance(activity.getApplicationContext()).registerReceiver(localBroadCastReceiver, intentFilter);
    }

    public void unregisterLocalBroadCastReceiver(){
        LocalBroadcastManager.getInstance(activity.getApplicationContext()).unregisterReceiver(localBroadCastReceiver);
    }
}
