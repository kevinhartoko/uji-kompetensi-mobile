package com.example.aldoduha.ujikompetensi.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.aldoduha.ujikompetensi.KYNBaseActivity;
import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.controller.KYNUserDetailController;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNConfirmationAlertDialogListener;
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
    }

    private void initDefaultValue(){
        database = new KYNDatabaseHelper(this);
        if(userId!=null && userId!=0){
            userModel = database.getUser(userId);
        }else{
            userModel = new KYNUserModel();
        }
        controller = new KYNUserDetailController(this);
        buttonLanjut.setOnClickListener(controller);
        buttonHapus.setOnClickListener(controller);
        buttonKembali.setOnClickListener(controller);
    }

    public void setValueToModel(){
        userModel.setNama(editTextNama.getText().toString());
        userModel.setUsername(editTextUsername.getText().toString());
        userModel.setPassword(editTextPassword.getText().toString());
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
        if(userModel.getPassword()!=null && !userModel.getPassword().equals("")){
            editTextPassword.setText(userModel.getPassword());
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
            editTextNama.setError(Html.fromHtml("Nama Tidak Boleh Kosong"));
            result = false;
        }
        if(username==null||username.equals("")){
            editTextUsername.setError(Html.fromHtml("Username Tidak Boleh Kosong"));
            result =false;
        }
        if(password==null||password.equals("")){
            editTextPassword.setError(Html.fromHtml("Password Tidak Boleh Kosong"));
            result =false;
        }
        return result;
    }

    public void initiateRole(){
        List<String> listRole = new ArrayList<>();
        listRole.add("Interviewee");
        listRole.add("Interviewer");
        listRole.add("Admin");
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listRole);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);
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
        showConfirmationAlertDialog("Apakah anda ingin keluar?", listener);
    }

    KYNConfirmationAlertDialogListener listenerHapus = new KYNConfirmationAlertDialogListener() {
        @Override
        public void onOK() {
            database.deleteUser(userId);
            setResult(KYNIntentConstant.RESULT_CODE_USER_DETAIL);
            finish();
        }

        @Override
        public void onCancel() {

        }
    };

    public void onButtonHapusClicked(){
        showConfirmationAlertDialog("Apakah anda yakin ingin menghapus user ini?", listenerHapus);
    }
}
