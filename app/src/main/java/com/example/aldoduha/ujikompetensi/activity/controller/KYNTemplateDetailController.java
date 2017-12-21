package com.example.aldoduha.ujikompetensi.activity.controller;

import android.view.View;

import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.KYNTemplateDetailActivity;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

/**
 * Created by aldoduha on 12/2/2017.
 */

public class KYNTemplateDetailController implements View.OnClickListener {
    private KYNTemplateDetailActivity activity;
    private KYNDatabaseHelper database;

    public KYNTemplateDetailController(KYNTemplateDetailActivity activity){
        this.activity = activity;
        this.database = new KYNDatabaseHelper(activity);
        activity.setValuesToView();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLanjut:
                onButtonLanjutClicked();
                break;
            case R.id.btnTambah:
                activity.onTambahButtonClicked();
                break;
            case R.id.btnHapus:
                activity.onHapusButtonClicked();
                break;
            case R.id.btnHapusTemplate:
                onButtonHapusTemplateClicked();
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
            activity.setResult(KYNIntentConstant.RESULT_CODE_TEMPLATE_DETAIL);
            activity.finish();
        }
    }
    private void onButtonKembaliClicked(){
        activity.onBackPressed();
    }
    private void onButtonHapusTemplateClicked(){
        activity.onButtonHapusTemplateClicked();
    }
}
