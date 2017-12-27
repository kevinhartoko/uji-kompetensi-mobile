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
import com.example.aldoduha.ujikompetensi.activity.KYNUserDetailActivity;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

/**
 * Created by aldoduha on 12/1/2017.
 */

public class KYNUserDetailController implements View.OnClickListener {
    private KYNUserDetailActivity activity;
    private KYNDatabaseHelper database;

    private BroadcastReceiver localBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            activity.dismisLoadingDialog();
            if (action.equals(KYNIntentConstant.ACTION_SUBMIT_USER)) {
                Bundle bundle = intent.getExtras();
                int code = bundle.getInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_FAILED);
                String message = bundle .getString(KYNIntentConstant.BUNDLE_KEY_MESSAGE);

                if(code==KYNIntentConstant.CODE_FAILED ||
                        code==KYNIntentConstant.CODE_SUBMIT_USER_FAILED){
                    if(message!=null && !message.equals(""))
                        activity.showAlertDialog("Error", message);
                    else
                        activity.showAlertDialog("Error", "Gagal Submit User");
                }else if(code==KYNIntentConstant.CODE_FAILED_TOKEN){
                    activity.showErrorTokenDialog();
                }else if(code==KYNIntentConstant.CODE_SUBMIT_USER_SUCCESS){
                    activity.finish();
                }
            }
        }
    };

    public KYNUserDetailController(KYNUserDetailActivity activity){
        this.activity = activity;
        this.database = new KYNDatabaseHelper(activity);
        activity.initiateRole();
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
            case R.id.btnHapus:
                onButtonHapusClicked();
                break;
            case R.id.btnKembali:
                onButtonKembaliClicked();
                break;
            default:
                break;
        }
    }
    private void onButtonLanjutClicked(){
        if(activity.validate()){
            activity.setValueToModel();
            activity.setResult(KYNIntentConstant.RESULT_CODE_USER_DETAIL);
            activity.finish();
        }
    }
    private void onButtonHapusClicked(){
        activity.onButtonHapusClicked();
    }
    private void onButtonKembaliClicked(){
        activity.onBackPressed();
    }

    public void registerLocalBroadCastReceiver(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(KYNIntentConstant.ACTION_SUBMIT_USER);
        intentFilter.addCategory(KYNIntentConstant.CATEGORY_SUBMIT_USER);
        LocalBroadcastManager.getInstance(activity.getApplicationContext()).registerReceiver(localBroadCastReceiver, intentFilter);
    }

    public void unregisterLocalBroadCastReceiver(){
        LocalBroadcastManager.getInstance(activity.getApplicationContext()).unregisterReceiver(localBroadCastReceiver);
    }
}
