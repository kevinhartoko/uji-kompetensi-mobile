package com.example.aldoduha.ujikompetensi.alertDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNConfirmationAlertDialogListener;
import com.example.aldoduha.ujikompetensi.R;

/**
 * Created by aldoduha on 10/8/2017.
 */

public class KYNConfirmationAlertDialog extends AlertDialog implements View.OnClickListener {
    private Button cancelButton;
    private Button okButton;
    private TextView messageTextView;
    private String message = "";
    private KYNConfirmationAlertDialogListener listener;

    public KYNConfirmationAlertDialog(Context context, KYNConfirmationAlertDialogListener listener, String message) {
        super(context);
        this.listener = listener;
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation_alert_dialog);

        loadView();
        initDefaultValue();
    }

    private void loadView() {
        okButton = (Button)findViewById(R.id.ya_button);
        cancelButton = (Button)findViewById(R.id.tidak_button);
        messageTextView = (TextView)findViewById(R.id.message_textview);
    }

    private void initDefaultValue() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        okButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        messageTextView.setText(message);
    }

    public void setDialogMessage(String message){
        this.message = message;

        if (messageTextView != null) {
            messageTextView.setText(message);
        }
    }

    public void setListener(KYNConfirmationAlertDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        this.dismiss();

        switch (v.getId()) {
            case R.id.ya_button:
                listener.onOK();
                dismiss();
                break;
            case R.id.tidak_button:
                listener.onCancel();
                dismiss();
                break;
            default:
                break;
        }
    }
}
