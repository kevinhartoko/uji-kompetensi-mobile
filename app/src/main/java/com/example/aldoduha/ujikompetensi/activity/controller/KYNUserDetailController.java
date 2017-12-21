package com.example.aldoduha.ujikompetensi.activity.controller;

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

    public KYNUserDetailController(KYNUserDetailActivity activity){
        this.activity = activity;
        this.database = new KYNDatabaseHelper(activity);
        activity.initiateRole();
        activity.setValuesToView();
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
}
