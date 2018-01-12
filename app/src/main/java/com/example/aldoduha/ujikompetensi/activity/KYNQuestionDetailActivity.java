package com.example.aldoduha.ujikompetensi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.aldoduha.ujikompetensi.KYNBaseActivity;
import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.controller.KYNQuestionDetailController;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNConfirmationAlertDialogListener;
import com.example.aldoduha.ujikompetensi.connection.KYNSMPUtilities;
import com.example.aldoduha.ujikompetensi.connection.api.listener.KYNServiceConnection;
import com.example.aldoduha.ujikompetensi.model.KYNQuestionModel;
import com.example.aldoduha.ujikompetensi.model.KYNUserModel;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

import org.w3c.dom.Text;

/**
 * Created by aldoduha on 11/12/2017.
 */

public class KYNQuestionDetailActivity extends KYNBaseActivity {
    private KYNQuestionDetailController controller;
    private EditText editTextKode;
    private EditText editTextPertanyaan;
    private EditText editTextJawaban1;
    private EditText editTextJawaban2;
    private EditText editTextJawaban3;
    private EditText editTextJawaban4;
//    private EditText editTextKunciJawaban;
    private EditText editTextBobot;
    private Button buttonLanjut;
    private Button buttonKembali;
    private Button buttonHapus;
    private KYNDatabaseHelper database;
    private Long questionId;
    private KYNQuestionModel questionModel;
    private RadioButton radio1;
    private RadioButton radio2;
    private RadioButton radio3;
    private RadioButton radio4;
    private TextView kunciJawabanErrTextview;
    private Spinner spinnerCategory;
    private ArrayAdapter adapter;
    private String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        questionId = getIntent().getLongExtra("questionId", 0);
        category = getIntent().getStringExtra(KYNIntentConstant.INTENT_EXTRA_CATEGORY);
        loadview();
        initDefaultValue();
    }
    @Override
    protected void onDestroy() {
        if(controller!=null)
            controller.onDestroy();
        super.onDestroy();
    }

    private void loadview(){
        editTextKode = (EditText)findViewById(R.id.edittextKode);
        editTextPertanyaan = (EditText)findViewById(R.id.edittextPertanyaan);
        editTextJawaban1 = (EditText)findViewById(R.id.edittextJawaban1);
        editTextJawaban2 = (EditText)findViewById(R.id.edittextJawaban2);
        editTextJawaban3 = (EditText)findViewById(R.id.edittextJawaban3);
        editTextJawaban4 = (EditText)findViewById(R.id.edittextJawaban4);
//        editTextKunciJawaban = (EditText)findViewById(R.id.edittextKunciJawaban);
        editTextBobot = (EditText)findViewById(R.id.edittextBobot);
        buttonLanjut = (Button)findViewById(R.id.btnLanjut);
        buttonKembali = (Button)findViewById(R.id.btnKembali);
        buttonHapus = (Button)findViewById(R.id.btnHapus);
        radio1 = (RadioButton)findViewById(R.id.radio1);
        radio2 = (RadioButton)findViewById(R.id.radio2);
        radio3 = (RadioButton)findViewById(R.id.radio3);
        radio4 = (RadioButton)findViewById(R.id.radio4);
        kunciJawabanErrTextview = (TextView)findViewById(R.id.kunciJawabanErrorTextview);
        spinnerCategory = (Spinner)findViewById(R.id.spinnerCategory);
    }

    private void initDefaultValue(){
        database = new KYNDatabaseHelper(this);
        if(questionId!=null && questionId!=0){
            questionModel = database.getQuestion(questionId);
        }else{
            questionModel = new KYNQuestionModel();
            buttonHapus.setVisibility(View.GONE);
        }
        controller = new KYNQuestionDetailController(this);
        buttonLanjut.setOnClickListener(controller);
        buttonKembali.setOnClickListener(controller);
        buttonHapus.setOnClickListener(controller);
        if(category!=null && !category.equals("")){
            int position = 0;
            for(int i=0;i<spinnerCategory.getCount();i++){
                if(category.equalsIgnoreCase(spinnerCategory.getItemAtPosition(i).toString()))
                    position=i;
            }
            spinnerCategory.setSelection(position);
        }
        spinnerCategory.setEnabled(false);
    }

    public void setValueToModel(){
        questionModel.setName(editTextKode.getText().toString());
        questionModel.setQuestion(editTextPertanyaan.getText().toString());
        questionModel.setAnswer1(editTextJawaban1.getText().toString());
        questionModel.setAnswer2(editTextJawaban2.getText().toString());
        questionModel.setAnswer3(editTextJawaban3.getText().toString());
        questionModel.setAnswer4(editTextJawaban4.getText().toString());
        if(radio1.isChecked()){
            questionModel.setKeyAnswer(editTextJawaban1.getText().toString());
        }else if(radio2.isChecked()) {
            questionModel.setKeyAnswer(editTextJawaban2.getText().toString());
        }else if(radio3.isChecked()) {
            questionModel.setKeyAnswer(editTextJawaban3.getText().toString());
        }else if(radio4.isChecked()) {
            questionModel.setKeyAnswer(editTextJawaban4.getText().toString());
        }
//        questionModel.setKeyAnswer(editTextKunciJawaban.getText().toString());
        questionModel.setBobot(Integer.parseInt(editTextBobot.getText().toString()));
        questionModel.setCategory(spinnerCategory.getSelectedItem().toString());
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
//            editTextKunciJawaban.setText(questionModel.getKeyAnswer());
            if(questionModel.getKeyAnswer().equals(questionModel.getAnswer1())){
                radio1.setChecked(true);
            }else if(questionModel.getKeyAnswer().equals(questionModel.getAnswer2())){
                radio2.setChecked(true);
            }else if(questionModel.getKeyAnswer().equals(questionModel.getAnswer3())){
                radio3.setChecked(true);
            }else if(questionModel.getKeyAnswer().equals(questionModel.getAnswer4())){
                radio4.setChecked(true);
            }
        }
        if(questionModel.getBobot()!=0){
            editTextBobot.setText(questionModel.getBobot()+"");
        }
        if(questionModel.getCategory()!=null && !questionModel.getCategory().equals("")){
            int position = 0;
            for(int i=0;i<spinnerCategory.getCount();i++){
                if(questionModel.getCategory().equalsIgnoreCase(spinnerCategory.getItemAtPosition(i).toString()))
                    position=i;
            }
            spinnerCategory.setSelection(position);
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
//        String kunciJawaban = editTextKunciJawaban.getText().toString();
        String bobot = editTextBobot.getText().toString();
        editTextKode.setError(null);
        editTextPertanyaan.setError(null);
        editTextJawaban1.setError(null);
        editTextJawaban2.setError(null);
        editTextJawaban3.setError(null);
        editTextJawaban4.setError(null);
//        editTextKunciJawaban.setError(null);
        editTextBobot.setError(null);
        if (kode==null||kode.equals("")){
            editTextKode.setError(Html.fromHtml("Code must be filled"));
            result = false;
        }
        if(pertanyaan==null||pertanyaan.equals("")){
            editTextPertanyaan.setError(Html.fromHtml("Question must be filled"));
            result =false;
        }
        if(jawaban1==null||jawaban1.equals("")){
            editTextJawaban1.setError(Html.fromHtml("Answer 1 must be filled"));
            result =false;
        }
        if(jawaban2==null||jawaban2.equals("")){
            editTextJawaban2.setError(Html.fromHtml("Answer 2 must be filled"));
            result =false;
        }
        if(jawaban3==null||jawaban3.equals("")){
            editTextJawaban3.setError(Html.fromHtml("Answer 3 must be filled"));
            result =false;
        }
        if(jawaban4==null||jawaban4.equals("")){
            editTextJawaban4.setError(Html.fromHtml("Answer 4 must be filled"));
            result =false;
        }
//        if(kunciJawaban==null||kunciJawaban.equals("")){
//            editTextKunciJawaban.setError(Html.fromHtml("Kunci Jawaban Tidak Boleh Kosong"));
//            result =false;
//        }
        if(bobot==null||bobot.equals("")){
            editTextBobot.setError(Html.fromHtml("Weight must be filled"));
            result =false;
        }else{
            int bobotInt = Integer.parseInt(bobot);
            if(bobotInt<1||bobotInt>10){
                editTextBobot.setError(Html.fromHtml("Weight must between 1-10"));
                result =false;
            }
        }

        if(!radio1.isChecked() && !radio2.isChecked() && !radio3.isChecked() && !radio4.isChecked()){
            kunciJawabanErrTextview.setVisibility(View.VISIBLE);
            result =false;
        }else {
            kunciJawabanErrTextview.setVisibility(View.GONE);
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
        showConfirmationAlertDialog("Are you sure to go back?", listener);
    }

    KYNConfirmationAlertDialogListener listenerHapus = new KYNConfirmationAlertDialogListener() {
        @Override
        public void onOK() {
            database.deleteQuestion(questionId);
            if(KYNSMPUtilities.isConnectServer){
                KYNQuestionModel model = new KYNQuestionModel();
                model.setId(questionModel.getId());
                model.setServerId(questionModel.getServerId());
                deleteQuestion(model);
            }else {
                setResult(KYNIntentConstant.RESULT_CODE_QUESTION_DETAIL);
                finish();
            }
        }

        @Override
        public void onCancel() {

        }
    };

    public void initiateCategory(){
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, KYNIntentConstant.categoryDetail);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }

    public void submitQuestion(){
        showLoadingDialog(getResources().getString(R.string.loading));
        KYNUserModel session = database.getSession();
        Intent intent = new Intent(this, KYNServiceConnection.class);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_DATA, questionModel);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_USERNAME, session.getUsername());
        intent.setAction(KYNIntentConstant.ACTION_SUBMIT_QUESTION);
        intent.addCategory(KYNIntentConstant.CATEGORY_SUBMIT_QUESTION);
        startService(intent);
    }

    public void submitQuestion(KYNQuestionModel questionModel){
        showLoadingDialog(getResources().getString(R.string.loading));
        KYNUserModel session = database.getSession();
        Intent intent = new Intent(this, KYNServiceConnection.class);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_DATA, questionModel);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_USERNAME, session.getUsername());
        intent.setAction(KYNIntentConstant.ACTION_SUBMIT_QUESTION);
        intent.addCategory(KYNIntentConstant.CATEGORY_SUBMIT_QUESTION);
        startService(intent);
    }
    public void deleteQuestion(KYNQuestionModel questionModel){
        showLoadingDialog(getResources().getString(R.string.loading));
        KYNUserModel session = database.getSession();
        Intent intent = new Intent(this, KYNServiceConnection.class);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_DATA, questionModel);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_USERNAME, session.getUsername());
        intent.setAction(KYNIntentConstant.ACTION_DELETE_QUESTION);
        intent.addCategory(KYNIntentConstant.CATEGORY_DELETE_QUESTION);
        startService(intent);
    }

    public void onButtonHapusClicked(){
        showConfirmationAlertDialog("Are you sure to delete this question?", listenerHapus);
    }
}
