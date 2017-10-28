package com.example.aldoduha.ujikompetensi.Fragment.Controller;

import android.content.Intent;
import android.view.View;

import com.example.aldoduha.ujikompetensi.Fragment.KYNQuestionFormIdentityFragment;
import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.KYNQuestionFormActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNQuestionFormQuestionActivity;
import com.example.aldoduha.ujikompetensi.activity.controller.KYNQuestionFormIdentityController;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNDatePickerDialogListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by aldoduha on 10/26/2017.
 */

public class KYNQuestionIdentityController implements View.OnClickListener {
    private KYNQuestionFormIdentityFragment fragment;
    private KYNDatabaseHelper database;

    public KYNQuestionIdentityController(KYNQuestionFormIdentityFragment fragment) {
        this.fragment = fragment;
        this.database = new KYNDatabaseHelper(fragment.getActivity());
    }

    private KYNDatePickerDialogListener listener = new KYNDatePickerDialogListener() {

        @Override
        public void onSubmit() {
            if (submitDate()) {
                fragment.dismissDialog();
            }
        }

        @Override
        public void onCancel() {
            fragment.dismissDialog();
        }
    };

    private boolean submitDate() {
        if (fragment.getTextViewDOB().getText().toString().equals("")) {
            String date = fragment.getDateValue();
            fragment.getTextViewDOB().setText(date);
            return true;
        } else {
            String date = fragment.getDateValue();
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

            fragment.getTextViewDOB().setText(date);
            return true;
        }
    }

    private void showDialog(String headerTitle, String date) {
        fragment.showDialog(listener, headerTitle, date);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLanjut:
                onButtonLanjutClicked();
                break;
            case R.id.textviewDOB:
                showDialog("DOB", fragment.getTextViewDOB().getText().toString());
            default:
                break;
        }
    }

    private void onButtonLanjutClicked() {
        if (fragment.validate()) {
            fragment.setValueToModel();
            fragment.getNewActivity().onNextButtonClicked(1);
        }
    }
}

