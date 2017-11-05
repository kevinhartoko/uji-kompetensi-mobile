package com.example.aldoduha.ujikompetensi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.aldoduha.ujikompetensi.KYNBaseActivity;
import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.controller.KYNHomeController;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNConfirmationAlertDialogListener;
import com.example.aldoduha.ujikompetensi.model.KYNIntervieweeModel;
import com.example.aldoduha.ujikompetensi.model.KYNQuestionModel;
import com.example.aldoduha.ujikompetensi.model.KYNUserModel;

/**
 * Created by aldoduha on 10/14/2017.
 */

public class KYNHomeActivity extends KYNBaseActivity{
    private Activity activity;
    private KYNHomeController controller;
    private Button jawabPertanyaanButton;
    private Button listIntervieweeButton;
    private Button userManagementButton;
    private Button questionManagementButton;
    private Button templateManagementButton;
    private Button logoutButton;
    private LinearLayout intervieweeLinear;
    private LinearLayout adminLinear;
    private KYNDatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        loadView();
        initDefaultValue();
    }

    public void doLogout(){
        finish();
    }
    KYNConfirmationAlertDialogListener listener = new KYNConfirmationAlertDialogListener() {
        @Override
        public void onOK() {
            doLogout();
        }

        @Override
        public void onCancel() {

        }
    };

    @Override
    public void onBackPressed() {
        doConfirmationLogout();
    }

    public void doConfirmationLogout(){
        showConfirmationAlertDialog("Apakah anda ingin keluar?", listener);
    }

    private void loadView(){
        jawabPertanyaanButton = (Button)findViewById(R.id.btnJawabPertanyaan);
        listIntervieweeButton = (Button)findViewById(R.id.btnListInterviewee);
        userManagementButton = (Button)findViewById(R.id.btnUserManagement);
        questionManagementButton = (Button)findViewById(R.id.btnQuestionManagement);
        templateManagementButton = (Button)findViewById(R.id.btnTemplateManagement);
        logoutButton = (Button)findViewById(R.id.btnLogout);
        intervieweeLinear = (LinearLayout)findViewById(R.id.linearInterviewee);
        adminLinear = (LinearLayout)findViewById(R.id.linearAdmin);
    }

    private void initDefaultValue(){
        controller = new KYNHomeController(this);
        jawabPertanyaanButton.setOnClickListener(controller);
        listIntervieweeButton.setOnClickListener(controller);
        userManagementButton.setOnClickListener(controller);
        questionManagementButton.setOnClickListener(controller);
        templateManagementButton.setOnClickListener(controller);
        logoutButton.setOnClickListener(controller);
        database = new KYNDatabaseHelper(this);
        //insert question
        database.deleteInterviewee();
        database.deleteQuestion();
        for (int i = 1;i<=10;i++){
            KYNQuestionModel model = new KYNQuestionModel();
            model.setQuestion("Pertanyaan "+i);
            model.setAnswer1("answer 1-"+i);
            model.setAnswer2("answer 2-"+i);
            model.setAnswer3("answer 3-"+i);
            model.setAnswer4("answer 4-"+i);
            model.setKeyAnswer("answer 1-"+i);
            database.insertQuestion(model);
        }
        //insert user
        database.deleteUser();
        for (int i=1;i<=4;i++){
            KYNUserModel model = new KYNUserModel();
            model.setNama("User"+i);
            model.setUsername("Username"+i);
            model.setPassword("password"+i);
            model.setRole("admin");
            database.insertUser(model);
        }
    }
}
