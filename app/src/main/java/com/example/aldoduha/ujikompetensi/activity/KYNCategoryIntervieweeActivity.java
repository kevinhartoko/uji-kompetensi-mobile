package com.example.aldoduha.ujikompetensi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import com.example.aldoduha.ujikompetensi.KYNBaseActivity;
import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.controller.KYNCategoryIntervieweeController;

/**
 * Created by aldoduha on 1/12/2018.
 */

public class KYNCategoryIntervieweeActivity extends KYNBaseActivity {
    private Activity activity;
    private KYNCategoryIntervieweeController controller;
    private Button developerButton;
    private Button baButton;
    private Button qaButton;
    private KYNDatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_interviewee);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        loadView();
        initDefaultValue();
    }

    private void loadView() {
        developerButton = (Button) findViewById(R.id.btnDeveloper);
        qaButton = (Button) findViewById(R.id.btnQA);
        baButton = (Button) findViewById(R.id.btnBA);
    }

    private void initDefaultValue() {
        controller = new KYNCategoryIntervieweeController(this);
        developerButton.setOnClickListener(controller);
        qaButton.setOnClickListener(controller);
        baButton.setOnClickListener(controller);
        database = new KYNDatabaseHelper(this);
    }
}