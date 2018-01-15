package com.example.aldoduha.ujikompetensi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.aldoduha.ujikompetensi.KYNBaseActivity;
import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.controller.KYNUserDetailController;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNConfirmationAlertDialogListener;
import com.example.aldoduha.ujikompetensi.connection.KYNSMPUtilities;
import com.example.aldoduha.ujikompetensi.connection.api.listener.KYNServiceConnection;
import com.example.aldoduha.ujikompetensi.model.KYNQuestionModel;
import com.example.aldoduha.ujikompetensi.model.KYNUserModel;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aldoduha on 11/5/2017.
 */

public class KYNUserDetailActivity extends KYNBaseActivity{
    private KYNUserDetailController controller;
    private EditText editTextNama;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Spinner spinnerRole;
    private Button buttonLanjut;
    private Button buttonHapus;
    private Button buttonKembali;
    private KYNDatabaseHelper database;
    private Long userId;
    private KYNUserModel userModel;
    private ArrayAdapter adapter;
    private LinearLayout linearPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        userId = getIntent().getLongExtra("userId", 0);
        loadview();
        initDefaultValue();
    }
    @Override
    protected void onDestroy() {
        if(controller!=null)
            controller.onDestroy();
        super.onDestroy();
    }

    private void loadview(){
        editTextNama = (EditText)findViewById(R.id.edittextNama);
        editTextUsername = (EditText)findViewById(R.id.edittextUsername);
        editTextPassword = (EditText)findViewById(R.id.edittextPassword);
        spinnerRole = (Spinner)findViewById(R.id.spinnerRole); 
        buttonLanjut = (Button)findViewById(R.id.btnLanjut);
        buttonKembali = (Button)findViewById(R.id.btnKembali);
        buttonHapus = (Button)findViewById(R.id.btnHapus);
        linearPassword = (LinearLayout)findViewById(R.id.linearPassword);
    }

    private void initDefaultValue(){
        database = new KYNDatabaseHelper(this);
        if(userId!=null && userId!=0){
            userModel = database.getUser(userId);
            linearPassword.setVisibility(View.GONE);
            buttonHapus.setVisibility(View.VISIBLE);
            editTextUsername.setEnabled(false);
            editTextUsername.setBackgroundColor(getResources().getColor(R.color.light_gray));
        }else{
            editTextUsername.setEnabled(true);
            userModel = new KYNUserModel();
            buttonHapus.setVisibility(View.GONE);
            linearPassword.setVisibility(View.VISIBLE);
        }
        controller = new KYNUserDetailController(this);
        buttonLanjut.setOnClickListener(controller);
        buttonHapus.setOnClickListener(controller);
        buttonKembali.setOnClickListener(controller);
    }

    public void setValueToModel(){
        userModel.setNama(editTextNama.getText().toString());
        userModel.setUsername(editTextUsername.getText().toString());
        if(linearPassword.getVisibility()==View.VISIBLE) {
            userModel.setPassword(editTextPassword.getText().toString());
        }
        userModel.setRole(spinnerRole.getSelectedItem().toString());
        if(userId==null || userId==0){
            userId = database.insertUser(userModel);
        }else{
            database.updateUser(userModel);
        }
    }

    public void setValuesToView(){
        if (userModel.getNama()!=null && !userModel.getNama().equals("")){
            editTextNama.setText(userModel.getNama());
        }
        if(userModel.getUsername()!=null && !userModel.getUsername().equals("")){
            editTextUsername.setText(userModel.getUsername());
        }
        if(userModel.getRole()!=null && !userModel.getRole().equals("")){
            int position = 0;
            for(int i=0;i<spinnerRole.getCount();i++){
                if(userModel.getRole().equalsIgnoreCase(spinnerRole.getItemAtPosition(i).toString()))
                    position=i;
            }
            spinnerRole.setSelection(position);
        }
    }

    public boolean validate(){
        boolean result = true;
        String nama = editTextNama.getText().toString();
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        editTextNama.setError(null);
        editTextUsername.setError(null);
        editTextPassword.setError(null);
        if (nama==null||nama.equals("")){
            editTextNama.setError(Html.fromHtml("Name must be filled"));
            result = false;
        }
        if(username==null||username.equals("")){
            editTextUsername.setError(Html.fromHtml("Username must be filled"));
            result =false;
        }
        if(linearPassword.getVisibility()==View.VISIBLE) {
            if (password == null || password.equals("")) {
                editTextPassword.setError(Html.fromHtml("Password must be filled"));
                result = false;
            }
        }
        return result;
    }

    public void initiateRole(){
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, KYNIntentConstant.role);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);
    }

    public void submitUser(){
        showLoadingDialog(getResources().getString(R.string.loading));
        KYNUserModel session = database.getSession();
        Intent intent = new Intent(this, KYNServiceConnection.class);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_DATA, userModel);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_USERNAME, session.getUsername());
        intent.setAction(KYNIntentConstant.ACTION_SUBMIT_USER);
        intent.addCategory(KYNIntentConstant.CATEGORY_SUBMIT_USER);
        startService(intent);
    }

    public void submitUser(KYNUserModel userModel){
        showLoadingDialog(getResources().getString(R.string.loading));
        KYNUserModel session = database.getSession();
        Intent intent = new Intent(this, KYNServiceConnection.class);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_DATA, userModel);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_USERNAME, session.getUsername());
        intent.setAction(KYNIntentConstant.ACTION_SUBMIT_USER);
        intent.addCategory(KYNIntentConstant.CATEGORY_SUBMIT_USER);
        startService(intent);
    }

    public void deleteUser(KYNUserModel userModel){
        showLoadingDialog(getResources().getString(R.string.loading));
        KYNUserModel session = database.getSession();
        Intent intent = new Intent(this, KYNServiceConnection.class);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_DATA, userModel);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_USERNAME, session.getUsername());
        intent.setAction(KYNIntentConstant.ACTION_DELETE_USER);
        intent.addCategory(KYNIntentConstant.CATEGORY_DELETE_USER);
        startService(intent);
    }

    KYNConfirmationAlertDialogListener listener = new KYNConfirmationAlertDialogListener() {
        @Override
        public void onOK() {
            finish();
        }

        @Override
        public void onCancel() {

        }
    };

    @Override
    public void onBackPressed() {
        showConfirmationAlertDialog("Are you sure to go back?", listener);
    }

    KYNConfirmationAlertDialogListener listenerHapus = new KYNConfirmationAlertDialogListener() {
        @Override
        public void onOK() {
            database.deleteUser(userId);
            if(KYNSMPUtilities.isConnectServer){
                if(userModel.getUsername().equals(KYNIntentConstant.USERNAME)){
                    showAlertDialog("Error", "This user is being used");
                }else {
                    KYNUserModel model = new KYNUserModel();
                    model.setId(userModel.getId());
                    model.setServerId(userModel.getServerId());
                    deleteUser(model);
                }
            }else {
                setResult(KYNIntentConstant.RESULT_CODE_USER_DETAIL);
                finish();
            }
        }

        @Override
        public void onCancel() {

        }
    };

    public void onButtonHapusClicked(){
        showConfirmationAlertDialog("Are you sure to delete this user?", listenerHapus);
    }
}
