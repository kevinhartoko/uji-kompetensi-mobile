package com.example.aldoduha.ujikompetensi.activity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aldoduha.ujikompetensi.KYNBaseActivity;
import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.controller.KYNIntervieweeDetailController;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNConfirmationAlertDialogListener;
import com.example.aldoduha.ujikompetensi.model.KYNFeedbackModel;
import com.example.aldoduha.ujikompetensi.model.KYNIntervieweeModel;
import com.example.aldoduha.ujikompetensi.model.KYNTemplateQuestionModel;

import java.util.List;

/**
 * Created by aldoduha on 12/14/2017.
 */

public class KYNIntervieweeDetailActivity extends KYNBaseActivity{
    private KYNIntervieweeDetailController controller;
    private Button buttonLanjut;
    private KYNDatabaseHelper database;
    private KYNIntervieweeModel intervieweeModel;
    private Long intervieweeId;
    private TextView textViewNama;
    private TextView textViewGender;
    private TextView textViewEmail;
    private TextView textViewHandphone;
    private TextView textViewAddress;
    private TextView textViewDOB;
    private EditText editTextFeedback;
    private Button buttonKirim;
    private LinearLayout linearLayoutFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interviewee_detail);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        intervieweeId = getIntent().getLongExtra("intervieweeId", 0);
        loadview();
        initDefaultValue();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void loadview(){
        buttonLanjut = (Button)findViewById(R.id.btnLanjut);
        buttonKirim = (Button)findViewById(R.id.btnKirim);
        textViewNama = (TextView)findViewById(R.id.textViewNama);
        textViewGender = (TextView)findViewById(R.id.textViewGender);
        textViewEmail = (TextView)findViewById(R.id.textViewEmail);
        textViewHandphone = (TextView)findViewById(R.id.textViewHandphone);
        textViewAddress = (TextView)findViewById(R.id.textViewAddress);
        textViewDOB = (TextView)findViewById(R.id.textViewDOB);
        editTextFeedback = (EditText)findViewById(R.id.edittextFeedback);
    }

    private void initDefaultValue(){
        database = new KYNDatabaseHelper(this);
        if(intervieweeId!=null && intervieweeId!=0){
            intervieweeModel = database.getInterviewee(intervieweeId);
        }else{
            intervieweeModel = new KYNIntervieweeModel();
        }
        controller = new KYNIntervieweeDetailController(this);
        if(intervieweeId!=null && intervieweeId!=0){
            List<KYNFeedbackModel> feedbackModels = database.getFeedbackList(intervieweeId);
            generateFeedback(feedbackModels);
        }
        buttonLanjut.setOnClickListener(controller);
        buttonKirim.setOnClickListener(controller);
    }

    public void setValuesToView(){

    }

    public void generateFeedback(List<KYNFeedbackModel> models){
        linearLayoutFeedback.removeAllViews();
        for (final KYNFeedbackModel feedbackModel: models) {
            final LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 15);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout.LayoutParams layoutParamsTextview = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.8f);
            layoutParamsTextview.setMargins(5, 0, 0, 0);

            LinearLayout.LayoutParams layoutParamsButton = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.2f);

            final TextView textView = new TextView(this);
            textView.setLayoutParams(layoutParamsTextview);
            textView.setTextColor(getResources().getColor(R.color.black));
            textView.setText(feedbackModel.getDescription());

            final Button button = new Button(this);
            button.setLayoutParams(layoutParamsButton);
            button.setTextColor(getResources().getColor(R.color.black));
            button.setAllCaps(false);
            button.setText("Hapus");
            button.setVisibility(View.GONE);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    database.deleteFeedback(feedbackModel.getId());
                    List<KYNFeedbackModel> feedbackModels = database.getFeedbackList(intervieweeId);
                    generateFeedback(feedbackModels);
                }
            });

            linearLayout.addView(textView);
            linearLayout.addView(button);

            linearLayoutFeedback.addView(linearLayout);
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

    public EditText getEditTextFeedback() {
        return editTextFeedback;
    }

    public KYNIntervieweeModel getIntervieweeModel() {
        return intervieweeModel;
    }

    public Long getIntervieweeId() {
        return intervieweeId;
    }
}
