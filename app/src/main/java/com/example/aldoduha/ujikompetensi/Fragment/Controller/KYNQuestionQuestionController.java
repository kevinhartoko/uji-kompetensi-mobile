package com.example.aldoduha.ujikompetensi.Fragment.Controller;

import android.view.View;

import com.example.aldoduha.ujikompetensi.Fragment.KYNQuestionFormQuestionFragment;
import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.KYNQuestionFormQuestionActivity;

/**
 * Created by aldoduha on 10/26/2017.
 */

public class KYNQuestionQuestionController implements View.OnClickListener {
    private KYNQuestionFormQuestionFragment fragment;
    private KYNDatabaseHelper database;
    public KYNQuestionQuestionController(KYNQuestionFormQuestionFragment fragment){
        this.fragment = fragment;
        this.database = new KYNDatabaseHelper(fragment.getActivity());
    }

    private void onButtonSubmitClicked(){
        fragment.onButtonSubmitClicked();
    }
    private void onButtonKembaliClicked(){
        fragment.onButtonKembaliClicked();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSubmit:
                onButtonSubmitClicked();
                break;
            case R.id.btnKembali:
                onButtonKembaliClicked();
                break;
            default:
                break;
        }
    }
}
