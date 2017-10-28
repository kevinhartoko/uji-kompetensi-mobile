package com.example.aldoduha.ujikompetensi.activity.controller;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.KYNQuestionFormIdentityActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNQuestionFormQuestionActivity;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNDatePickerDialogListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by aldoduha on 10/15/2017.
 */

public class KYNQuestionFormIdentityController implements View.OnClickListener {
    private KYNQuestionFormIdentityActivity activity;
    private KYNDatabaseHelper database;

    public KYNQuestionFormIdentityController(KYNQuestionFormIdentityActivity activity){
        this.activity = activity;
        this.database = new KYNDatabaseHelper(activity);
    }

    private KYNDatePickerDialogListener listener = new KYNDatePickerDialogListener() {

        @Override
        public void onSubmit() {
            if(submitDate()){
                activity.dismissDialog();
            }
        }

        @Override
        public void onCancel() {
            activity.dismissDialog();
        }
    };
    private boolean submitDate(){
        if(activity.getTextViewDOB().getText().toString().equals("")){
            String date = activity.getDateValue();
            activity.getTextViewDOB().setText(date);
            return true;
        }else{
            String date = activity.getDateValue();
            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            Date dates = null;
            try {
                dates = dateformat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, 1999);
            cal.set(Calendar.MONTH, 12);
            cal.set(Calendar.DAY_OF_MONTH, 31);
            cal.set(Calendar.HOUR, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);

            activity.getTextViewDOB().setText(date);
            return true;
        }
    }

    private void showDialog(String headerTitle, String date){
        activity.showDialog(listener, headerTitle, date);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLanjut:
                onButtonLanjutClicked();
                break;
            case R.id.textviewDOB:
                showDialog("DOB", activity.getTextViewDOB().getText().toString());
            default:
                break;
        }
    }
    private void onButtonLanjutClicked(){
        if(activity.validate()){
            activity.setValueToModel();
            Intent i = new Intent(activity, KYNQuestionFormQuestionActivity.class);
            activity.startActivityForResult(i,1);
        }
    }
}
