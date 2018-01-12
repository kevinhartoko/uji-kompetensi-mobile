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
import com.example.aldoduha.ujikompetensi.activity.KYNQuestionListActivity;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNConfirmationAlertDialogListener;
import com.example.aldoduha.ujikompetensi.model.KYNQuestionModel;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aldoduha on 11/12/2017.
 */

public class KYNQuestionListController implements View.OnClickListener {
    private KYNQuestionListActivity activity;
    private KYNDatabaseHelper database;
    private List<KYNQuestionModel> questions;

    private BroadcastReceiver localBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(KYNIntentConstant.ACTION_QUESTION_LIST)){
                onReceiveBroadCastForQuestionList(intent);
            }
        }
    };

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
            questions = database.getListQuestion();
            activity.generateTable(questions);
        }
    }

    private KYNConfirmationAlertDialogListener backPressListener = new KYNConfirmationAlertDialogListener() {
        @Override
        public void onOK() {
            activity.finish();
        }

        @Override
        public void onCancel() {

        }
    };

    public KYNQuestionListController(KYNQuestionListActivity activity) {
        this.activity = activity;
        this.database = new KYNDatabaseHelper(activity);
        questions = new ArrayList<>();
        questions = database.getListQuestion();
        activity.generateTable(questions);
    }

    public void showOnBackPressAlertDialog() {
        activity.showOnBackPressAlertDialog(backPressListener);
    }

    private void onAddButtonClicked() {
        activity.onAddButtonClicked();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonKembali:
                showOnBackPressAlertDialog();
                break;
            case R.id.buttonTambah:
                onAddButtonClicked();
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

    public void onDestory() {
        database.close();
    }

    public void onResume() {
        registerLocalBroadCastReceiver();
    }

    public void onPause() {
        unregisterLocalBroadCastReceiver();
    }
}
