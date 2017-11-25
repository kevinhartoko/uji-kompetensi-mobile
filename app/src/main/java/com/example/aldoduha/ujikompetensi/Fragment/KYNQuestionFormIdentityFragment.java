package com.example.aldoduha.ujikompetensi.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.aldoduha.ujikompetensi.Fragment.Controller.KYNQuestionIdentityController;
import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.KYNQuestionFormActivity;
import com.example.aldoduha.ujikompetensi.activity.controller.KYNQuestionFormIdentityController;
import com.example.aldoduha.ujikompetensi.alertDialog.KYNDatePickerDialog;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNConfirmationAlertDialogListener;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNDatePickerDialogListener;
import com.example.aldoduha.ujikompetensi.model.KYNIntervieweeModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by aldoduha on 10/26/2017.
 */

public class KYNQuestionFormIdentityFragment extends KYNBaseFragment {
    private KYNQuestionIdentityController controller;
    private KYNDatabaseHelper database;
    private EditText editTextNama;
    private EditText editTextEmail;
    private EditText editTextHandphone;
    private EditText editTextAddress;
    private TextView textViewDOB;
    private Button buttonLanjut;
    private KYNDatePickerDialog datePickerDialog;
    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    private KYNQuestionFormActivity activity;
    private KYNIntervieweeModel intervieweeModel;
    private RadioButton radioLakilaki;
    private RadioButton radioPerempuan;
    private TextView genderErrorTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_question_form_identity, null);
        loadview();
        initiateDefaultValue();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setValueToView();
    }

    private void loadview(){
        editTextNama = (EditText)view.findViewById(R.id.edittextNama);
        editTextEmail = (EditText)view.findViewById(R.id.edittextEmail);
        editTextHandphone = (EditText)view.findViewById(R.id.edittextHandphone);
        editTextAddress = (EditText)view.findViewById(R.id.edittextAddress);
        textViewDOB = (TextView)view.findViewById(R.id.textviewDOB);
        buttonLanjut = (Button)view.findViewById(R.id.btnLanjut);
        radioLakilaki = (RadioButton)view.findViewById(R.id.radioLakilaki);
        radioPerempuan = (RadioButton)view.findViewById(R.id.radioPerempuan);
        genderErrorTextView = (TextView)view.findViewById(R.id.genderErrorTextview);
    }
    private void initiateDefaultValue(){
        activity = (KYNQuestionFormActivity)getActivity();
        controller = new KYNQuestionIdentityController(this);
        intervieweeModel = activity.getIntervieweeModel();
        database = new KYNDatabaseHelper(activity);
        buttonLanjut.setOnClickListener(controller);
        textViewDOB.setOnClickListener(controller);
    }
    public void setValueToModel(){
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
        if(radioLakilaki.isChecked()){
            intervieweeModel.setGender("L");
        }else{
            intervieweeModel.setGender("P");
        }
        if(intervieweeModel.getId()==null) {
            Long id = database.insertInterviewee(intervieweeModel);
            intervieweeModel.setId(id);
            activity.setIntervieweeId(id);
            database.updateQuestionIntervieweeId(id);
        } else {
            database.updateInterviewee(intervieweeModel);
        }
    }

    public void setValueToView(){
        if(intervieweeModel.getNama()!=null && !intervieweeModel.getNama().equals("")){
            editTextNama.setText(intervieweeModel.getNama());
        }else{
            editTextNama.setText("");
        }
        if(intervieweeModel.getEmail()!=null && !intervieweeModel.getEmail().equals("")){
            editTextEmail.setText(intervieweeModel.getEmail());
        }else{
            editTextEmail.setText("");
        }
        if(intervieweeModel.getHandphone()!=null && !intervieweeModel.getHandphone().equals("")){
            editTextHandphone.setText(intervieweeModel.getHandphone());
        }else{
            editTextHandphone.setText("");
        }
        if(intervieweeModel.getAddress()!=null && !intervieweeModel.getAddress().equals("")){
            editTextAddress.setText(intervieweeModel.getAddress());
        }else{
            editTextAddress.setText("");
        }
        if (intervieweeModel.getDob() != null && !intervieweeModel.getDob().equals("")) {
            textViewDOB.setText(format.format(intervieweeModel.getDob()));
        }
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
        if(!radioLakilaki.isChecked() && !radioPerempuan.isChecked()){
            genderErrorTextView.setVisibility(View.VISIBLE);
            result = false;
        }else{
            genderErrorTextView.setVisibility(View.GONE);
        }
        return result;
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

    public KYNQuestionFormActivity getNewActivity() {
        return activity;
    }

    public void setActivity(KYNQuestionFormActivity activity) {
        this.activity = activity;
    }
}
