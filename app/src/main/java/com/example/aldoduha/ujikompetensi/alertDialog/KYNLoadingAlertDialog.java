package com.example.aldoduha.ujikompetensi.alertDialog;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.example.aldoduha.ujikompetensi.R;

/**
 * Created by aldoduha on 10/8/2017.
 */

public class KYNLoadingAlertDialog extends AlertDialog {
    private TextView loadingText;
    private String titleText;

    public KYNLoadingAlertDialog(Context context, String titleText) {
        super(context);
        this.titleText = titleText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_alert_dialog);
        loadView();
        initiateDefaultValue();
    }

    private void loadView(){
        loadingText = (TextView)findViewById(R.id.loading_text);
    }

    private void initiateDefaultValue(){
        loadingText.setText(titleText);
    }

    public void setLoadingText(String text){
        titleText = text;
        if (loadingText != null) {
            loadingText.setText(text);
        }
    }
}
