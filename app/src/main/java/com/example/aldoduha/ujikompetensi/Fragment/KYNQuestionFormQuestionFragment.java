package com.example.aldoduha.ujikompetensi.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.aldoduha.ujikompetensi.Fragment.Controller.KYNQuestionQuestionController;
import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.KYNQuestionFormActivity;
import com.example.aldoduha.ujikompetensi.activity.controller.KYNQuestionFormQuestionController;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNConfirmationAlertDialogListener;
import com.example.aldoduha.ujikompetensi.model.KYNQuestionModel;

import java.util.List;

/**
 * Created by aldoduha on 10/26/2017.
 */

public class KYNQuestionFormQuestionFragment extends KYNBaseFragment {
    private KYNQuestionFormActivity activity;
    private KYNQuestionQuestionController controller;
    private Button buttonSubmit;
    private Button buttonKembali;
    private LinearLayout linearLayoutRoot;
    private KYNDatabaseHelper database;
    private List<KYNQuestionModel> questionList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (KYNQuestionFormActivity)getActivity();
        view = inflater.inflate(R.layout.activity_question_form_question, null);
        loadview();
        initDefaultValue();
        return view;
    }

    private void loadview(){
        buttonSubmit = (Button)view.findViewById(R.id.btnSubmit);
        buttonKembali = (Button)view.findViewById(R.id.btnKembali);
        linearLayoutRoot = (LinearLayout)view.findViewById(R.id.linearRoot);
    }

    private void initDefaultValue(){
        controller = new KYNQuestionQuestionController(this);
        database = new KYNDatabaseHelper(activity);
        buttonSubmit.setOnClickListener(controller);
        buttonKembali.setOnClickListener(controller);
        generateQuestion();
    }

    private void generateQuestion() {
        linearLayoutRoot.removeAllViews();
        questionList = activity.getQuestionModels();
        if (questionList.size() > 0) {
            for (final KYNQuestionModel model : questionList) {
                final LinearLayout linearLayout = new LinearLayout(activity);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                final TextView textView = new TextView(activity);
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                textView.setText(model.getQuestion());

                final RadioGroup radioGroup = new RadioGroup(activity);
                radioGroup.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                radioGroup.setOrientation(LinearLayout.VERTICAL);

                final RadioButton radioButton1 = new RadioButton(activity);
                radioButton1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                radioButton1.setText(model.getAnswer1());
                radioButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (radioButton1.isChecked())
                            model.setIntervieweeAnswer(model.getAnswer1());
                    }
                });

                final RadioButton radioButton2 = new RadioButton(activity);
                radioButton2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                radioButton2.setText(model.getAnswer2());
                radioButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (radioButton2.isChecked())
                            model.setIntervieweeAnswer(model.getAnswer2());
                    }
                });

                final RadioButton radioButton3 = new RadioButton(activity);
                radioButton3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                radioButton3.setText(model.getAnswer3());
                radioButton3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (radioButton3.isChecked())
                            model.setIntervieweeAnswer(model.getAnswer3());
                    }
                });

                final RadioButton radioButton4 = new RadioButton(activity);
                radioButton4.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                radioButton4.setText(model.getAnswer4());
                radioButton4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (radioButton4.isChecked())
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

                if(model.getIntervieweeAnswer()!=null && !model.getIntervieweeAnswer().equals("")){
                    if(model.getIntervieweeAnswer().equals(model.getAnswer1()))
                        radioButton1.setChecked(true);
                    else if(model.getIntervieweeAnswer().equals(model.getAnswer2()))
                        radioButton2.setChecked(true);
                    else if(model.getIntervieweeAnswer().equals(model.getAnswer3()))
                        radioButton3.setChecked(true);
                    else if(model.getIntervieweeAnswer().equals(model.getAnswer4()))
                        radioButton4.setChecked(true);
                }
            }
        } else {

        }
    }
    KYNConfirmationAlertDialogListener submitListener = new KYNConfirmationAlertDialogListener() {
        @Override
        public void onOK() {
            for(KYNQuestionModel model:questionList){
                database.updateQuestionIntervieweeAnswer(model);
            }
            activity.finish();
        }

        @Override
        public void onCancel() {

        }
    };

    public void onButtonSubmitClicked(){
        activity.showConfirmationAlertDialog("Apakah anda sudah yakin dengan semua jawaban anda?", submitListener);
    }
    public void onButtonKembaliClicked(){
        activity.onNextButtonClicked(0);
    }
}
