package com.example.aldoduha.ujikompetensi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import com.example.aldoduha.ujikompetensi.KYNBaseActivity;
import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.controller.KYNCategoryIntervieweeController;
import com.example.aldoduha.ujikompetensi.alertDialog.KYNConfirmationAlertDialog;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNConfirmationAlertDialogListener;

/**
 * Created by aldoduha on 1/12/2018.
 */

public class KYNCategoryIntervieweeActivity extends KYNBaseActivity {
    private Activity activity;
    private KYNCategoryIntervieweeController controller;
    private Button developerButton;
    private Button baButton;
    private Button qaButton;
    private Button backButton;
    private KYNDatabaseHelper database;
    KYNConfirmationAlertDialog confirmationAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_interviewee);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        loadView();
        initDefaultValue();
    }

    @Override
    public void onBackPressed() {
        if (controller != null) {
            controller.showOnBackPressAlertDialog();
        } else {
            super.onBackPressed();
        }
    }

    private void loadView() {
        developerButton = (Button) findViewById(R.id.btnDeveloper);
        qaButton = (Button) findViewById(R.id.btnQA);
        baButton = (Button) findViewById(R.id.btnBA);
        backButton = (Button)findViewById(R.id.buttonKembali);
    }

    private void initDefaultValue() {
        controller = new KYNCategoryIntervieweeController(this);
        developerButton.setOnClickListener(controller);
        qaButton.setOnClickListener(controller);
        baButton.setOnClickListener(controller);
        backButton.setOnClickListener(controller);
        database = new KYNDatabaseHelper(this);
    }

    public void showOnBackPressAlertDialog(KYNConfirmationAlertDialogListener listener) {
        if (confirmationAlertDialog == null) {
            confirmationAlertDialog = new KYNConfirmationAlertDialog(this, listener, "Are you sure to go back?");
        }
        confirmationAlertDialog.setCancelable(false);
        confirmationAlertDialog.setCanceledOnTouchOutside(false);
        confirmationAlertDialog.show();
    }
}