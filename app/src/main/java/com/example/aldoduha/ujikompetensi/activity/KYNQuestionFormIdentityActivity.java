package com.example.aldoduha.ujikompetensi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aldoduha.ujikompetensi.KYNBaseActivity;
import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.controller.KYNQuestionFormIdentityController;
import com.example.aldoduha.ujikompetensi.alertDialog.KYNDatePickerDialog;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNConfirmationAlertDialogListener;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNDatePickerDialogListener;
import com.example.aldoduha.ujikompetensi.model.KYNIntervieweeModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by aldoduha on 10/15/2017.
 */

public class KYNQuestionFormIdentityActivity extends KYNBaseActivity {
    private Activity activity;
    private KYNQuestionFormIdentityController controller;
    private EditText editTextNama;
    private EditText editTextEmail;
    private EditText editTextHandphone;
    private EditText editTextAddress;
    private TextView textViewDOB;
    private Button buttonLanjut;
    private Long intervieweeId;
    private KYNDatePickerDialog datePickerDialog;
    private KYNDatabaseHelper database;
    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_form_identity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        loadview();
        initDefaultValue();
    }

    @Override
    protected void onDestroy() {
        database.deleteInterviewee();
        database.updateQuestionIntervieweeId(null);
        super.onDestroy();
    }

    private void loadview(){
        editTextNama = (EditText)findViewById(R.id.edittextNama);
        editTextEmail = (EditText)findViewById(R.id.edittextEmail);
        editTextHandphone = (EditText)findViewById(R.id.edittextHandphone);
        editTextAddress = (EditText)findViewById(R.id.edittextAddress);
        textViewDOB = (TextView)findViewById(R.id.textviewDOB);
        buttonLanjut = (Button)findViewById(R.id.btnLanjut);
    }
    private void initDefaultValue(){
        controller = new KYNQuestionFormIdentityController(this);
        database = new KYNDatabaseHelper(this);
        buttonLanjut.setOnClickListener(controller);
        textViewDOB.setOnClickListener(controller);
    }
    public void setValueToModel(){
        KYNIntervieweeModel intervieweeModel = new KYNIntervieweeModel();
        intervieweeModel.setNama(editTextNama.getText().toString());
        intervieweeModel.setEmail(editTextEmail.getText().toString());
        intervieweeModel.setAddress(editTextAddress.getText().toString());
        intervieweeModel.setHandphone(editTextHandphone.getText().toString());
        if (!textViewDOB.getText().toString().isEmpty() && !textViewDOB.getText().toString().trim().equals("")) {
            try {
                intervieweeModel.setDob(format.parse(textViewDOB.getText().toString().trim()));
            } catch (ParseException e) {

            }
        }
        if(intervieweeId==null) {
            intervieweeId = database.insertInterviewee(intervieweeModel);
            database.updateQuestionIntervieweeId(intervieweeId);
        } else {
            intervieweeModel.setId(intervieweeId);
            database.updateInterviewee(intervieweeModel);
        }
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

    public boolean validate(){
        boolean result = true;
        String nama = editTextNama.getText().toString();
        String email = editTextEmail.getText().toString();
        String handphone = editTextHandphone.getText().toString();
        String address = editTextAddress.getText().toString();
        String dob = textViewDOB.getText().toString();
        editTextNama.setError(null);
        editTextEmail.setError(null);
        editTextHandphone.setError(null);
        editTextAddress.setError(null);
        textViewDOB.setError(null);
        if(nama==null||nama.equals("")){
            editTextNama.setError(Html.fromHtml("Nama Tidak Boleh Kosong"));
            result=false;
        }
        if(email==null||email.equals("")){
            editTextEmail.setError(Html.fromHtml("Email Tidak Boleh Kosong"));
            result=false;
        }
        if(handphone==null||handphone.equals("")){
            editTextHandphone.setError(Html.fromHtml("Handphone Tidak Boleh Kosong"));
            result=false;
        }
        if(address==null||address.equals("")){
            editTextAddress.setError(Html.fromHtml("Address Tidak Boleh Kosong"));
            result=false;
        }
        if(dob==null||dob.equals("")){
            textViewDOB.setError(Html.fromHtml("DOB Tidak Boleh Kosong"));
            result=false;
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    activity.finish();
                }
                break;
            default:
                break;
        }
    }

    public void showDialog(KYNDatePickerDialogListener listener, String headerTitle, String date){
        datePickerDialog = new KYNDatePickerDialog(activity, listener, headerTitle, date);
        datePickerDialog.setCanceledOnTouchOutside(false);
        datePickerDialog.show();
    }

    public void dismissDialog(){
        datePickerDialog.dismiss();
    }

    public String getDateValue(){
        if (datePickerDialog == null) {
            return null;
        }
        return datePickerDialog.getDate();
    }

    public EditText getEditTextNama() {
        return editTextNama;
    }

    public EditText getEditTextEmail() {
        return editTextEmail;
    }

    public EditText getEditTextHandphone() {
        return editTextHandphone;
    }

    public EditText getEditTextAddress() {
        return editTextAddress;
    }

    public TextView getTextViewDOB() {
        return textViewDOB;
    }
}
