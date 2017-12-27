package com.example.aldoduha.ujikompetensi.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.aldoduha.ujikompetensi.KYNBaseActivity;
import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.controller.KYNLoginController;
import com.example.aldoduha.ujikompetensi.alertDialog.KYNConfirmationAlertDialog;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNConfirmationAlertDialogListener;

public class KYNLoginActivity extends KYNBaseActivity {
    EditText username;
    EditText password;
    Button btnLogin;
    KYNLoginActivity activity;
    KYNLoginController controller;
    KYNConfirmationAlertDialog exitConfirmationDialog;
    private InputMethodManager imm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadview();
        initDefaultValue();
    }

    @Override
    protected void onResume() {
        dismisLoadingDialog();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if(controller!=null)
            controller.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        showExitConfirmationDialog();
    }

    private void loadview(){
        username = (EditText)findViewById(R.id.edittextUsername);
        password = (EditText)findViewById(R.id.edittextPassword);
        btnLogin = (Button)findViewById(R.id.buttonLogin);
    }

    private void initDefaultValue(){
        activity = this;
        controller = new KYNLoginController(this);
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        btnLogin.setOnClickListener(controller);
        initiateSQLChiper();
    }
    protected void initiateSQLChiper() {
        AsyncTask<Void, Void, Void> asyntask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                showLoadingDialog("Persiapan Database");
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                KYNDatabaseHelper database = new KYNDatabaseHelper(KYNLoginActivity.this);
                database.close();
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                controller.initDatabase();
                dismisLoadingDialog();
                super.onPostExecute(result);
            }
        };
        asyntask.execute();
    }
    public void showExitConfirmationDialog(){
        if (exitConfirmationDialog == null) {
            exitConfirmationDialog = new KYNConfirmationAlertDialog(activity, new KYNConfirmationAlertDialogListener() {

                @Override
                public void onOK() {
                    //Exit app
                    ActivityCompat.finishAffinity(activity);
                    System.exit(0);
                }

                @Override
                public void onCancel() {
                    //Do Nothing
                }
            },"");

            exitConfirmationDialog.setDialogMessage("Apakah anda ingin keluar dari aplikasi?");
        }

        exitConfirmationDialog.show();
    }

    public void doLogin(){
        showLoadingDialog(getResources().getString(R.string.loading));
        controller.doLogin();
    }
    public void hideKeyBoard(){
        imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
    }

    public EditText getUsername() {
        return username;
    }

    public EditText getPassword() {
        return password;
    }
}
