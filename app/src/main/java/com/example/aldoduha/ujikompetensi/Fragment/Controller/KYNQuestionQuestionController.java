package com.example.aldoduha.ujikompetensi.Fragment.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.example.aldoduha.ujikompetensi.Fragment.KYNQuestionFormQuestionFragment;
import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.KYNQuestionFormQuestionActivity;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNInfoAlertDialogListener;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

/**
 * Created by aldoduha on 10/26/2017.
 */

public class KYNQuestionQuestionController implements View.OnClickListener {
    private KYNQuestionFormQuestionFragment fragment;
    private KYNDatabaseHelper database;

    private BroadcastReceiver localBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            fragment.getNewActivity().dismisLoadingDialog();
            if (action.equals(KYNIntentConstant.ACTION_SUBMIT_INTERVIEWEE_DATA)) {
                Bundle bundle = intent.getExtras();
                int code = bundle.getInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_FAILED);
                String message = bundle .getString(KYNIntentConstant.BUNDLE_KEY_MESSAGE);
                String score = bundle.getString(KYNIntentConstant.BUNDLE_KEY_SCORE);
                if(code==KYNIntentConstant.CODE_FAILED ||
                        code==KYNIntentConstant.CODE_SUBMIT_INTERVIEWEE_DATA_FAILED){
                    if(message!=null && !message.equals(""))
                        fragment.getNewActivity().showAlertDialog("Error", message);
                    else
                        fragment.getNewActivity().showAlertDialog("Error", "Submit Interviewee Data Failed");
                }else if(code==KYNIntentConstant.CODE_FAILED_TOKEN){
                    fragment.getNewActivity().showErrorTokenDialog();
                }else if(code==KYNIntentConstant.CODE_SUBMIT_INTERVIEWEE_DATA_SUCCESS){
                    fragment.getNewActivity().showAlertDialog("Your Score :", score, new KYNInfoAlertDialogListener() {
                        @Override
                        public void onOk() {
                            fragment.getNewActivity().finish();
                        }
                    }, true);
                }
            }
        }
    };

    public KYNQuestionQuestionController(KYNQuestionFormQuestionFragment fragment){
        this.fragment = fragment;
        this.database = new KYNDatabaseHelper(fragment.getActivity());
    }

    private void onButtonSubmitClicked(){
        fragment.onButtonSubmitClicked();
    }
    private void onButtonKembaliClicked(){
        fragment.onButtonKembaliClicked();
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

    public void registerLocalBroadCastReceiver(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(KYNIntentConstant.ACTION_SUBMIT_INTERVIEWEE_DATA);
        intentFilter.addCategory(KYNIntentConstant.CATEGORY_SUBMIT_INTERVIEWEE_DATA);
        LocalBroadcastManager.getInstance(fragment.getNewActivity().getApplicationContext()).registerReceiver(localBroadCastReceiver, intentFilter);
    }

    public void unregisterLocalBroadCastReceiver(){
        LocalBroadcastManager.getInstance(fragment.getNewActivity().getApplicationContext()).unregisterReceiver(localBroadCastReceiver);
    }
}
