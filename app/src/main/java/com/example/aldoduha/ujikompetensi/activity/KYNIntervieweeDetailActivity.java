package com.example.aldoduha.ujikompetensi.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.aldoduha.ujikompetensi.KYNBaseActivity;
import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.controller.KYNIntervieweeDetailController;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNConfirmationAlertDialogListener;
import com.example.aldoduha.ujikompetensi.connection.KYNSMPUtilities;
import com.example.aldoduha.ujikompetensi.connection.api.listener.KYNServiceConnection;
import com.example.aldoduha.ujikompetensi.model.KYNFeedbackModel;
import com.example.aldoduha.ujikompetensi.model.KYNIntervieweeModel;
import com.example.aldoduha.ujikompetensi.model.KYNQuestionModel;
import com.example.aldoduha.ujikompetensi.model.KYNTemplateModel;
import com.example.aldoduha.ujikompetensi.model.KYNUserModel;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

import java.text.SimpleDateFormat;
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
    private TextView textViewCategory;
    private TextView textViewScore;
    private EditText editTextFeedback;
    private Button buttonKirim;
    private LinearLayout linearLayoutFeedback;
    private LinearLayout linearLayoutQuestion;
    private Button btnQuestion;
    private Long feedbackId;//untuk delete feedback

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
        if(controller!=null)
            controller.unregisterLocalBroadCastReceiver();
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
        textViewCategory = (TextView)findViewById(R.id.textViewCategory);
        textViewScore = (TextView)findViewById(R.id.textViewScore);
        editTextFeedback = (EditText)findViewById(R.id.edittextFeedback);
        linearLayoutFeedback = (LinearLayout)findViewById(R.id.linearFeedback);
        linearLayoutQuestion =(LinearLayout)findViewById(R.id.linearQuestion);
        btnQuestion = (Button)findViewById(R.id.btnQuestion);
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
            List<KYNQuestionModel> questionModels = database.getListQuestion(intervieweeId);
            generateQuestion(questionModels);
        }
        buttonLanjut.setOnClickListener(controller);
        buttonKirim.setOnClickListener(controller);
        btnQuestion.setOnClickListener(controller);
    }

    public void setValuesToView(){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        textViewNama.setText(intervieweeModel.getNama());
        if(intervieweeModel.getGender().equals("P"))
            textViewGender.setText("Perempuan");
        else if(intervieweeModel.getGender().equals("L"))
            textViewGender.setText("Laki-laki");
        textViewEmail.setText(getIntervieweeModel().getEmail());
        textViewHandphone.setText(intervieweeModel.getHandphone());
        textViewAddress.setText(intervieweeModel.getAddress());
        textViewDOB.setText(format.format(intervieweeModel.getDob()));
        textViewCategory.setText(intervieweeModel.getCategory());
        textViewScore.setText(intervieweeModel.getScore()+"");
    }

    public void generateFeedback(List<KYNFeedbackModel> models){
        linearLayoutFeedback.removeAllViews();
        for (final KYNFeedbackModel feedbackModel: models) {
            final LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 15);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            final LinearLayout linearLayoutUsername = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParamsUsername = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,0.8f);
            linearLayoutUsername.setLayoutParams(layoutParamsUsername);
            linearLayoutUsername.setOrientation(LinearLayout.VERTICAL);

            LinearLayout.LayoutParams layoutParamsTextview = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParamsTextview.setMargins(5, 0, 0, 0);

            LinearLayout.LayoutParams layoutParamsButton = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.2f);

            final TextView textViewUsername = new TextView(this);
            textViewUsername.setLayoutParams(layoutParamsTextview);
            textViewUsername.setTextColor(getResources().getColor(R.color.black));
            textViewUsername.setText(feedbackModel.getName()+":");
            textViewUsername.setTypeface(Typeface.DEFAULT_BOLD);

            final TextView textView = new TextView(this);
            textView.setLayoutParams(layoutParamsTextview);
            textView.setTextColor(getResources().getColor(R.color.black));
            textView.setText(feedbackModel.getDescription());

            final Button button = new Button(this);
            button.setLayoutParams(layoutParamsButton);
            button.setTextColor(getResources().getColor(R.color.black));
            button.setAllCaps(false);
            button.setText("Hapus");

            KYNUserModel session = database.getSession();
            if(session.getUsername().equals(feedbackModel.getName()))
                button.setVisibility(View.VISIBLE);
            else
                button.setVisibility(View.GONE);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(KYNSMPUtilities.isConnectServer){
                        KYNFeedbackModel model = new KYNFeedbackModel();
                        model.setId(feedbackModel.getId());
                        model.setServerId(feedbackModel.getServerId());
                        feedbackId = feedbackModel.getId();
                        deleteFeedback(model);
                    }else{
                        database.deleteFeedback(feedbackModel.getId());
                        List<KYNFeedbackModel> feedbackModels = database.getFeedbackList(intervieweeId);
                        generateFeedback(feedbackModels);
                    }
                }
            });

            linearLayoutUsername.addView(textViewUsername);
            linearLayoutUsername.addView(textView);
            linearLayout.addView(linearLayoutUsername);
            linearLayout.addView(button);
            linearLayoutFeedback.addView(linearLayout);
        }
    }

    private void generateQuestion(List<KYNQuestionModel> questionList) {
        linearLayoutQuestion.removeAllViews();
        if (questionList.size() > 0) {
            for (final KYNQuestionModel model : questionList) {
                final LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                final TextView textView = new TextView(this);
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                textView.setText(model.getQuestion());
                textView.setTextColor(getResources().getColor(R.color.black));

                final RadioGroup radioGroup = new RadioGroup(this);
                radioGroup.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                radioGroup.setOrientation(LinearLayout.VERTICAL);

                final RadioButton radioButton1 = new RadioButton(this);
                radioButton1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                radioButton1.setText(model.getAnswer1());
                radioButton1.setTextColor(getResources().getColor(R.color.black));

                final RadioButton radioButton2 = new RadioButton(this);
                radioButton2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                radioButton2.setText(model.getAnswer2());
                radioButton2.setTextColor(getResources().getColor(R.color.black));

                final RadioButton radioButton3 = new RadioButton(this);
                radioButton3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                radioButton3.setText(model.getAnswer3());
                radioButton3.setTextColor(getResources().getColor(R.color.black));

                final RadioButton radioButton4 = new RadioButton(this);
                radioButton4.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                radioButton4.setText(model.getAnswer4());
                radioButton4.setTextColor(getResources().getColor(R.color.black));

                radioGroup.addView(radioButton1);
                radioGroup.addView(radioButton2);
                radioGroup.addView(radioButton3);
                radioGroup.addView(radioButton4);
                linearLayout.addView(textView);
                linearLayout.addView(radioGroup);
                linearLayoutQuestion.addView(linearLayout);

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
                radioButton1.setEnabled(false);
//                radioButton1.setBackgroundColor(getResources().getColor(R.color.light_gray));
                radioButton2.setEnabled(false);
//                radioButton2.setBackgroundColor(getResources().getColor(R.color.light_gray));
                radioButton3.setEnabled(false);
//                radioButton3.setBackgroundColor(getResources().getColor(R.color.light_gray));
                radioButton4.setEnabled(false);
//                radioButton4.setBackgroundColor(getResources().getColor(R.color.light_gray));
            }
        } else {

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
        showConfirmationAlertDialog("Are you sure to go back?", listener);
    }

    public void submitFeedback(KYNFeedbackModel model){
        showLoadingDialog(getResources().getString(R.string.loading));
        KYNUserModel session = database.getSession();
        Intent intent = new Intent(this, KYNServiceConnection.class);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_DATA, model);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_USERNAME, session.getUsername());
        intent.setAction(KYNIntentConstant.ACTION_SUBMIT_FEEDBACK);
        intent.addCategory(KYNIntentConstant.CATEGORY_SUBMIT_FEEDBACK);
        startService(intent);
    }

    public void deleteFeedback(KYNFeedbackModel model){
        showLoadingDialog(getResources().getString(R.string.loading));
        KYNUserModel session = database.getSession();
        Intent intent = new Intent(this, KYNServiceConnection.class);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_DATA, model);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_USERNAME, session.getUsername());
        intent.setAction(KYNIntentConstant.ACTION_DELETE_FEEDBACK);
        intent.addCategory(KYNIntentConstant.CATEGORY_DELETE_FEEDBACK);
        startService(intent);
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

    public LinearLayout getLinearLayoutQuestion() {
        return linearLayoutQuestion;
    }

    public Button getBtnQuestion() {
        return btnQuestion;
    }

    public Long getFeedbackId() {
        return feedbackId;
    }
}
