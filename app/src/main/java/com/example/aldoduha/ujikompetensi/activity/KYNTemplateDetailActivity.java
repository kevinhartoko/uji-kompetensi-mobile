package com.example.aldoduha.ujikompetensi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aldoduha.ujikompetensi.KYNBaseActivity;
import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.controller.KYNTemplateDetailController;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNConfirmationAlertDialogListener;
import com.example.aldoduha.ujikompetensi.connection.KYNSMPUtilities;
import com.example.aldoduha.ujikompetensi.connection.api.listener.KYNServiceConnection;
import com.example.aldoduha.ujikompetensi.model.KYNTemplateModel;
import com.example.aldoduha.ujikompetensi.model.KYNTemplateQuestionModel;
import com.example.aldoduha.ujikompetensi.model.KYNUserModel;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

import java.util.List;

/**
 * Created by aldoduha on 11/12/2017.
 */

public class KYNTemplateDetailActivity extends KYNBaseActivity {
    private KYNTemplateDetailController controller;
    private EditText editTextNama;
    private EditText editTextJumlahSoal;
    private LinearLayout linearLayoutSoal;
    private Button buttonTambah;
    private Button buttonHapus;
    private Button buttonLanjut;
    private Button buttonKembali;
    private Button buttonHapusTemplate;
    private KYNDatabaseHelper database;
    private Long templateId;
    private KYNTemplateModel templateModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_detail);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        templateId = getIntent().getLongExtra("templateId", 0);
        loadview();
        initDefaultValue();
    }
    @Override
    protected void onDestroy() {
        if(controller!=null)
            controller.unregisterLocalBroadCastReceiver();
        super.onDestroy();
    }

    private void loadview(){
        editTextNama = (EditText)findViewById(R.id.edittextNama);
        editTextJumlahSoal = (EditText)findViewById(R.id.edittextJumlahSoal);
        linearLayoutSoal = (LinearLayout)findViewById(R.id.linearSoal);
        buttonTambah = (Button)findViewById(R.id.btnTambah);
        buttonHapus = (Button)findViewById(R.id.btnHapus);
        buttonLanjut = (Button)findViewById(R.id.btnLanjut);
        buttonHapusTemplate = (Button)findViewById(R.id.btnHapusTemplate);
        buttonKembali = (Button)findViewById(R.id.btnKembali);
    }

    private void initDefaultValue(){
        database = new KYNDatabaseHelper(this);
        if(templateId!=null && templateId!=0){
            templateModel = database.getTemplate(templateId);
        }else{
            templateModel = new KYNTemplateModel();
        }
        controller = new KYNTemplateDetailController(this);
        if(templateId!=null && templateId!=0){
            List<KYNTemplateQuestionModel> templateQuestionModels = database.getTemplateQuestionList(templateId);
            generateKolomSoal(templateQuestionModels);
        }
        buttonLanjut.setOnClickListener(controller);
        buttonHapus.setOnClickListener(controller);
        buttonTambah.setOnClickListener(controller);
        buttonHapusTemplate.setOnClickListener(controller);
        buttonKembali.setOnClickListener(controller);
    }

    public void setValueToModel(){
        templateModel.setNama(editTextNama.getText().toString());
        templateModel.setJumlahSoal(Integer.parseInt(editTextJumlahSoal.getText().toString()));
        if(templateId==null || templateId==0){
            templateId = database.insertTemplate(templateModel);
            templateModel.setId(templateId);
        }else{
            database.updateTemplate(templateModel);
        }
        database.deleteTemplateQuestion(templateId);
        for (int i=0;i<linearLayoutSoal.getChildCount();i++){
            KYNTemplateQuestionModel templateQuestionModel = new KYNTemplateQuestionModel();
            LinearLayout linearCheck1 = (LinearLayout) linearLayoutSoal.getChildAt(i);
            EditText editTextCheck1 = (EditText)linearCheck1.getChildAt(1);
            EditText editTextCheck2 = (EditText)linearCheck1.getChildAt(3);
            String check1 = editTextCheck1.getText().toString();
            String check2 = editTextCheck2.getText().toString();
            templateQuestionModel.setBobot(Integer.parseInt(check1));
            templateQuestionModel.setJumlahSoal(Integer.parseInt(check2));
            templateQuestionModel.setTemplateModel(templateModel);
            database.insertTemplateQuestion(templateQuestionModel);
        }
    }

    public void setValuesToView(){
        if (templateModel.getNama()!=null && !templateModel.getNama().equals("")){
            editTextNama.setText(templateModel.getNama());
        }
        if (templateModel.getJumlahSoal()!=0){
            editTextJumlahSoal.setText(templateModel.getJumlahSoal()+"");
        }
    }

    public boolean validate(){
        boolean result = true;
        String nama = editTextNama.getText().toString();
        String jumlahSoal = editTextJumlahSoal.getText().toString();
        editTextNama.setError(null);
        editTextJumlahSoal.setError(null);
        if (nama==null||nama.equals("")){
            editTextNama.setError(Html.fromHtml("Nama Tidak Boleh Kosong"));
            result = false;
        }
        if(jumlahSoal==null||jumlahSoal.equals("")){
            editTextJumlahSoal.setError(Html.fromHtml("Jumlah Soal Tidak Boleh Kosong"));
            result =false;
        }
        if(!cekKolomSoal() || linearLayoutSoal.getChildCount()==0){
            result = false;
            showAlertDialog("Info","Isi kolom soal yang ada terlebih dahulu");
        }else{
            if(jumlahSoal!=null && !jumlahSoal.equals("")) {
                int jumlah = 0;
                for (int i = 0; i < linearLayoutSoal.getChildCount(); i++) {
                    LinearLayout linearCheck1 = (LinearLayout) linearLayoutSoal.getChildAt(i);
                    EditText editTextCheck2 = (EditText) linearCheck1.getChildAt(3);
                    String check2 = editTextCheck2.getText().toString();
                    jumlah = jumlah + Integer.parseInt(check2);
                }
                int soal = Integer.parseInt(jumlahSoal);
                if (jumlah != soal) {
                    result = false;
                    showAlertDialog("Info", "Jumlah soal tidak sesuai");
                }
            }
        }
        return result;
    }

    private boolean cekKolomSoal(){
        boolean result = false;
        if(linearLayoutSoal.getChildCount()>0) {
            LinearLayout linearCheck1 = (LinearLayout) linearLayoutSoal.getChildAt(linearLayoutSoal.getChildCount() - 1);
            EditText editTextCheck1 = (EditText) linearCheck1.getChildAt(1);
            EditText editTextCheck2 = (EditText) linearCheck1.getChildAt(3);
            String check1 = editTextCheck1.getText().toString();
            String check2 = editTextCheck2.getText().toString();
            if (!check1.equals("") && !check2.equals("")) {
                result = true;
            }
        }else{
            result =true;
        }
        return result;
    }

    public void generateKolomSoal(List<KYNTemplateQuestionModel> models){
        linearLayoutSoal.removeAllViews();
        for (KYNTemplateQuestionModel templateQuestionModel: models) {
            final LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 15);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout.LayoutParams layoutParamsTextview = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.3f);
            layoutParamsTextview.setMargins(5, 0, 0, 0);

            LinearLayout.LayoutParams layoutParamsEdittext = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.2f);

            final TextView textView = new TextView(this);
            textView.setLayoutParams(layoutParamsTextview);
            textView.setTextColor(getResources().getColor(R.color.black));
            textView.setText("Bobot");

            final EditText editText = new EditText(this);
            editText.setLayoutParams(layoutParamsEdittext);
            editText.setTextColor(getResources().getColor(R.color.black));
            editText.setBackground(getResources().getDrawable(R.drawable.edittext_style));
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setText(templateQuestionModel.getBobot()+"");

            final TextView textView1 = new TextView(this);
            textView1.setLayoutParams(layoutParamsTextview);
            textView1.setTextColor(getResources().getColor(R.color.black));
            textView1.setText("Banyaknya");

            final EditText editText1 = new EditText(this);
            editText1.setLayoutParams(layoutParamsEdittext);
            editText1.setTextColor(getResources().getColor(R.color.black));
            editText1.setBackground(getResources().getDrawable(R.drawable.edittext_style));
            editText1.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText1.setText(templateQuestionModel.getJumlahSoal()+"");

            linearLayout.addView(textView);
            linearLayout.addView(editText);
            linearLayout.addView(textView1);
            linearLayout.addView(editText1);

            linearLayoutSoal.addView(linearLayout);
        }
    }

    public void onTambahButtonClicked(){
        if(cekKolomSoal()) {
            final LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 15);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout.LayoutParams layoutParamsTextview = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.3f);
            layoutParamsTextview.setMargins(5, 0, 0, 0);

            LinearLayout.LayoutParams layoutParamsEdittext = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.2f);

            final TextView textView = new TextView(this);
            textView.setLayoutParams(layoutParamsTextview);
            textView.setTextColor(getResources().getColor(R.color.black));
            textView.setText("Bobot");

            final EditText editText = new EditText(this);
            editText.setLayoutParams(layoutParamsEdittext);
            editText.setTextColor(getResources().getColor(R.color.black));
            editText.setBackground(getResources().getDrawable(R.drawable.edittext_style));
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);

            final TextView textView1 = new TextView(this);
            textView1.setLayoutParams(layoutParamsTextview);
            textView1.setTextColor(getResources().getColor(R.color.black));
            textView1.setText("Banyaknya");

            final EditText editText1 = new EditText(this);
            editText1.setLayoutParams(layoutParamsEdittext);
            editText1.setTextColor(getResources().getColor(R.color.black));
            editText1.setBackground(getResources().getDrawable(R.drawable.edittext_style));
            editText1.setInputType(InputType.TYPE_CLASS_NUMBER);

            linearLayout.addView(textView);
            linearLayout.addView(editText);
            linearLayout.addView(textView1);
            linearLayout.addView(editText1);

            linearLayoutSoal.addView(linearLayout);
        }else{
            showAlertDialog("Info","Isi kolom soal yang ada terlebih dahulu");
        }
    }

    public void onHapusButtonClicked(){
        if(linearLayoutSoal.getChildCount()>0)
            linearLayoutSoal.removeViewAt(linearLayoutSoal.getChildCount()-1);
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

    KYNConfirmationAlertDialogListener listenerHapus = new KYNConfirmationAlertDialogListener() {
        @Override
        public void onOK() {
            List<KYNTemplateQuestionModel> models = database.getTemplateQuestionList(templateId);
            for (KYNTemplateQuestionModel model : models) {
                database.deleteTemplateQuestion(model.getId());
            }
            database.deleteTemplate(templateId);

            if(KYNSMPUtilities.isConnectServer){
                KYNTemplateModel model = new KYNTemplateModel();
                model.setId(templateModel.getId());
                model.setServerId(templateModel.getServerId());
                submitTemplate(model);
            }else {
                setResult(KYNIntentConstant.RESULT_CODE_TEMPLATE_DETAIL);
                finish();
            }
        }

        @Override
        public void onCancel() {

        }
    };

    public void onButtonHapusTemplateClicked(){
        showConfirmationAlertDialog("Apakah anda yakin ingin menghapus template ini?",listenerHapus);
    }

    public void submitTemplate(){
        showLoadingDialog(getResources().getString(R.string.loading));
        KYNUserModel session = database.getSession();
        Intent intent = new Intent(this, KYNServiceConnection.class);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_DATA, templateModel);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_USERNAME, session.getUsername());
        intent.setAction(KYNIntentConstant.ACTION_SUBMIT_TEMPLATE);
        intent.addCategory(KYNIntentConstant.CATEGORY_SUBMIT_TEMPLATE);
        startService(intent);
    }

    public void submitTemplate(KYNTemplateModel templateModel){
        showLoadingDialog(getResources().getString(R.string.loading));
        KYNUserModel session = database.getSession();
        Intent intent = new Intent(this, KYNServiceConnection.class);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_DATA, templateModel);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_USERNAME, session.getUsername());
        intent.setAction(KYNIntentConstant.ACTION_SUBMIT_TEMPLATE);
        intent.addCategory(KYNIntentConstant.CATEGORY_SUBMIT_TEMPLATE);
        startService(intent);
    }
}
