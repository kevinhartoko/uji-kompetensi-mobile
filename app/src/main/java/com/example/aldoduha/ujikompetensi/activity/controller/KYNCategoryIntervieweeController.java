package com.example.aldoduha.ujikompetensi.activity.controller;

import android.content.Intent;
import android.view.View;

import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.KYNCategoryIntervieweeActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNIntervieweeListActivity;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNConfirmationAlertDialogListener;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

/**
 * Created by aldoduha on 1/12/2018.
 */

public class KYNCategoryIntervieweeController implements View.OnClickListener {
    private KYNCategoryIntervieweeActivity activity;
    public KYNCategoryIntervieweeController(KYNCategoryIntervieweeActivity activity){
        this.activity = activity;
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
    private void onDeveloperClicked(){
        Intent intentList = new Intent(activity, KYNIntervieweeListActivity.class);
        intentList.putExtra(KYNIntentConstant.INTENT_EXTRA_CATEGORY, "Developer");
        activity.startActivity(intentList);
    }

    private void onQaClicked(){
        Intent intentList = new Intent(activity, KYNIntervieweeListActivity.class);
        intentList.putExtra(KYNIntentConstant.INTENT_EXTRA_CATEGORY, "Quality Assurance");
        activity.startActivity(intentList);
    }

    private void onBaClicked(){
        Intent intentList = new Intent(activity, KYNIntervieweeListActivity.class);
        intentList.putExtra(KYNIntentConstant.INTENT_EXTRA_CATEGORY, "Business Analyst");
        activity.startActivity(intentList);
    }

    public void showOnBackPressAlertDialog() {
        activity.showOnBackPressAlertDialog(backPressListener);
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
            case R.id.buttonKembali:
                showOnBackPressAlertDialog();
                break;
            default:
                break;
        }
    }
}
