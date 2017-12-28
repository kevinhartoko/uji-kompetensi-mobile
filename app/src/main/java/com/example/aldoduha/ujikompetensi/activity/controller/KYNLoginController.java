package com.example.aldoduha.ujikompetensi.activity.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.View;

import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.KYNHomeActivity;
import com.example.aldoduha.ujikompetensi.activity.KYNLoginActivity;
import com.example.aldoduha.ujikompetensi.connection.KYNSMPUtilities;
import com.example.aldoduha.ujikompetensi.connection.api.listener.KYNServiceConnection;
import com.example.aldoduha.ujikompetensi.model.KYNUserModel;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;
import com.j256.ormlite.stmt.query.In;

/**
 * Created by aldoduha on 10/8/2017.
 */
public class KYNLoginController implements View.OnClickListener, View.OnKeyListener {
    private KYNLoginActivity activity;
    private int codeFailed;
    private KYNDatabaseHelper database;

    private BroadcastReceiver localBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!intent.getCategories().contains(KYNIntentConstant.CATEGORY_LOGIN)) {
                return;
            }

            Bundle bundle = intent.getExtras();
            if (bundle == null) {
                return;
            }

            int code = bundle.getInt(KYNIntentConstant.BUNDLE_KEY_CODE);
            String message = bundle.getString(KYNIntentConstant.BUNDLE_KEY_MESSAGE);
            String action = intent.getAction();
            if (action.equals(KYNIntentConstant.ACTION_LOGIN)) {
                switch (code) {
                    case KYNIntentConstant.CODE_LOGIN_SUCCESS:
                        activity.dismisLoadingDialog();
                        startActivityForLoginSuccess();
                        break;
                    case KYNIntentConstant.CODE_LOGIN_FAILED:
                        activity.dismisLoadingDialog();
                        if(message!=null && !message.trim().equals("null")){
                            activity.showAlertDialog("Error", message.trim());
                        }else{
                            activity.showAlertDialog("Error", "Gagal Login");
                        }
                        break;
                    default:
                        activity.dismisLoadingDialog();
                        if(message!=null && !message.trim().equals("null")){
                            activity.showAlertDialog("Error", message.trim());
                        }else{
                            activity.showAlertDialog("Error", "Gagal Login");
                        }
                        break;
                }
            }
        }
    };

    public KYNLoginController(KYNLoginActivity activity) {
        this.activity = activity;
        this.codeFailed = activity.getIntent().getIntExtra(KYNIntentConstant.INTENT_EXTRA_CODE, KYNIntentConstant.CODE_FAILED);
        registerLocalBroadCastReceiver();
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

    public void onDestroy(){
        unregisterLocalBroadCastReceiver();
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
        if(KYNSMPUtilities.isConnectServer){
            login();
        }else {
            KYNUserModel session = new KYNUserModel();
            session.setUsername(username);
            session.setPassword(password);
            session.setNama("hehe");
            session.setToken("adasda");
            session.setRole("admin");
            database.deleteSession();
            database.insertSession(session);
            startActivityForLoginSuccess();
        }
    }

    private void login(){
        String username = activity.getUsername().getText().toString().trim();
        String password = activity.getPassword().getText().toString().trim();
        KYNUserModel userModel = new KYNUserModel();
        userModel.setUsername(username);
        userModel.setPassword(password);
        Intent intent = new Intent(activity, KYNServiceConnection.class);
        intent.setAction(KYNIntentConstant.ACTION_LOGIN);
        intent.addCategory(KYNIntentConstant.CATEGORY_LOGIN);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_DATA, userModel);
        activity.startService(intent);
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
    public void registerLocalBroadCastReceiver(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(KYNIntentConstant.ACTION_LOGIN);
        intentFilter.addCategory(KYNIntentConstant.CATEGORY_LOGIN);
        LocalBroadcastManager.getInstance(activity.getApplicationContext()).registerReceiver(localBroadCastReceiver, intentFilter);
    }

    public void unregisterLocalBroadCastReceiver(){
        LocalBroadcastManager.getInstance(activity.getApplicationContext()).unregisterReceiver(localBroadCastReceiver);
    }
}
