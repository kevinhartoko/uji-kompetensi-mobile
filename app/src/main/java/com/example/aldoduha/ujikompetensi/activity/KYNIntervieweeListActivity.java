package com.example.aldoduha.ujikompetensi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aldoduha.ujikompetensi.KYNBaseActivity;
import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.controller.KYNIntervieweeListController;
import com.example.aldoduha.ujikompetensi.adapter.KYNIntervieweeListAdapter;
import com.example.aldoduha.ujikompetensi.alertDialog.KYNConfirmationAlertDialog;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNConfirmationAlertDialogListener;
import com.example.aldoduha.ujikompetensi.connection.KYNSMPUtilities;
import com.example.aldoduha.ujikompetensi.connection.api.listener.KYNServiceConnection;
import com.example.aldoduha.ujikompetensi.model.KYNFeedbackModel;
import com.example.aldoduha.ujikompetensi.model.KYNIntervieweeModel;
import com.example.aldoduha.ujikompetensi.model.KYNQuestionModel;
import com.example.aldoduha.ujikompetensi.model.KYNTemplateModel;
import com.example.aldoduha.ujikompetensi.model.KYNUserModel;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by aldoduha on 12/10/2017.
 */

public class KYNIntervieweeListActivity extends KYNBaseActivity {
    private KYNIntervieweeListController controller;
    private KYNIntervieweeListActivity activity;
    private KYNDatabaseHelper database;
    private KYNConfirmationAlertDialog confirmationAlertDialog;
    private ListView listView;
    private KYNIntervieweeListAdapter adapter;
    private Button refreshButton;
    private EditText searchEdittext;
    private Long intervieweeId;
    private String category="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        category = getIntent().getStringExtra(KYNIntentConstant.INTENT_EXTRA_CATEGORY);
        this.database = new KYNDatabaseHelper(activity);
        if(!KYNSMPUtilities.isConnectServer) {
            insertInterviewee();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interviewee_list);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        loadview();
        initiateDefaultValue();
        setMethod();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        if (controller != null) {
            initiateDefaultValue();
            controller.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (controller != null) {
            controller.onPause();
        }
        super.onPause();
    }

    @Override
    public void onLowMemory() {
        Toast.makeText(activity, "Your device is running low. Please Restart.", Toast.LENGTH_LONG);
        super.onLowMemory();
    }

    @Override
    public void onDestroy() {
        if (controller != null) {
            controller.onDestory();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (controller != null) {
            controller.showOnBackPressAlertDialog();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case KYNIntentConstant.REQUEST_CODE_INTERVIEWEE_DETAIL:
                List<KYNIntervieweeModel> intervieweeModels = database.getListIntervieweeByCategory(category);
                generateList(intervieweeModels);
                break;
            default:
                break;
        }
    }

    private void loadview() {
        refreshButton = (Button) findViewById(R.id.btnRefresh);
        listView = (ListView)findViewById(R.id.interviewee_listview);
        searchEdittext = (EditText)findViewById(R.id.edittextSearch);
    }

    private void initiateDefaultValue() {
        adapter = new KYNIntervieweeListAdapter(this, R.layout.adapter_interviewee_list, new ArrayList<KYNIntervieweeModel>());
        listView.setAdapter(adapter);
        controller = new KYNIntervieweeListController(this);
        listView.setOnItemClickListener(controller);
    }

    private void setMethod() {
        refreshButton.setOnClickListener(controller);
        searchEdittext.addTextChangedListener(controller);
    }

    public void generateList(List<KYNIntervieweeModel> intervieweeModels) {
        listView.setVisibility(View.VISIBLE);
        adapter.updateList(intervieweeModels);
    }

    public void updateListView(List<KYNIntervieweeModel> intervieweeList){
        if(listView !=null) {
            listView.setVisibility(View.VISIBLE);

            if(controller.getIntervieweeModels() != null) {
                adapter.updateList(intervieweeList);
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void showOnBackPressAlertDialog(KYNConfirmationAlertDialogListener listener) {
        if (confirmationAlertDialog == null) {
            confirmationAlertDialog = new KYNConfirmationAlertDialog(this, listener, "Are you sure to go back?");
        }
        confirmationAlertDialog.setCancelable(false);
        confirmationAlertDialog.setCanceledOnTouchOutside(false);
        confirmationAlertDialog.show();
    }

    public EditText getSearchEdittext() {
        return searchEdittext;
    }

    public void setSearchEdittext(EditText searchEdittext) {
        this.searchEdittext = searchEdittext;
    }

    public void insertInterviewee(){
        database.deleteInterviewee();
        database.deleteFeedback();
        database.deleteQuestion();
        for (int i=0;i<2;i++){
            KYNIntervieweeModel model = new KYNIntervieweeModel();
            if(i==0) {
                model.setNama("Richard");
                model.setGender("L");
                model.setScore(90);
            } else {
                model.setNama("Natasha");
                model.setGender("P");
                model.setScore(100);
            }
            model.setDob(new Date());
            model.setAddress("jln sunter hijau 1 blok y1 no"+i);
            model.setHandphone("081811853"+i);
            model.setEmail("asdasd@yahoo.com");
            model.setId(database.insertInterviewee(model));

            KYNFeedbackModel feedbackModel = new KYNFeedbackModel();
            feedbackModel.setName("a");
            feedbackModel.setIntervieweeModel(model);
            feedbackModel.setDescription("bagus");
            database.insertFeedback(feedbackModel);
            feedbackModel.setName("b");
            feedbackModel.setDescription("bagus banget");
            database.insertFeedback(feedbackModel);
            for (int y = 1; y <= 10; y++) {
                KYNQuestionModel questionModel = new KYNQuestionModel();
                questionModel.setQuestion("Pertanyaan " + y);
                questionModel.setAnswer1("answer 1-" + y);
                questionModel.setAnswer2("answer 2-" + y);
                questionModel.setAnswer3("answer 3-" + y);
                questionModel.setAnswer4("answer 4-" + y);
                questionModel.setKeyAnswer("answer 1-" + y);
                questionModel.setBobot(i);
                questionModel.setName("code"+i);
                if(y==1||y==5||y==9)
                    questionModel.setIntervieweeAnswer("answer 1-" + y);
                else if(y==2||y==6||y==10)
                    questionModel.setIntervieweeAnswer("answer 2-" + y);
                else if(y==3||y==7)
                    questionModel.setIntervieweeAnswer("answer 3-" + y);
                else if(y==4||y==8)
                    questionModel.setIntervieweeAnswer("answer 4-" + y);
                questionModel.setIntervieweeModel(model);
                database.insertQuestion(questionModel);
            }
        }
    }

    public void getIntervieweeDetail(KYNIntervieweeModel model){
        showLoadingDialog(getResources().getString(R.string.loading));
        KYNUserModel session = database.getSession();
        Intent intent = new Intent(this, KYNServiceConnection.class);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_DATA, model);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_USERNAME, session.getUsername());
        intent.setAction(KYNIntentConstant.ACTION_INTERVIEWEE_DETAIL);
        intent.addCategory(KYNIntentConstant.CATEGORY_INTERVIEWEE_DETAIL);
        startService(intent);
    }

    public KYNIntervieweeListAdapter getAdapter() {
        return adapter;
    }

    public Long getIntervieweeId() {
        return intervieweeId;
    }

    public void setIntervieweeId(Long intervieweeId) {
        this.intervieweeId = intervieweeId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
