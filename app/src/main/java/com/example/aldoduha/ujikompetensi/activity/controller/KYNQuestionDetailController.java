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
import com.example.aldoduha.ujikompetensi.activity.KYNLoginActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNQuestionDetailActivity;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNConfirmationAlertDialogListener;
import com.example.aldoduha.ujikompetensi.connection.KYNSMPUtilities;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

/**
 * Created by aldoduha on 11/26/2017.
 */

public class KYNQuestionDetailController implements View.OnClickListener {
    private KYNQuestionDetailActivity activity;
    private KYNDatabaseHelper database;

    private BroadcastReceiver localBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            activity.dismisLoadingDialog();
            if (action.equals(KYNIntentConstant.ACTION_SUBMIT_QUESTION)) {
                Bundle bundle = intent.getExtras();
                int code = bundle.getInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_FAILED);
                String message = bundle .getString(KYNIntentConstant.BUNDLE_KEY_MESSAGE);

                if(code==KYNIntentConstant.CODE_FAILED ||
                        code==KYNIntentConstant.CODE_SUBMIT_QUESTION_FAILED){
                    if(message!=null && !message.equals(""))
                        activity.showAlertDialog("Error", message);
                    else
                        activity.showAlertDialog("Error", "Submit Question Failed");
                }else if(code==KYNIntentConstant.CODE_FAILED_TOKEN){
                    activity.showErrorTokenDialog();
                }else if(code==KYNIntentConstant.CODE_SUBMIT_QUESTION_SUCCESS){
                    activity.setResult(KYNIntentConstant.RESULT_CODE_QUESTION_DETAIL);
                    activity.finish();
                }
            }else if (action.equals(KYNIntentConstant.ACTION_DELETE_QUESTION)) {
                Bundle bundle = intent.getExtras();
                int code = bundle.getInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_FAILED);
                String message = bundle .getString(KYNIntentConstant.BUNDLE_KEY_MESSAGE);

                if(code==KYNIntentConstant.CODE_FAILED ||
                        code==KYNIntentConstant.CODE_DELETE_QUESTION_FAILED){
                    if(message!=null && !message.equals(""))
                        activity.showAlertDialog("Error", message);
                    else
                        activity.showAlertDialog("Error", "Delete Question Failed");
                }else if(code==KYNIntentConstant.CODE_FAILED_TOKEN){
                    activity.showErrorTokenDialog();
                }else if(code==KYNIntentConstant.CODE_DELETE_QUESTION_SUCCESS){
                    activity.setResult(KYNIntentConstant.RESULT_CODE_QUESTION_DETAIL);
                    activity.finish();
                }
            }
        }
    };

    public KYNQuestionDetailController(KYNQuestionDetailActivity activity){
        this.activity = activity;
        this.database = new KYNDatabaseHelper(activity);
        activity.initiateCategory();
        activity.setValuesToView();
        registerLocalBroadCastReceiver();
    }

    public void onDestroy(){
        unregisterLocalBroadCastReceiver();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLanjut:
                onButtonLanjutClicked();
                break;
            case R.id.btnKembali:
                onButtonKembaliClicked();
                break;
            case R.id.btnHapus:
                onButtonHapusClicked();
                break;
            default:
                break;
        }
    }
    private void onButtonLanjutClicked(){
        if(activity.validate()){
            activity.setValueToModel();
            if(KYNSMPUtilities.isConnectServer){
                activity.submitQuestion();
            }else {
                activity.setResult(KYNIntentConstant.RESULT_CODE_QUESTION_DETAIL);
                activity.finish();
            }
        }
    }
    private void onButtonKembaliClicked(){
        activity.onBackPressed();
    }

    private void onButtonHapusClicked(){
        activity.onButtonHapusClicked();
    }

    public void registerLocalBroadCastReceiver(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(KYNIntentConstant.ACTION_SUBMIT_QUESTION);
        intentFilter.addCategory(KYNIntentConstant.CATEGORY_SUBMIT_QUESTION);
        intentFilter.addAction(KYNIntentConstant.ACTION_DELETE_QUESTION);
        intentFilter.addCategory(KYNIntentConstant.CATEGORY_DELETE_QUESTION);
        LocalBroadcastManager.getInstance(activity.getApplicationContext()).registerReceiver(localBroadCastReceiver, intentFilter);
    }

    public void unregisterLocalBroadCastReceiver(){
        LocalBroadcastManager.getInstance(activity.getApplicationContext()).unregisterReceiver(localBroadCastReceiver);
    }
}
