package com.example.aldoduha.ujikompetensi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.example.aldoduha.ujikompetensi.activity.KYNLoginActivity;
import com.example.aldoduha.ujikompetensi.alertDialog.KYNConfirmationAlertDialog;
import com.example.aldoduha.ujikompetensi.alertDialog.KYNInfoAlertDialog;
import com.example.aldoduha.ujikompetensi.alertDialog.KYNLoadingAlertDialog;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNConfirmationAlertDialogListener;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNInfoAlertDialogListener;
import com.example.aldoduha.ujikompetensi.connection.KYNConnectionManager;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

import net.sqlcipher.database.SQLiteDatabase;

/**
 * Created by aldoduha on 10/8/2017.
 */

public class KYNBaseActivity extends FragmentActivity {
    private KYNLoadingAlertDialog loadingAlertDialog;
    private KYNConfirmationAlertDialog confirmationAlertDialog;
    protected KYNInfoAlertDialog errorTokenAlertDialog;
    protected KYNInfoAlertDialog infoAlertDialog;
    protected boolean isShowLoading;
    protected boolean isShowAlert;
    protected String title = "";
    protected String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        System.loadLibrary("stlport_shared");
        SQLiteDatabase.loadLibs(getApplicationContext());
        super.onCreate(savedInstanceState);
        KYNConnectionManager conMan= new KYNConnectionManager(this);
        if (savedInstanceState != null) {
            isShowLoading = (boolean) savedInstanceState.getBoolean(KYNIntentConstant.BUNDLE_KEY_LOADING_SHOW);
            isShowAlert = (boolean) savedInstanceState.getBoolean(KYNIntentConstant.BUNDLE_KEY_ALERT_SHOW);
            message = (String) savedInstanceState.getString(KYNIntentConstant.BUNDLE_KEY_MESSAGE);
            title = (String) savedInstanceState.getString(KYNIntentConstant.BUNDLE_KEY_TITLE);
            if (isShowLoading) {
                if (isShowAlert) {
                    dismisLoadingDialog();
                    showAlertDialog(title, message);
                } else {
                    showLoadingDialog(message);
                }
            } else {
                dismisLoadingDialog();
            }

            if (isShowAlert) {
                showAlertDialog(title, message);
            }
        }
    }

    @Override
    public void onLowMemory() {
        Toast.makeText(this, "Your device is running low. Please Restart.", Toast.LENGTH_LONG);
        super.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        dismisLoadingDialog();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KYNIntentConstant.BUNDLE_KEY_ALERT_SHOW, isShowAlert);
        outState.putBoolean(KYNIntentConstant.BUNDLE_KEY_LOADING_SHOW, isShowLoading);
        outState.putString(KYNIntentConstant.BUNDLE_KEY_MESSAGE, message);
        outState.putString(KYNIntentConstant.BUNDLE_KEY_TITLE, title);
        super.onSaveInstanceState(outState);
    }

    public void showLoadingDialog(String text){
        dismisLoadingDialog();
        loadingAlertDialog = new KYNLoadingAlertDialog(this, text);
        loadingAlertDialog.setCancelable(false);
        loadingAlertDialog.setCanceledOnTouchOutside(false);
        if (message == null || message.equals("")) {
            if (text.equals("Persiapan Database")){
                message = "Persiapan Database";
            }
        } else {
            if (!text.equals("Persiapan Database")) {
                message = text;
            }
        }

        loadingAlertDialog.setLoadingText(text);
        if (loadingAlertDialog.isShowing()) {
            isShowLoading = false;
            loadingAlertDialog.dismiss();
        } else {
            isShowLoading = true;
            loadingAlertDialog.show();
        }
    }
    public void dismisLoadingDialog() {
        isShowLoading = false;
        if (loadingAlertDialog != null) {
            if (loadingAlertDialog.isShowing()) {
                loadingAlertDialog.dismiss();
            }
        } else {
            loadingAlertDialog = new KYNLoadingAlertDialog(this, message);
            loadingAlertDialog.dismiss();
        }
    }
    public void showConfirmationAlertDialog(String message, KYNConfirmationAlertDialogListener listener) {
        confirmationAlertDialog = new KYNConfirmationAlertDialog(this, listener, message);
        confirmationAlertDialog.show();
    }

    public void showAlertDialog(String title, String message) {
        dismisLoadingDialog();
        this.title = title;
        this.message = message;
        isShowAlert = true;
        infoAlertDialog = new KYNInfoAlertDialog(this, title, message);
        infoAlertDialog.setListener(new KYNInfoAlertDialogListener() {

            @Override
            public void onOk() {
                isShowAlert = false;
            }
        });
        if(!isFinishing())
            infoAlertDialog.show();
    }

    public void showAlertDialog(String title, String message, KYNInfoAlertDialogListener listener, boolean score) {
        dismisLoadingDialog();
        this.title = title;
        this.message = message;
        isShowAlert = true;
        infoAlertDialog = new KYNInfoAlertDialog(this, title, message, score);
        infoAlertDialog.setListener(listener);
        infoAlertDialog.show();
    }
    public void showErrorTokenDialog() {
        if (errorTokenAlertDialog == null) {
            errorTokenAlertDialog = new KYNInfoAlertDialog(this, "Gagal", "Akun anda digunakan di perangkat lain, Harap login kembali");
            errorTokenAlertDialog.setCancelable(false);
            errorTokenAlertDialog.setCanceledOnTouchOutside(false);
        }
        errorTokenAlertDialog.setListener(new KYNInfoAlertDialogListener() {
            @Override
            public void onOk() {
                Intent intent = new Intent(KYNBaseActivity.this, KYNLoginActivity.class);
                intent.putExtra(KYNIntentConstant.INTENT_EXTRA_CODE, KYNIntentConstant.CODE_FAILED_TOKEN);
                startActivity(intent);
            }
        });
        if(!isFinishing())
            errorTokenAlertDialog.show();
    }
}
