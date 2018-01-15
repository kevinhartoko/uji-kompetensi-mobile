package com.example.aldoduha.ujikompetensi.connection.api.listener;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.connection.api.KYNAPIDeleteFeedback;
import com.example.aldoduha.ujikompetensi.connection.api.KYNAPIDeleteQuestion;
import com.example.aldoduha.ujikompetensi.connection.api.KYNAPIDeleteTemplate;
import com.example.aldoduha.ujikompetensi.connection.api.KYNAPIDeleteUser;
import com.example.aldoduha.ujikompetensi.connection.api.KYNAPIGenerateQuestion;
import com.example.aldoduha.ujikompetensi.connection.api.KYNAPIIntervieweeDetail;
import com.example.aldoduha.ujikompetensi.connection.api.KYNAPIIntervieweeList;
import com.example.aldoduha.ujikompetensi.connection.api.KYNAPILogin;
import com.example.aldoduha.ujikompetensi.connection.api.KYNAPILogout;
import com.example.aldoduha.ujikompetensi.connection.api.KYNAPIQuestionList;
import com.example.aldoduha.ujikompetensi.connection.api.KYNAPISubmitFeedback;
import com.example.aldoduha.ujikompetensi.connection.api.KYNAPISubmitIntervieweeData;
import com.example.aldoduha.ujikompetensi.connection.api.KYNAPISubmitQuestion;
import com.example.aldoduha.ujikompetensi.connection.api.KYNAPISubmitTemplate;
import com.example.aldoduha.ujikompetensi.connection.api.KYNAPISubmitUser;
import com.example.aldoduha.ujikompetensi.connection.api.KYNAPITemplateDetail;
import com.example.aldoduha.ujikompetensi.connection.api.KYNAPITemplateList;
import com.example.aldoduha.ujikompetensi.connection.api.KYNAPIUserList;
import com.example.aldoduha.ujikompetensi.connection.api.listener.impl.KYNAPIDeleteFeedbackListener;
import com.example.aldoduha.ujikompetensi.connection.api.listener.impl.KYNAPIDeleteQuestionListener;
import com.example.aldoduha.ujikompetensi.connection.api.listener.impl.KYNAPIDeleteTemplateListener;
import com.example.aldoduha.ujikompetensi.connection.api.listener.impl.KYNAPIDeleteUserListener;
import com.example.aldoduha.ujikompetensi.connection.api.listener.impl.KYNAPIGenerateQuestionListener;
import com.example.aldoduha.ujikompetensi.connection.api.listener.impl.KYNAPIIntervieweeDetailListener;
import com.example.aldoduha.ujikompetensi.connection.api.listener.impl.KYNAPIIntervieweeListListener;
import com.example.aldoduha.ujikompetensi.connection.api.listener.impl.KYNAPILoginListener;
import com.example.aldoduha.ujikompetensi.connection.api.listener.impl.KYNAPILogoutListener;
import com.example.aldoduha.ujikompetensi.connection.api.listener.impl.KYNAPIQuestionListListener;
import com.example.aldoduha.ujikompetensi.connection.api.listener.impl.KYNAPISubmitFeedbackListener;
import com.example.aldoduha.ujikompetensi.connection.api.listener.impl.KYNAPISubmitIntervieweeDataListener;
import com.example.aldoduha.ujikompetensi.connection.api.listener.impl.KYNAPISubmitQuestionListener;
import com.example.aldoduha.ujikompetensi.connection.api.listener.impl.KYNAPISubmitTemplateListener;
import com.example.aldoduha.ujikompetensi.connection.api.listener.impl.KYNAPISubmitUserListener;
import com.example.aldoduha.ujikompetensi.connection.api.listener.impl.KYNAPITemplateDetailListener;
import com.example.aldoduha.ujikompetensi.connection.api.listener.impl.KYNAPITemplateListListener;
import com.example.aldoduha.ujikompetensi.connection.api.listener.impl.KYNAPIUserListListener;
import com.example.aldoduha.ujikompetensi.model.KYNFeedbackModel;
import com.example.aldoduha.ujikompetensi.model.KYNIntervieweeModel;
import com.example.aldoduha.ujikompetensi.model.KYNQuestionModel;
import com.example.aldoduha.ujikompetensi.model.KYNTemplateModel;
import com.example.aldoduha.ujikompetensi.model.KYNUserModel;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aldoduha on 12/18/2017.
 */

public class KYNServiceConnection extends Service {
    private KYNDatabaseHelper database;
    private List<String> listPhoneNumberCustomerForAction = new ArrayList<String>();

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        SQLiteDatabase.loadLibs(getApplicationContext());

        database = new KYNDatabaseHelper(getApplicationContext());
        registerLocalBroadcastReceiver();
    }

    @Override
    public void onDestroy() {
        database.close();
        unregisterLocalBroadcastReceiver();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent==null)
            return super.onStartCommand(intent, flags, startId);

        if (intent.getAction().equals(KYNIntentConstant.ACTION_LOGIN)) {
            requestLogin(intent);
        }else if (intent.getAction().equals(KYNIntentConstant.ACTION_LOGOUT)) {
            requestLogout(intent);
        }else if (intent.getAction().equals(KYNIntentConstant.ACTION_USER_LIST)) {
            requestUserList(intent);
        }else if (intent.getAction().equals(KYNIntentConstant.ACTION_QUESTION_LIST)) {
            requestQuestionList(intent);
        }else if (intent.getAction().equals(KYNIntentConstant.ACTION_TEMPLATE_LIST)) {
            requestTemplateList(intent);
        }else if (intent.getAction().equals(KYNIntentConstant.ACTION_INTERVIEWEE_LIST)) {
            requestIntervieweeList(intent);
        }else if (intent.getAction().equals(KYNIntentConstant.ACTION_GENERATE_QUESTION)) {
            requestGenerateQuestion(intent);
        }else if (intent.getAction().equals(KYNIntentConstant.ACTION_SUBMIT_QUESTION)) {
            requestSubmitQuestion(intent);
        }else if (intent.getAction().equals(KYNIntentConstant.ACTION_SUBMIT_USER)) {
            requestSubmitUser(intent);
        }else if (intent.getAction().equals(KYNIntentConstant.ACTION_SUBMIT_TEMPLATE)) {
            requestSubmitTemplate(intent);
        }else if (intent.getAction().equals(KYNIntentConstant.ACTION_SUBMIT_FEEDBACK)) {
            requestSubmitFeedback(intent);
        }else if (intent.getAction().equals(KYNIntentConstant.ACTION_SUBMIT_INTERVIEWEE_DATA)) {
            requestSubmitIntervieweeData(intent);
        }else if (intent.getAction().equals(KYNIntentConstant.ACTION_INTERVIEWEE_DETAIL)) {
            requestIntervieweeDetail(intent);
        }else if (intent.getAction().equals(KYNIntentConstant.ACTION_DELETE_QUESTION)) {
            requestDeleteQuestion(intent);
        }else if (intent.getAction().equals(KYNIntentConstant.ACTION_DELETE_USER)) {
            requestDeleteUser(intent);
        }else if (intent.getAction().equals(KYNIntentConstant.ACTION_DELETE_TEMPLATE)) {
            requestDeleteTemplate(intent);
        }else if (intent.getAction().equals(KYNIntentConstant.ACTION_DELETE_FEEDBACK)) {
            requestDeleteFeedback(intent);
        }else if (intent.getAction().equals(KYNIntentConstant.ACTION_TEMPLATE_DETAIL)) {
            requestTemplateDetail(intent);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void requestLogin(Intent intent){
        KYNUserModel userModel = (KYNUserModel)intent.getSerializableExtra(KYNIntentConstant.INTENT_EXTRA_DATA);

        KYNAPILogin requestLogin = new KYNAPILogin(getApplicationContext(), new KYNAPILoginListener());
        requestLogin.setData(userModel);
        requestLogin.execute();
    }

    private void requestLogout(Intent intent){
        KYNUserModel userModel = (KYNUserModel)intent.getSerializableExtra(KYNIntentConstant.INTENT_EXTRA_DATA);

        KYNAPILogout requestLogout = new KYNAPILogout(getApplicationContext(), new KYNAPILogoutListener());
        requestLogout.setData(userModel);
        requestLogout.execute();
    }

    private void requestUserList(Intent intent){
        KYNUserModel userModel = (KYNUserModel)intent.getSerializableExtra(KYNIntentConstant.INTENT_EXTRA_DATA);

        KYNAPIUserList requestUserList = new KYNAPIUserList(getApplicationContext(), new KYNAPIUserListListener());
        requestUserList.setData(userModel);
        requestUserList.execute();
    }

    private void requestQuestionList(Intent intent){
        String category = intent.getStringExtra(KYNIntentConstant.INTENT_EXTRA_CATEGORY);

        KYNAPIQuestionList requestQuestionList = new KYNAPIQuestionList(getApplicationContext(), new KYNAPIQuestionListListener());
        requestQuestionList.setData(category);
        requestQuestionList.execute();
    }

    private void requestTemplateList(Intent intent){
        KYNUserModel userModel = (KYNUserModel)intent.getSerializableExtra(KYNIntentConstant.INTENT_EXTRA_DATA);

        KYNAPITemplateList requestTemplateList = new KYNAPITemplateList(getApplicationContext(), new KYNAPITemplateListListener());
        requestTemplateList.setData(userModel);
        requestTemplateList.execute();
    }

    private void requestIntervieweeList(Intent intent){
        KYNUserModel userModel = (KYNUserModel)intent.getSerializableExtra(KYNIntentConstant.INTENT_EXTRA_DATA);

        KYNAPIIntervieweeList requestIntervieweeList = new KYNAPIIntervieweeList(getApplicationContext(), new KYNAPIIntervieweeListListener());
        requestIntervieweeList.setData(userModel);
        requestIntervieweeList.execute();
    }

    private void requestGenerateQuestion(Intent intent){
        KYNIntervieweeModel intervieweeModel= (KYNIntervieweeModel)intent.getSerializableExtra(KYNIntentConstant.INTENT_EXTRA_DATA);
        String template = intent.getStringExtra(KYNIntentConstant.INTENT_EXTRA_TEMPLATE);
        String category = intent.getStringExtra(KYNIntentConstant.INTENT_EXTRA_CATEGORY);

        KYNAPIGenerateQuestion requestGenerateQuestion = new KYNAPIGenerateQuestion(getApplicationContext(), new KYNAPIGenerateQuestionListener());
        requestGenerateQuestion.setData(intervieweeModel, template, category);
        requestGenerateQuestion.execute();
    }

    private void requestSubmitQuestion(Intent intent){
        KYNQuestionModel questionModel = (KYNQuestionModel) intent.getSerializableExtra(KYNIntentConstant.INTENT_EXTRA_DATA);
        String username = intent.getStringExtra(KYNIntentConstant.INTENT_EXTRA_USERNAME);

        KYNAPISubmitQuestion requestSubmitQuestion = new KYNAPISubmitQuestion(getApplicationContext(), new KYNAPISubmitQuestionListener());
        requestSubmitQuestion.setData(username, questionModel);
        requestSubmitQuestion.execute();
    }

    private void requestSubmitUser(Intent intent){
        KYNUserModel userModel = (KYNUserModel) intent.getSerializableExtra(KYNIntentConstant.INTENT_EXTRA_DATA);
        String username = intent.getStringExtra(KYNIntentConstant.INTENT_EXTRA_USERNAME);

        KYNAPISubmitUser requestSubmitUser = new KYNAPISubmitUser(getApplicationContext(), new KYNAPISubmitUserListener());
        requestSubmitUser.setData(username, userModel);
        requestSubmitUser.execute();
    }

    private void requestSubmitTemplate(Intent intent){
        KYNTemplateModel templateModel = (KYNTemplateModel) intent.getSerializableExtra(KYNIntentConstant.INTENT_EXTRA_DATA);
        String username = intent.getStringExtra(KYNIntentConstant.INTENT_EXTRA_USERNAME);

        KYNAPISubmitTemplate requestSubmitTemplate = new KYNAPISubmitTemplate(getApplicationContext(), new KYNAPISubmitTemplateListener());
        requestSubmitTemplate.setData(username, templateModel);
        requestSubmitTemplate.execute();
    }

    private void requestSubmitFeedback(Intent intent){
        KYNFeedbackModel feedbackModel = (KYNFeedbackModel) intent.getSerializableExtra(KYNIntentConstant.INTENT_EXTRA_DATA);
        String username = intent.getStringExtra(KYNIntentConstant.INTENT_EXTRA_USERNAME);

        KYNAPISubmitFeedback requestSubmitFeedback = new KYNAPISubmitFeedback(getApplicationContext(), new KYNAPISubmitFeedbackListener());
        requestSubmitFeedback.setData(username, feedbackModel);
        requestSubmitFeedback.execute();
    }

    private void requestSubmitIntervieweeData(Intent intent){
        KYNIntervieweeModel intervieweeModel = (KYNIntervieweeModel) intent.getSerializableExtra(KYNIntentConstant.INTENT_EXTRA_DATA);
        String username = intent.getStringExtra(KYNIntentConstant.INTENT_EXTRA_USERNAME);

        KYNAPISubmitIntervieweeData requestSubmitIntervieweeData = new KYNAPISubmitIntervieweeData(getApplicationContext(), new KYNAPISubmitIntervieweeDataListener());
        requestSubmitIntervieweeData.setData(username, intervieweeModel);
        requestSubmitIntervieweeData.execute();
    }

    private void requestIntervieweeDetail(Intent intent){
        KYNIntervieweeModel intervieweeModel = (KYNIntervieweeModel) intent.getSerializableExtra(KYNIntentConstant.INTENT_EXTRA_DATA);
        String username = intent.getStringExtra(KYNIntentConstant.INTENT_EXTRA_USERNAME);

        KYNAPIIntervieweeDetail requestIntervieweeDetail = new KYNAPIIntervieweeDetail(getApplicationContext(), new KYNAPIIntervieweeDetailListener());
        requestIntervieweeDetail.setData(username, intervieweeModel);
        requestIntervieweeDetail.execute();
    }
    private void requestDeleteQuestion(Intent intent){
        KYNQuestionModel questionModel = (KYNQuestionModel) intent.getSerializableExtra(KYNIntentConstant.INTENT_EXTRA_DATA);
        String username = intent.getStringExtra(KYNIntentConstant.INTENT_EXTRA_USERNAME);

        KYNAPIDeleteQuestion requestDeleteQuestion = new KYNAPIDeleteQuestion(getApplicationContext(), new KYNAPIDeleteQuestionListener());
        requestDeleteQuestion.setData(username, questionModel);
        requestDeleteQuestion.execute();
    }
    private void requestDeleteUser(Intent intent){
        KYNUserModel userModel = (KYNUserModel) intent.getSerializableExtra(KYNIntentConstant.INTENT_EXTRA_DATA);
        String username = intent.getStringExtra(KYNIntentConstant.INTENT_EXTRA_USERNAME);

        KYNAPIDeleteUser requestDeleteUser = new KYNAPIDeleteUser(getApplicationContext(), new KYNAPIDeleteUserListener());
        requestDeleteUser.setData(username, userModel);
        requestDeleteUser.execute();
    }
    private void requestDeleteTemplate(Intent intent){
        KYNTemplateModel templateModel = (KYNTemplateModel) intent.getSerializableExtra(KYNIntentConstant.INTENT_EXTRA_DATA);
        String username = intent.getStringExtra(KYNIntentConstant.INTENT_EXTRA_USERNAME);

        KYNAPIDeleteTemplate requestDeleteTemplate = new KYNAPIDeleteTemplate(getApplicationContext(), new KYNAPIDeleteTemplateListener());
        requestDeleteTemplate.setData(username, templateModel);
        requestDeleteTemplate.execute();
    }
    private void requestDeleteFeedback(Intent intent){
        KYNFeedbackModel feedbackModel = (KYNFeedbackModel) intent.getSerializableExtra(KYNIntentConstant.INTENT_EXTRA_DATA);
        String username = intent.getStringExtra(KYNIntentConstant.INTENT_EXTRA_USERNAME);

        KYNAPIDeleteFeedback requestDeleteFeedback = new KYNAPIDeleteFeedback(getApplicationContext(), new KYNAPIDeleteFeedbackListener());
        requestDeleteFeedback.setData(username, feedbackModel);
        requestDeleteFeedback.execute();
    }
    private void requestTemplateDetail(Intent intent){
        KYNTemplateModel templateModel = (KYNTemplateModel) intent.getSerializableExtra(KYNIntentConstant.INTENT_EXTRA_DATA);
        String username = intent.getStringExtra(KYNIntentConstant.INTENT_EXTRA_USERNAME);

        KYNAPITemplateDetail requestTemplateDetail = new KYNAPITemplateDetail(getApplicationContext(), new KYNAPITemplateDetailListener());
        requestTemplateDetail.setData(username, templateModel);
        requestTemplateDetail.execute();
    }

    private void registerLocalBroadcastReceiver(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(KYNIntentConstant.ACTION_LOGIN);
        intentFilter.addCategory(KYNIntentConstant.CATEGORY_LOGIN);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(broadcastReceiver, intentFilter);
    }

    private void unregisterLocalBroadcastReceiver(){
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(broadcastReceiver);
    }
}
