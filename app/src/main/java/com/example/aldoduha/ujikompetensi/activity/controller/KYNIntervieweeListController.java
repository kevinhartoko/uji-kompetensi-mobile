package com.example.aldoduha.ujikompetensi.activity.controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;

import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.KYNIntervieweeDetailActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNIntervieweeListActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNTemplateDetailActivity;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNConfirmationAlertDialogListener;
import com.example.aldoduha.ujikompetensi.model.KYNIntervieweeModel;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by aldoduha on 12/10/2017.
 */

public class KYNIntervieweeListController implements View.OnClickListener, AdapterView.OnItemClickListener, TextWatcher {
    private KYNIntervieweeListActivity activity;
    private KYNDatabaseHelper database;
    private List<KYNIntervieweeModel> intervieweeModels;

    private KYNConfirmationAlertDialogListener backPressListener = new KYNConfirmationAlertDialogListener() {
        @Override
        public void onOK() {
            activity.finish();
        }

        @Override
        public void onCancel() {

        }
    };

    public KYNIntervieweeListController(KYNIntervieweeListActivity activity) {
        this.activity = activity;
        this.database = new KYNDatabaseHelper(activity);
        intervieweeModels = new ArrayList<>();
        intervieweeModels = database.getListInterviewee();
        activity.generateList(intervieweeModels);
    }

    public void showOnBackPressAlertDialog() {
        activity.showOnBackPressAlertDialog(backPressListener);
    }

    public void onRefreshButtonClicked(){
        activity.getSearchEdittext().setText("");
        intervieweeModels = database.getListInterviewee();
        activity.updateListView(intervieweeModels);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRefresh:
                onRefreshButtonClicked();
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

    private void onSearchCustomer(CharSequence key){
        List<KYNIntervieweeModel> listIntervieweeAfterSearch = new ArrayList<KYNIntervieweeModel>();
        List<KYNIntervieweeModel> listIntervieweeAll = intervieweeModels;

        if (key.length() > 0) {
            String keyString = key.toString().toLowerCase();
            for (int i = 0; i < listIntervieweeAll.size(); i++) {
                KYNIntervieweeModel model = listIntervieweeAll.get(i);
                if (model.getNama().toLowerCase().contains(keyString)) {
                    listIntervieweeAfterSearch.add(model);
                }
            }
            activity.updateListView(listIntervieweeAfterSearch);
        }else{
            activity.updateListView(listIntervieweeAll);
        }
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        KYNIntervieweeModel intervieweeModel = activity.getAdapter().getItem(position);
        Bundle b = new Bundle();
        b.putLong("intervieweeId", intervieweeModel.getId());
        Intent i = new Intent(activity, KYNIntervieweeDetailActivity.class);
        i.putExtras(b);
        activity.startActivityForResult(i, KYNIntentConstant.REQUEST_CODE_INTERVIEWEE_DETAIL);
    }

    public List<KYNIntervieweeModel> getIntervieweeModels() {
        return intervieweeModels;
    }

    public void setIntervieweeModels(List<KYNIntervieweeModel> intervieweeModels) {
        this.intervieweeModels = intervieweeModels;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(activity.getSearchEdittext().hasFocus())
            onSearchCustomer(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
