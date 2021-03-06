package com.example.aldoduha.ujikompetensi.activity.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.KYNCategoryIntervieweeActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNCategoryQuestionActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNHomeActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNIntervieweeListActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNLoginActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNQuestionFormActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNQuestionListActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNTemplateListActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNUserListActivity;
import com.example.aldoduha.ujikompetensi.connection.KYNSMPUtilities;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

/**
 * Created by aldoduha on 10/14/2017.
 */

public class KYNHomeController implements View.OnClickListener {
    private KYNHomeActivity activity;

    private BroadcastReceiver localBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(KYNIntentConstant.ACTION_LOGOUT)) {
                onReceiveBroadCastForLogout(intent);
            }else if(action.equals(KYNIntentConstant.ACTION_USER_LIST)){
                onReceiveBroadCastForUserList(intent);
            }else if(action.equals(KYNIntentConstant.ACTION_QUESTION_LIST)){
                onReceiveBroadCastForQuestionList(intent);
            }else if(action.equals(KYNIntentConstant.ACTION_TEMPLATE_LIST)){
                onReceiveBroadCastForTemplateList(intent);
            }else if(action.equals(KYNIntentConstant.ACTION_INTERVIEWEE_LIST)){
                onReceiveBroadCastForIntervieweeList(intent);
            }
        }
    };

    public void onResume(){
        registerLocalBroadCastReceiver();
    }

    public void onPause(){
        unregisterLocalBroadCastReceiver();
    }

    public KYNHomeController(KYNHomeActivity activity) {
        this.activity = activity;
        getView();
    }

    public void getView() {
        if(activity.getSession().getRole().equalsIgnoreCase("admin")){
            activity.getAdminLinear().setVisibility(View.VISIBLE);
            activity.getIntervieweeLinear().setVisibility(View.GONE);
            activity.getListIntervieweeButton().setVisibility(View.VISIBLE);
        }else if(activity.getSession().getRole().equalsIgnoreCase("interviewee")){
            activity.getAdminLinear().setVisibility(View.GONE);
            activity.getIntervieweeLinear().setVisibility(View.VISIBLE);
            activity.getListIntervieweeButton().setVisibility(View.GONE);
        }else if(activity.getSession().getRole().equalsIgnoreCase("interviewer")){
            activity.getAdminLinear().setVisibility(View.GONE);
            activity.getIntervieweeLinear().setVisibility(View.GONE);
            activity.getListIntervieweeButton().setVisibility(View.VISIBLE);
        }
        //untuk pengecekan menu yang ditampilakan berdasarkan role user
    }

    private void onJawabPertanyaanClicked() {
        if(KYNSMPUtilities.isConnectServer){
            activity.getTemplateList();
        }else{
            Intent intent = new Intent(activity, KYNQuestionFormActivity.class);
            activity.startActivity(intent);
        }
    }

    private void onListIntervieweeClicked() {
        if(KYNSMPUtilities.isConnectServer){
            activity.getIntervieweeList();
        }else {
            Intent intent = new Intent(activity, KYNIntervieweeListActivity.class);
            activity.startActivity(intent);
        }
    }

    private void onUserManagementClicked() {
        if(KYNSMPUtilities.isConnectServer){
            activity.getUserList();
        }else {
            Intent intent = new Intent(activity, KYNUserListActivity.class);
            activity.startActivity(intent);
        }
    }

    private void onQuestionManagementClicked() {
        Intent intent = new Intent(activity, KYNCategoryQuestionActivity.class);
        activity.startActivity(intent);
    }

    private void onTemplateManagementClicked() {
        if(KYNSMPUtilities.isConnectServer){
            activity.getTemplateList();
        }else {
            Intent intent = new Intent(activity, KYNTemplateListActivity.class);
            activity.startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnJawabPertanyaan:
                onJawabPertanyaanClicked();
                break;
            case R.id.btnListInterviewee:
                onListIntervieweeClicked();
                break;
            case R.id.btnUserManagement:
                onUserManagementClicked();
                break;
            case R.id.btnQuestionManagement:
                onQuestionManagementClicked();
                break;
            case R.id.btnTemplateManagement:
                onTemplateManagementClicked();
                break;
            case R.id.btnLogout:
                activity.doConfirmationLogout();
                break;
            default:
                break;
        }
    }

    private void onReceiveBroadCastForLogout(Intent intent) {
        activity.dismisLoadingDialog();
        Bundle bundle = intent.getExtras();
        int code = bundle.getInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_FAILED);
        String message = bundle .getString(KYNIntentConstant.BUNDLE_KEY_MESSAGE);

        if(code==KYNIntentConstant.CODE_FAILED ||
                code==KYNIntentConstant.CODE_LOGOUT_FAILED){
            if(message!=null && !message.equals(""))
                activity.showAlertDialog("Error", message);
            else
                activity.showAlertDialog("Error", "Logout Failed");
        }else if(code==KYNIntentConstant.CODE_FAILED_TOKEN){
            activity.showErrorTokenDialog();
        }else if(code==KYNIntentConstant.CODE_LOGOUT_SUCCESS){
            activity.finish();
            Intent intentLogin = new Intent(activity, KYNLoginActivity.class);
            activity.startActivity(intentLogin);
        }
    }

    private void onReceiveBroadCastForUserList(Intent intent) {
        activity.dismisLoadingDialog();
        Bundle bundle = intent.getExtras();
        int code = bundle.getInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_FAILED);
        String message = bundle .getString(KYNIntentConstant.BUNDLE_KEY_MESSAGE);

        if(code==KYNIntentConstant.CODE_FAILED ||
                code==KYNIntentConstant.CODE_USER_LIST_FAILED){
            if(message!=null && !message.equals(""))
                activity.showAlertDialog("Error", message);
            else
                activity.showAlertDialog("Error", "Failed to get list");
        }else if(code==KYNIntentConstant.CODE_FAILED_TOKEN){
            activity.showErrorTokenDialog();
        }else if(code==KYNIntentConstant.CODE_USER_LIST_SUCCESS){
            Intent intentList = new Intent(activity, KYNUserListActivity.class);
            activity.startActivity(intentList);
        }
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
            activity.startActivity(intentList);
        }
    }

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
            if(activity.getSession().getRole().equalsIgnoreCase("admin")) {
                Intent intentList = new Intent(activity, KYNTemplateListActivity.class);
                activity.startActivity(intentList);
            }else{
                Intent intentActivity = new Intent(activity, KYNQuestionFormActivity.class);
                activity.startActivity(intentActivity);
            }
        }
    }

    private void onReceiveBroadCastForIntervieweeList(Intent intent) {
        activity.dismisLoadingDialog();
        Bundle bundle = intent.getExtras();
        int code = bundle.getInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_FAILED);
        String message = bundle .getString(KYNIntentConstant.BUNDLE_KEY_MESSAGE);

        if(code==KYNIntentConstant.CODE_FAILED ||
                code==KYNIntentConstant.CODE_INTERVIEWEE_LIST_FAILED){
            if(message!=null && !message.equals(""))
                activity.showAlertDialog("Error", message);
            else
                activity.showAlertDialog("Error", "Failed to get list");
        }else if(code==KYNIntentConstant.CODE_FAILED_TOKEN){
            activity.showErrorTokenDialog();
        }else if(code==KYNIntentConstant.CODE_INTERVIEWEE_LIST_SUCCESS){
            Intent intentList = new Intent(activity, KYNCategoryIntervieweeActivity.class);
            activity.startActivity(intentList);
        }
    }
    public void registerLocalBroadCastReceiver(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(KYNIntentConstant.ACTION_LOGOUT);
        intentFilter.addAction(KYNIntentConstant.ACTION_USER_LIST);
        intentFilter.addAction(KYNIntentConstant.ACTION_TEMPLATE_LIST);
        intentFilter.addAction(KYNIntentConstant.ACTION_QUESTION_LIST);
        intentFilter.addAction(KYNIntentConstant.ACTION_INTERVIEWEE_LIST);
        intentFilter.addCategory(KYNIntentConstant.CATEGORY_LOGOUT);
        intentFilter.addCategory(KYNIntentConstant.CATEGORY_USER_LIST);
        intentFilter.addCategory(KYNIntentConstant.CATEGORY_TEMPLATE_LIST);
        intentFilter.addCategory(KYNIntentConstant.CATEGORY_QUESTION_LIST);
        intentFilter.addCategory(KYNIntentConstant.CATEGORY_INTERVIEWEE_LIST);
        LocalBroadcastManager.getInstance(activity.getApplicationContext()).registerReceiver(localBroadCastReceiver, intentFilter);
    }

    public void unregisterLocalBroadCastReceiver(){
        LocalBroadcastManager.getInstance(activity.getApplicationContext()).unregisterReceiver(localBroadCastReceiver);
    }
}
