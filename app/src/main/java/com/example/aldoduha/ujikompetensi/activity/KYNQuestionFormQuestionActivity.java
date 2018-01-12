package com.example.aldoduha.ujikompetensi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.aldoduha.ujikompetensi.KYNBaseActivity;
import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.controller.KYNQuestionFormIdentityController;
import com.example.aldoduha.ujikompetensi.activity.controller.KYNQuestionFormQuestionController;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNConfirmationAlertDialogListener;
import com.example.aldoduha.ujikompetensi.model.KYNQuestionModel;

import java.util.List;

/**
 * Created by aldoduha on 10/26/2017.
 */

public class KYNQuestionFormQuestionActivity extends KYNBaseActivity {
    private Activity activity;
    private KYNQuestionFormQuestionController controller;
    private Button buttonSubmit;
    private Button buttonKembali;
    private LinearLayout linearLayoutRoot;
    private KYNDatabaseHelper database;
    private List<KYNQuestionModel> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_form_question);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        loadview();
        initDefaultValue();
    }

    private void loadview(){
        buttonSubmit = (Button)findViewById(R.id.btnSubmit);
        buttonKembali = (Button)findViewById(R.id.btnKembali);
        linearLayoutRoot = (LinearLayout)findViewById(R.id.linearRoot);
    }

    private void initDefaultValue(){
        controller = new KYNQuestionFormQuestionController(this);
        database = new KYNDatabaseHelper(this);
        buttonSubmit.setOnClickListener(controller);
        buttonKembali.setOnClickListener(controller);
        generateQuestion();
    }

    KYNConfirmationAlertDialogListener listener = new KYNConfirmationAlertDialogListener() {
        @Override
        public void onOK() {
            setResult(activity.RESULT_OK);
            finish();
        }

        @Override
        public void onCancel() {

        }
    };

    @Override
    public void onBackPressed() {
        showConfirmationAlertDialog("All of your data will not be saved, are you sure to go back?", listener);
    }

    private void generateQuestion(){
        linearLayoutRoot.removeAllViews();
        questionList = database.getListQuestion();
        if(questionList.size()>0) {
            for (final KYNQuestionModel model : questionList) {
                final LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                final TextView textView = new TextView(this);
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                textView.setText(model.getQuestion());

                final RadioGroup radioGroup = new RadioGroup(this);
                radioGroup.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                radioGroup.setOrientation(LinearLayout.VERTICAL);

                final RadioButton radioButton1 = new RadioButton(this);
                radioButton1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                radioButton1.setText(model.getAnswer1());
                radioButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(radioButton1.isChecked())
                            model.setIntervieweeAnswer(model.getAnswer1());
                    }
                });

                final RadioButton radioButton2 = new RadioButton(this);
                radioButton2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                radioButton2.setText(model.getAnswer2());
                radioButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(radioButton2.isChecked())
                            model.setIntervieweeAnswer(model.getAnswer2());
                    }
                });

                final RadioButton radioButton3 = new RadioButton(this);
                radioButton3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                radioButton3.setText(model.getAnswer3());
                radioButton3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(radioButton3.isChecked())
                            model.setIntervieweeAnswer(model.getAnswer3());
                    }
                });

                final RadioButton radioButton4 = new RadioButton(this);
                radioButton4.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                radioButton4.setText(model.getAnswer4());
                radioButton4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(radioButton4.isChecked())
                            model.setIntervieweeAnswer(model.getAnswer4());
                    }
                });

                radioGroup.addView(radioButton1);
                radioGroup.addView(radioButton2);
                radioGroup.addView(radioButton3);
                radioGroup.addView(radioButton4);
                linearLayout.addView(textView);
                linearLayout.addView(radioGroup);
                linearLayoutRoot.addView(linearLayout);
            }
        }else{

        }
    }
    KYNConfirmationAlertDialogListener submitListener = new KYNConfirmationAlertDialogListener() {
        @Override
        public void onOK() {
            for(KYNQuestionModel model:questionList){
                database.updateQuestionIntervieweeAnswer(model);
            }
            setResult(activity.RESULT_OK);
            finish();
        }

        @Override
        public void onCancel() {

        }
    };
    public void onButtonSubmitClicked(){
        showConfirmationAlertDialog("Are you sure with all your answer?", submitListener);
    }

    KYNConfirmationAlertDialogListener kembaliListener = new KYNConfirmationAlertDialogListener() {
        @Override
        public void onOK() {
            finish();
        }

        @Override
        public void onCancel() {

        }
    };
    public void onButtonKembaliClicked(){
        showConfirmationAlertDialog("All of your data will not be saved, are you sure to go back?", kembaliListener);
    }
}
