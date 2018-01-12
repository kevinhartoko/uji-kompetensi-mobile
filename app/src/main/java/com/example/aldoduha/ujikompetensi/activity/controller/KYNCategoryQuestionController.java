package com.example.aldoduha.ujikompetensi.activity.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.KYNCategoryQuestionActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNQuestionListActivity;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

/**
 * Created by aldoduha on 1/12/2018.
 */

public class KYNCategoryQuestionController implements View.OnClickListener {
    private KYNCategoryQuestionActivity activity;
    private String category="";
    private BroadcastReceiver localBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(KYNIntentConstant.ACTION_QUESTION_LIST)){
                onReceiveBroadCastForQuestionList(intent);
            }
        }
    };
    public KYNCategoryQuestionController(KYNCategoryQuestionActivity activity){
        this.activity = activity;
    }

    public void onResume(){
        registerLocalBroadCastReceiver();
    }

    public void onPause(){
        unregisterLocalBroadCastReceiver();
    }

    private void onDeveloperClicked(){
//        Intent intentList = new Intent(activity, KYNIntervieweeListActivity.class);
//        intentList.putExtra(KYNIntentConstant.INTENT_EXTRA_CATEGORY, "Developer");
//        activity.startActivity(intentList);
        category = "Developer";
        activity.getQuestionList(category);
    }

    private void onQaClicked(){
//        Intent intentList = new Intent(activity, KYNIntervieweeListActivity.class);
//        intentList.putExtra(KYNIntentConstant.INTENT_EXTRA_CATEGORY, "QA");
//        activity.startActivity(intentList);
        category = "QA";
        activity.getQuestionList(category);
    }

    private void onBaClicked(){
//        Intent intentList = new Intent(activity, KYNIntervieweeListActivity.class);
//        intentList.putExtra(KYNIntentConstant.INTENT_EXTRA_CATEGORY, "BA");
//        activity.startActivity(intentList);
        category = "BA";
        activity.getQuestionList(category);
    }

    private void onReceiveBroadCastForQuestionList(Intent intent) {
        activity.dismisLoadingDialog();
        Bundle bundle = intent.getExtras();
        int code = bundle.getInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_FAILED);
        String message = bundle .getString(KYNIntentConstant.BUNDLE_KEY_MESSAGE);

        if(code==KYNIntentConstant.CODE_FAILED ||
                code==KYNIntentConstant.CODE_QUESTION_LIST_FAILED){
            if(message!=null && !message.equals(""))
                activity.showAlertDialog("Error", message);
            else
                activity.showAlertDialog("Error", "Failed to get list");
        }else if(code==KYNIntentConstant.CODE_FAILED_TOKEN){
            activity.showErrorTokenDialog();
        }else if(code==KYNIntentConstant.CODE_QUESTION_LIST_SUCCESS){
            Intent intentList = new Intent(activity, KYNQuestionListActivity.class);
            intentList.putExtra(KYNIntentConstant.INTENT_EXTRA_CATEGORY, category);
            activity.startActivity(intentList);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDeveloper:
                onDeveloperClicked();
                break;
            case R.id.btnQA:
                onQaClicked();
                break;
            case R.id.btnBA:
                onBaClicked();
                break;
            default:
                break;
        }
    }
    public void registerLocalBroadCastReceiver(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(KYNIntentConstant.ACTION_QUESTION_LIST);
        intentFilter.addCategory(KYNIntentConstant.CATEGORY_QUESTION_LIST);
        LocalBroadcastManager.getInstance(activity.getApplicationContext()).registerReceiver(localBroadCastReceiver, intentFilter);
    }

    public void unregisterLocalBroadCastReceiver(){
        LocalBroadcastManager.getInstance(activity.getApplicationContext()).unregisterReceiver(localBroadCastReceiver);
    }
}
