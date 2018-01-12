package com.example.aldoduha.ujikompetensi.activity;

import android.app.Activity;
import android.content.Intent;
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
import com.example.aldoduha.ujikompetensi.connection.KYNSMPUtilities;
import com.example.aldoduha.ujikompetensi.connection.api.listener.KYNServiceConnection;
import com.example.aldoduha.ujikompetensi.model.KYNIntervieweeModel;
import com.example.aldoduha.ujikompetensi.model.KYNQuestionModel;
import com.example.aldoduha.ujikompetensi.model.KYNTemplateModel;
import com.example.aldoduha.ujikompetensi.model.KYNUserModel;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

/**
 * Created by aldoduha on 10/14/2017.
 */

public class KYNHomeActivity extends KYNBaseActivity {
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
    KYNUserModel session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        loadView();
        initDefaultValue();
    }

    @Override
    protected void onPause() {
        if(controller!=null)
            controller.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(controller!=null)
            controller.onResume();
    }

    public void doLogout() {
        if (KYNSMPUtilities.isConnectServer) {
            showLoadingDialog(activity.getResources().getString(R.string.loading));
            KYNUserModel session = database.getSession();
            Intent intent = new Intent(this, KYNServiceConnection.class);
            intent.putExtra(KYNIntentConstant.INTENT_EXTRA_DATA, session);
            intent.setAction(KYNIntentConstant.ACTION_LOGOUT);
            intent.addCategory(KYNIntentConstant.CATEGORY_LOGOUT);
            activity.startService(intent);
        } else{
            finish();
        }
    }

    public void getUserList(){
        showLoadingDialog(activity.getResources().getString(R.string.loading));
        Intent intent = new Intent(this, KYNServiceConnection.class);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_DATA, session);
        intent.setAction(KYNIntentConstant.ACTION_USER_LIST);
        intent.addCategory(KYNIntentConstant.CATEGORY_USER_LIST);
        activity.startService(intent);
    }

    public void getQuestionList(){
        showLoadingDialog(activity.getResources().getString(R.string.loading));
        Intent intent = new Intent(this, KYNServiceConnection.class);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_DATA, session);
        intent.setAction(KYNIntentConstant.ACTION_QUESTION_LIST);
        intent.addCategory(KYNIntentConstant.CATEGORY_QUESTION_LIST);
        activity.startService(intent);
    }

    public void getTemplateList(){
        showLoadingDialog(activity.getResources().getString(R.string.loading));
        Intent intent = new Intent(this, KYNServiceConnection.class);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_DATA, session);
        intent.setAction(KYNIntentConstant.ACTION_TEMPLATE_LIST);
        intent.addCategory(KYNIntentConstant.CATEGORY_TEMPLATE_LIST);
        activity.startService(intent);
    }

    public void getIntervieweeList(){
        showLoadingDialog(activity.getResources().getString(R.string.loading));
        Intent intent = new Intent(this, KYNServiceConnection.class);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_DATA, session);
        intent.setAction(KYNIntentConstant.ACTION_INTERVIEWEE_LIST);
        intent.addCategory(KYNIntentConstant.CATEGORY_INTERVIEWEE_LIST);
        activity.startService(intent);
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

    public void doConfirmationLogout() {
        showConfirmationAlertDialog("Are you sure to go back?", listener);
    }

    private void loadView() {
        jawabPertanyaanButton = (Button) findViewById(R.id.btnJawabPertanyaan);
        listIntervieweeButton = (Button) findViewById(R.id.btnListInterviewee);
        userManagementButton = (Button) findViewById(R.id.btnUserManagement);
        questionManagementButton = (Button) findViewById(R.id.btnQuestionManagement);
        templateManagementButton = (Button) findViewById(R.id.btnTemplateManagement);
        logoutButton = (Button) findViewById(R.id.btnLogout);
        intervieweeLinear = (LinearLayout) findViewById(R.id.linearInterviewee);
        adminLinear = (LinearLayout) findViewById(R.id.linearAdmin);
    }

    private void initDefaultValue() {
        database = new KYNDatabaseHelper(this);
        session = database.getSession();
        controller = new KYNHomeController(this);
        jawabPertanyaanButton.setOnClickListener(controller);
        listIntervieweeButton.setOnClickListener(controller);
        userManagementButton.setOnClickListener(controller);
        questionManagementButton.setOnClickListener(controller);
        templateManagementButton.setOnClickListener(controller);
        logoutButton.setOnClickListener(controller);

        if(!KYNSMPUtilities.isConnectServer) {
            insertData();
        }
    }

    public void insertData(){
        database.deleteInterviewee();
        database.deleteQuestion();
        //insert question
        for (int i = 1; i <= 10; i++) {
            KYNQuestionModel model = new KYNQuestionModel();
            model.setQuestion("Pertanyaan " + i);
            model.setAnswer1("answer 1-" + i);
            model.setAnswer2("answer 2-" + i);
            model.setAnswer3("answer 3-" + i);
            model.setAnswer4("answer 4-" + i);
            model.setKeyAnswer("answer 1-" + i);
            model.setBobot(i);
            model.setName("code" + i);
            database.insertQuestion(model);
        }
        //insert user
        database.deleteUser();
        for (int i = 1; i <= 4; i++) {
            KYNUserModel model = new KYNUserModel();
            model.setNama("User" + i);
            model.setUsername("Username" + i);
            model.setPassword("password" + i);
            model.setRole("admin");
            database.insertUser(model);
        }
        //insert template
        database.deleteTemplate();
        for (int i = 1; i <= 3; i++) {
            KYNTemplateModel model = new KYNTemplateModel();
            model.setNama("Template" + i);
            model.setJumlahSoal(1 + i);
            database.insertTemplate(model);
        }
    }

    public KYNUserModel getSession() {
        return session;
    }

    public void setSession(KYNUserModel session) {
        this.session = session;
    }

    public LinearLayout getIntervieweeLinear() {
        return intervieweeLinear;
    }

    public void setIntervieweeLinear(LinearLayout intervieweeLinear) {
        this.intervieweeLinear = intervieweeLinear;
    }

    public LinearLayout getAdminLinear() {
        return adminLinear;
    }

    public void setAdminLinear(LinearLayout adminLinear) {
        this.adminLinear = adminLinear;
    }

    public Button getListIntervieweeButton() {
        return listIntervieweeButton;
    }

    public void setListIntervieweeButton(Button listIntervieweeButton) {
        this.listIntervieweeButton = listIntervieweeButton;
    }
}
