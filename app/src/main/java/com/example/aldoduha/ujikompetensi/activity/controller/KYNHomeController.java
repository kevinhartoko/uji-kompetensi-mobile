package com.example.aldoduha.ujikompetensi.activity.controller;

import android.content.Intent;
import android.view.View;

import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.KYNHomeActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNQuestionFormActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNQuestionFormIdentityActivity;

/**
 * Created by aldoduha on 10/14/2017.
 */

public class KYNHomeController implements View.OnClickListener {
    private KYNHomeActivity acitivity;

    public KYNHomeController(KYNHomeActivity acitivity){
        this.acitivity = acitivity;
        getView();
    }
    public void getView(){
        //untuk pengecekan menu yang ditampilakan berdasarkan role user
    }
    private void onJawabPertanyaanClicked(){
        Intent intent = new Intent(acitivity, KYNQuestionFormActivity.class);
        acitivity.startActivity(intent);
    }
    private void onListIntervieweeClicked(){

    }
    private void onUserManagementClicked(){

    }
    private void onQuestionManagementClicked(){

    }
    private void onTemplateManagementClicked(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
                acitivity.doConfirmationLogout();
                break;
            default:
                break;
        }
    }
}
