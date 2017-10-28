package com.example.aldoduha.ujikompetensi.alertDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNInfoAlertDialogListener;

/**
 * Created by aldoduha on 10/9/2017.
 */

public class KYNInfoAlertDialog extends AlertDialog implements View.OnClickListener {
    private String title;
    private String message;
    private TextView titleTextView;
    private TextView messageTextView;
    private Button okButton;
    private KYNInfoAlertDialogListener listener;

    public KYNInfoAlertDialog(Context context, String title, String message) {
        super(context);
        this.title = title;
        this.message = message;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_alert_dialog);

        loadView();
        initiateDefaultValue();
    }

    @Override
    public void onClick(View v) {
        this.dismiss();
        if(listener!=null)
            listener.onOk();
    }

    private void loadView(){
        titleTextView = (TextView)findViewById(R.id.alert_info_title);
        messageTextView = (TextView)findViewById(R.id.alert_info_message);
        okButton = (Button)findViewById(R.id.alert_info_button_ok);
    }

    private void initiateDefaultValue(){
        titleTextView.setText(title);
        messageTextView.setText(message);
        okButton.setOnClickListener(this);
    }

    public void setListener(KYNInfoAlertDialogListener listener) {
        this.listener = listener;
    }
}
