package com.example.aldoduha.ujikompetensi.activity.controller;

import android.content.Intent;
import android.view.View;

import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.KYNCategoryIntervieweeActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNIntervieweeListActivity;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

/**
 * Created by aldoduha on 1/12/2018.
 */

public class KYNCategoryIntervieweeController implements View.OnClickListener {
    private KYNCategoryIntervieweeActivity activity;
    public KYNCategoryIntervieweeController(KYNCategoryIntervieweeActivity activity){
        this.activity = activity;
    }
    private void onDeveloperClicked(){
        Intent intentList = new Intent(activity, KYNIntervieweeListActivity.class);
        intentList.putExtra(KYNIntentConstant.INTENT_EXTRA_CATEGORY, "Developer");
        activity.startActivity(intentList);
    }

    private void onQaClicked(){
        Intent intentList = new Intent(activity, KYNIntervieweeListActivity.class);
        intentList.putExtra(KYNIntentConstant.INTENT_EXTRA_CATEGORY, "QA");
        activity.startActivity(intentList);
    }

    private void onBaClicked(){
        Intent intentList = new Intent(activity, KYNIntervieweeListActivity.class);
        intentList.putExtra(KYNIntentConstant.INTENT_EXTRA_CATEGORY, "BA");
        activity.startActivity(intentList);
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
}
