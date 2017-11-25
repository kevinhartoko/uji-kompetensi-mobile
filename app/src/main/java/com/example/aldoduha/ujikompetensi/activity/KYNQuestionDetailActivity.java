package com.example.aldoduha.ujikompetensi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aldoduha.ujikompetensi.KYNBaseActivity;
import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.controller.KYNQuestionDetailController;
import com.example.aldoduha.ujikompetensi.activity.controller.KYNQuestionFormIdentityController;
import com.example.aldoduha.ujikompetensi.alertDialog.KYNDatePickerDialog;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNConfirmationAlertDialogListener;
import com.example.aldoduha.ujikompetensi.model.KYNQuestionModel;

import java.text.SimpleDateFormat;

/**
 * Created by aldoduha on 11/12/2017.
 */

public class KYNQuestionDetailActivity extends KYNBaseActivity {
    private Activity activity;
    private KYNQuestionDetailController controller;
    private EditText editTextKode;
    private EditText editTextPertanyaan;
    private EditText editTextJawaban1;
    private EditText editTextJawaban2;
    private EditText editTextJawaban3;
    private EditText editTextJawaban4;
    private EditText editTextKunciJawaban;
    private EditText editTextBobot;
    private Button buttonLanjut;
    private KYNDatabaseHelper database;
    private Long questionId;
    private KYNQuestionModel questionModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        questionId = getIntent().getLongExtra("questionId", 0);
        loadview();
        initDefaultValue();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void loadview(){
        editTextKode = (EditText)findViewById(R.id.edittextKode);
        editTextPertanyaan = (EditText)findViewById(R.id.edittextPertanyaan);
        editTextJawaban1 = (EditText)findViewById(R.id.edittextJawaban1);
        editTextJawaban2 = (EditText)findViewById(R.id.edittextJawaban2);
        editTextJawaban3 = (EditText)findViewById(R.id.edittextJawaban3);
        editTextJawaban4 = (EditText)findViewById(R.id.edittextJawaban4);
        editTextKunciJawaban = (EditText)findViewById(R.id.edittextKunciJawaban);
        editTextBobot = (EditText)findViewById(R.id.edittextBobot);
        buttonLanjut = (Button)findViewById(R.id.btnLanjut);
    }

    private void initDefaultValue(){
        database = new KYNDatabaseHelper(this);
        if(questionId!=null && questionId!=0){
            questionModel = database.getQuestion(questionId);
        }else{
            questionModel = new KYNQuestionModel();
        }
        controller = new KYNQuestionDetailController(this);
        buttonLanjut.setOnClickListener(controller);
    }

    public void setValueToModel(){
        questionModel.setName(editTextKode.getText().toString());
        questionModel.setQuestion(editTextPertanyaan.getText().toString());
        questionModel.setAnswer1(editTextJawaban1.getText().toString());
        questionModel.setAnswer2(editTextJawaban2.getText().toString());
        questionModel.setAnswer3(editTextJawaban3.getText().toString());
        questionModel.setAnswer4(editTextJawaban4.getText().toString());
        questionModel.setKeyAnswer(editTextKunciJawaban.getText().toString());
        questionModel.setBobot(Integer.parseInt(editTextBobot.getText().toString()));
        if(questionId==null || questionId==0){
            questionId = database.insertQuestion(questionModel);
        }else{
            database.updateQuestion(questionModel);
        }
    }

    public void setValuesToView(){
        if (questionModel.getName()!=null){
            editTextKode.setText(questionModel.getName());
        }
        if(questionModel.getQuestion()!=null){
            editTextPertanyaan.setText(questionModel.getQuestion());
        }
        if(questionModel.getAnswer1()!=null){
            editTextJawaban1.setText(questionModel.getAnswer1());
        }
        if(questionModel.getAnswer2()!=null){
            editTextJawaban2.setText(questionModel.getAnswer2());
        }
        if(questionModel.getAnswer3()!=null){
            editTextJawaban3.setText(questionModel.getAnswer3());
        }
        if(questionModel.getAnswer4()!=null){
            editTextJawaban4.setText(questionModel.getAnswer4());
        }
        if(questionModel.getKeyAnswer()!=null){
            editTextKunciJawaban.setText(questionModel.getKeyAnswer());
        }
        if(questionModel.getBobot()!=0){
            editTextBobot.setText(questionModel.getBobot()+"");
        }
    }

    public boolean validate(){
        boolean result = true;
        String kode = editTextKode.getText().toString();
        String pertanyaan = editTextPertanyaan.getText().toString();
        String jawaban1 = editTextJawaban1.getText().toString();
        String jawaban2 = editTextJawaban2.getText().toString();
        String jawaban3 = editTextJawaban3.getText().toString();
        String jawaban4 = editTextJawaban4.getText().toString();
        String kunciJawaban = editTextKunciJawaban.getText().toString();
        String bobot = editTextBobot.getText().toString();
        editTextKode.setError(null);
        editTextPertanyaan.setError(null);
        editTextJawaban1.setError(null);
        editTextJawaban2.setError(null);
        editTextJawaban3.setError(null);
        editTextJawaban4.setError(null);
        editTextKunciJawaban.setError(null);
        editTextBobot.setError(null);
        if (kode==null||kode.equals("")){
            editTextKode.setError(Html.fromHtml("Kode Tidak Boleh Kosong"));
            result = false;
        }
        if(pertanyaan==null||pertanyaan.equals("")){
            editTextPertanyaan.setError(Html.fromHtml("Pertanyaan Tidak Boleh Kosong"));
            result =false;
        }
        if(jawaban1==null||jawaban1.equals("")){
            editTextJawaban1.setError(Html.fromHtml("Jawaban 1 Tidak Boleh Kosong"));
            result =false;
        }
        if(jawaban2==null||jawaban2.equals("")){
            editTextJawaban2.setError(Html.fromHtml("Jawaban 2 Tidak Boleh Kosong"));
            result =false;
        }
        if(jawaban3==null||jawaban3.equals("")){
            editTextJawaban3.setError(Html.fromHtml("Jawaban 3 Tidak Boleh Kosong"));
            result =false;
        }
        if(jawaban4==null||jawaban4.equals("")){
            editTextJawaban4.setError(Html.fromHtml("Jawaban 4 Tidak Boleh Kosong"));
            result =false;
        }
        if(kunciJawaban==null||kunciJawaban.equals("")){
            editTextKunciJawaban.setError(Html.fromHtml("Kunci Jawaban Tidak Boleh Kosong"));
            result =false;
        }
        if(bobot==null||bobot.equals("")){
            editTextBobot.setError(Html.fromHtml("Boobot Tidak Boleh Kosong"));
            result =false;
        }
        return result;
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
}
