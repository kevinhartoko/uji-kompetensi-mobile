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
import com.example.aldoduha.ujikompetensi.activity.KYNQuestionFormActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNTemplateDetailActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNTemplateListActivity;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNConfirmationAlertDialogListener;
import com.example.aldoduha.ujikompetensi.model.KYNTemplateModel;
import com.example.aldoduha.ujikompetensi.model.KYNUserModel;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aldoduha on 11/12/2017.
 */

public class KYNTemplateListController implements View.OnClickListener {
    private KYNTemplateListActivity activity;
    private KYNDatabaseHelper database;
    private List<KYNTemplateModel> templates;

    private BroadcastReceiver localBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
           if(action.equals(KYNIntentConstant.ACTION_TEMPLATE_LIST)){
                onReceiveBroadCastForTemplateList(intent);
            }else if(action.equals(KYNIntentConstant.ACTION_TEMPLATE_DETAIL)){
               onReceiveBroadCastForTemplateDetail(intent);
           }
        }
    };

    private void onReceiveBroadCastForTemplateList(Intent intent) {
        activity.dismisLoadingDialog();
        Bundle bundle = intent.getExtras();
        int code = bundle.getInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_FAILED);
        String message = bundle .getString(KYNIntentConstant.BUNDLE_KEY_MESSAGE);

        if(code==KYNIntentConstant.CODE_FAILED ||
                code==KYNIntentConstant.CODE_TEMPLATE_LIST_FAILED){
            if(message!=null && !message.equals(""))
                activity.showAlertDialog("Error", message);
            else
                activity.showAlertDialog("Error", "Failed to get list");
        }else if(code==KYNIntentConstant.CODE_FAILED_TOKEN){
            activity.showErrorTokenDialog();
        }else if(code==KYNIntentConstant.CODE_TEMPLATE_LIST_SUCCESS){
            List<KYNTemplateModel> templateModels = database.getTemplateList();
            activity.generateTable(templateModels);
        }
    }
    private void onReceiveBroadCastForTemplateDetail(Intent intent) {
        activity.dismisLoadingDialog();
        Bundle bundle = intent.getExtras();
        int code = bundle.getInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_FAILED);
        String message = bundle .getString(KYNIntentConstant.BUNDLE_KEY_MESSAGE);

        if(code==KYNIntentConstant.CODE_FAILED ||
                code==KYNIntentConstant.CODE_TEMPLATE_DETAIL_FAILED){
            if(message!=null && !message.equals(""))
                activity.showAlertDialog("Error", message);
            else
                activity.showAlertDialog("Error", "Failed to get detail");
        }else if(code==KYNIntentConstant.CODE_FAILED_TOKEN){
            activity.showErrorTokenDialog();
        }else if(code==KYNIntentConstant.CODE_TEMPLATE_DETAIL_SUCCESS){
            Bundle b = new Bundle();
            Long id = bundle.getLong(KYNIntentConstant.BUNDLE_KEY_ID,0l);
            b.putLong("templateId", id);
            Intent i = new Intent(activity, KYNTemplateDetailActivity.class);
            i.putExtras(b);
            activity.startActivityForResult(i, KYNIntentConstant.REQUEST_CODE_TEMPLATE_DETAIL);
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

    public KYNTemplateListController(KYNTemplateListActivity activity) {
        this.activity = activity;
        this.database = new KYNDatabaseHelper(activity);
        templates = new ArrayList<>();
        templates = database.getTemplateList();
        activity.generateTable(templates);
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
        intentFilter.addAction(KYNIntentConstant.ACTION_TEMPLATE_LIST);
        intentFilter.addCategory(KYNIntentConstant.CATEGORY_TEMPLATE_LIST);
        intentFilter.addAction(KYNIntentConstant.ACTION_TEMPLATE_DETAIL);
        intentFilter.addCategory(KYNIntentConstant.CATEGORY_TEMPLATE_DETAIL);
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
