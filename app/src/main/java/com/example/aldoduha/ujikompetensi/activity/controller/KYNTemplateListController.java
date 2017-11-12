package com.example.aldoduha.ujikompetensi.activity.controller;

import android.view.View;

import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.KYNTemplateListActivity;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNConfirmationAlertDialogListener;
import com.example.aldoduha.ujikompetensi.model.KYNTemplateModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aldoduha on 11/12/2017.
 */

public class KYNTemplateListController implements View.OnClickListener {
    private KYNTemplateListActivity activity;
    private KYNDatabaseHelper database;
    private List<KYNTemplateModel> templates;

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

    private void registerBroadcastReceiver() {
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(MTFIntentConstant.ACTION_NEW_CUSTOMER);
//        intentFilter.addAction(MTFIntentConstant.ACTION_UPDATE_CUSTOMER);
//        intentFilter.addAction(MTFIntentConstant.ACTION_CHECK_CIF);
//        intentFilter.addAction(MTFIntentConstant.ACTION_CHECK_DHIB);
//        intentFilter.addCategory(MTFIntentConstant.CATEGORY_REGISTER_CUSTOMER);
//        intentFilter.addCategory(MTFIntentConstant.CATEGORY_CUSTOMER_LIST);
//        LocalBroadcastManager.getInstance(activity.getApplicationContext()).registerReceiver(localBroadCastReceiver, intentFilter);
    }

    public void unregisterLocalBroadCastReceiver() {
//        LocalBroadcastManager.getInstance(activity.getApplicationContext()).unregisterReceiver(localBroadCastReceiver);
    }

    public void onDestory() {
        database.close();
    }

    public void onResume() {
        registerBroadcastReceiver();
    }

    public void onPause() {
        unregisterLocalBroadCastReceiver();
    }
}
