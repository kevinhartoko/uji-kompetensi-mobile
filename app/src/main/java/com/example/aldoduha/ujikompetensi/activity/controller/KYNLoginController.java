package com.example.aldoduha.ujikompetensi.activity.controller;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;

import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.KYNHomeActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNLoginActivity;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

/**
 * Created by aldoduha on 10/8/2017.
 */
public class KYNLoginController implements View.OnClickListener, View.OnKeyListener {
    private KYNLoginActivity activity;
    private int codeFailed;
    private KYNDatabaseHelper database;

    public KYNLoginController(KYNLoginActivity activity) {
        this.activity = activity;
        this.codeFailed = activity.getIntent().getIntExtra(KYNIntentConstant.INTENT_EXTRA_CODE, KYNIntentConstant.CODE_FAILED);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLogin:
                //function login;
                //sebelom ada service lsg start activity
                activity.doLogin();
                break;
            default:
                break;
        }
    }
    public void initDatabase(){
        this.database = KYNDatabaseHelper.getInstance(activity);
    }

    public void onDestory(){
        if(database != null)
            database.close();
    }

    public void doLogin(){
        String username = activity.getUsername().getText().toString();
        String password = activity.getPassword().getText().toString();
        if (username == null || username.equals("")) {
            activity.showAlertDialog("Gagal", "Username dan Password harus diisi");
            return;
        }

        if (password == null || password.equals("")) {
            activity.showAlertDialog("Gagal", "Username dan Password harus diisi");
            return;
        }
        activity.hideKeyBoard();
        //start service login
        startActivityForLoginSuccess();
    }

    private void startActivityForLoginSuccess(){
        Intent intent = new Intent(activity, KYNHomeActivity.class);
        activity.startActivity(intent);
    }
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        boolean result = false;
        if(keyCode == KeyEvent.KEYCODE_ENTER){
            if(event.getAction()==KeyEvent.ACTION_DOWN){
                result = true;
                //function login;
                //sebelom ada service lsg start activity
                startActivityForLoginSuccess();
            }
        }

        return result;
    }
}
