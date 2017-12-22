package com.example.aldoduha.ujikompetensi.activity.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.KYNHomeActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNIntervieweeListActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNLoginActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNQuestionFormActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNQuestionListActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNTemplateListActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNUserListActivity;
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

    public KYNHomeController(KYNHomeActivity activity) {
        this.activity = activity;
        getView();
    }

    public void getView() {
        //untuk pengecekan menu yang ditampilakan berdasarkan role user
    }

    private void onJawabPertanyaanClicked() {
        Intent intent = new Intent(activity, KYNQuestionFormActivity.class);
        activity.startActivity(intent);
    }

    private void onListIntervieweeClicked() {
        Intent intent = new Intent(activity, KYNIntervieweeListActivity.class);
        activity.startActivity(intent);
//        activity.getIntervieweeList();
    }

    private void onUserManagementClicked() {
        Intent intent = new Intent(activity, KYNUserListActivity.class);
        activity.startActivity(intent);
//        activity.getUserList();
    }

    private void onQuestionManagementClicked() {
        Intent intent = new Intent(activity, KYNQuestionListActivity.class);
        activity.startActivity(intent);
//        activity.getQuestionList();
    }

    private void onTemplateManagementClicked() {
        Intent intent = new Intent(activity, KYNTemplateListActivity.class);
        activity.startActivity(intent);
//        activity.getTemplateList();
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
                activity.showAlertDialog("Error", "Gagal Logout");
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
                activity.showAlertDialog("Error", "Gagal Ambil List");
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
                activity.showAlertDialog("Error", "Gagal Ambil List");
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
                activity.showAlertDialog("Error", "Gagal Ambil List");
        }else if(code==KYNIntentConstant.CODE_FAILED_TOKEN){
            activity.showErrorTokenDialog();
        }else if(code==KYNIntentConstant.CODE_TEMPLATE_LIST_SUCCESS){
            Intent intentList = new Intent(activity, KYNTemplateListActivity.class);
            activity.startActivity(intentList);
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
                activity.showAlertDialog("Error", "Gagal Ambil List");
        }else if(code==KYNIntentConstant.CODE_FAILED_TOKEN){
            activity.showErrorTokenDialog();
        }else if(code==KYNIntentConstant.CODE_INTERVIEWEE_LIST_SUCCESS){
            Intent intentList = new Intent(activity, KYNIntervieweeListActivity.class);
            activity.startActivity(intentList);
        }
    }
}
