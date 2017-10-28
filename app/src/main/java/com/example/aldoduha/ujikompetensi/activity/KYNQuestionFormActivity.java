package com.example.aldoduha.ujikompetensi.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aldoduha.ujikompetensi.Fragment.KYNQuestionFormIdentityFragment;
import com.example.aldoduha.ujikompetensi.Fragment.KYNQuestionFormQuestionFragment;
import com.example.aldoduha.ujikompetensi.KYNApplicationContext;
import com.example.aldoduha.ujikompetensi.KYNBaseActivity;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.controller.KYNQuestionFormController;
import com.example.aldoduha.ujikompetensi.alertDialog.KYNConfirmationAlertDialog;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNConfirmationAlertDialogListener;
import com.example.aldoduha.ujikompetensi.model.KYNIntervieweeModel;
import com.example.aldoduha.ujikompetensi.model.KYNQuestionModel;

import java.util.List;

/**
 * Created by aldoduha on 10/26/2017.
 */

public class KYNQuestionFormActivity extends KYNBaseActivity {
    private FragmentTabHost fragmentTabHost;
    private List<KYNQuestionModel> questionModels;
    private KYNIntervieweeModel intervieweeModel;
    private KYNQuestionFormActivity activity;
    private KYNQuestionFormController controller;
    private KYNConfirmationAlertDialog confirmationAlertDialog;
    private KYNApplicationContext application;
    private Long intervieweeId;
    private LinearLayout identity;
    private LinearLayout question;
    private TextView identityText;
    private TextView questionText;
    private String tabId = "IDENTITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_form);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        application = (KYNApplicationContext) getApplicationContext();
        loadview();
        initDefaultValue();
        if (savedInstanceState != null) {
            if (application.getTabPosition() == 0) {
                tabId = "IDENTITY";
                onTabChange(tabId);
            } else if (application.getTabPosition() == 1) {
                tabId = "QUESTION";
                onTabChange(tabId);
            }
        }
        if(intervieweeId!=null)
            intervieweeModel = controller.getIntervieweeModel(intervieweeId);
        else
            intervieweeModel = new KYNIntervieweeModel();

        questionModels = controller.getListQuestion();
    }
    private void loadview(){
        fragmentTabHost = (FragmentTabHost) findViewById(R.id.question_form_tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        fragmentTabHost = (FragmentTabHost) findViewById(R.id.question_form_tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        View tabHost0 = LayoutInflater.from(this).
                inflate(R.layout.tab_add_layout, null, false);
        identity = (LinearLayout) tabHost0.findViewById(R.id.tabLayoutIndicator);
        identityText = (TextView) tabHost0.findViewById(R.id.textTab);
        identity.setBackgroundColor(getResources().getColor(R.color.gray));
        identityText.setText("Identity");

        View tabHost1 = LayoutInflater.from(this).
                inflate(R.layout.tab_add_layout, null, false);
        question = (LinearLayout) tabHost1.findViewById(R.id.tabLayoutIndicator);
        questionText = (TextView) tabHost1.findViewById(R.id.textTab);
        question.setBackgroundColor(getResources().getColor(R.color.gray));
        questionText.setText("Question");


        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("IDENTITY").setIndicator(tabHost0), KYNQuestionFormIdentityFragment.class, null);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("QUESTION").setIndicator(tabHost1), KYNQuestionFormQuestionFragment.class, null);

        disableAllTab();
    }
    public void disableAllTab(){
        fragmentTabHost.getTabWidget().getChildTabViewAt(0).setEnabled(false);
        fragmentTabHost.getTabWidget().getChildTabViewAt(1).setEnabled(false);
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

    private void initDefaultValue(){
        activity = this;
        controller = new KYNQuestionFormController(activity);
    }

    public void onTabChange(String tabId) {
        this.tabId = tabId;
        if (tabId.equals("IDENTITY")) {
            application.setTabPosition(0);
            identityText.setBackgroundColor(getResources().getColor(R.color.light_gray));
            questionText.setBackgroundColor(getResources().getColor(R.color.transparent));

        } else if (tabId.equals("QUESTION")) {
            application.setTabPosition(1);
            application.setTabPosition(0);
            identityText.setBackgroundColor(getResources().getColor(R.color.transparent));
            questionText.setBackgroundColor(getResources().getColor(R.color.light_gray));
        }
    }
    public void onNextButtonClicked(int position) {
        application.setTabPosition(position);
        try {
            fragmentTabHost.setCurrentTab(position);
            fragmentTabHost.getTabWidget().getChildTabViewAt(position).setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setSelectedFragment(int position) {
        application.setTabPosition(position);
        try {
            fragmentTabHost.setCurrentTab(position);
        } catch (Exception e) {

        }
    }

    public FragmentTabHost getFragmentTabHost() {
        return fragmentTabHost;
    }

    public void setFragmentTabHost(FragmentTabHost fragmentTabHost) {
        this.fragmentTabHost = fragmentTabHost;
    }

    public List<KYNQuestionModel> getQuestionModels() {
        return questionModels;
    }

    public void setQuestionModels(List<KYNQuestionModel> questionModels) {
        this.questionModels = questionModels;
    }

    public KYNIntervieweeModel getIntervieweeModel() {
        return intervieweeModel;
    }

    public void setIntervieweeModel(KYNIntervieweeModel intervieweeModel) {
        this.intervieweeModel = intervieweeModel;
    }

    public KYNQuestionFormActivity getActivity() {
        return activity;
    }

    public void setActivity(KYNQuestionFormActivity activity) {
        this.activity = activity;
    }

    public KYNQuestionFormController getController() {
        return controller;
    }

    public void setController(KYNQuestionFormController controller) {
        this.controller = controller;
    }

    public KYNConfirmationAlertDialog getConfirmationAlertDialog() {
        return confirmationAlertDialog;
    }

    public void setConfirmationAlertDialog(KYNConfirmationAlertDialog confirmationAlertDialog) {
        this.confirmationAlertDialog = confirmationAlertDialog;
    }

    public Long getIntervieweeId() {
        return intervieweeId;
    }

    public void setIntervieweeId(Long intervieweeId) {
        this.intervieweeId = intervieweeId;
    }

    public LinearLayout getIdentity() {
        return identity;
    }

    public void setIdentity(LinearLayout identity) {
        this.identity = identity;
    }

    public LinearLayout getQuestion() {
        return question;
    }

    public void setQuestion(LinearLayout question) {
        this.question = question;
    }

    public TextView getIdentityText() {
        return identityText;
    }

    public void setIdentityText(TextView identityText) {
        this.identityText = identityText;
    }

    public TextView getQuestionText() {
        return questionText;
    }

    public void setQuestionText(TextView questionText) {
        this.questionText = questionText;
    }

    public String getTabId() {
        return tabId;
    }

    public void setTabId(String tabId) {
        this.tabId = tabId;
    }

    public KYNConfirmationAlertDialogListener getListener() {
        return listener;
    }

    public void setListener(KYNConfirmationAlertDialogListener listener) {
        this.listener = listener;
    }
}
