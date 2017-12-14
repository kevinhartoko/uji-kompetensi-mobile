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
import com.example.aldoduha.ujikompetensi.model.KYNIntervieweeModel;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        this.database = new KYNDatabaseHelper(activity);
        insertInterviewee();
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
            //controller.updateAttachmentUI();
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
                List<KYNIntervieweeModel> intervieweeModels = database.getListInterviewee();
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
            confirmationAlertDialog = new KYNConfirmationAlertDialog(this, listener, "Apakah anda ingin keluar?");
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
        for (int i=0;i<2;i++){
            KYNIntervieweeModel model = new KYNIntervieweeModel();
            if(i==0) {
                model.setNama("Richard");
                model.setGender("L");
            } else {
                model.setNama("Natasha");
                model.setGender("P");
            }
            model.setDob(new Date());
            model.setAddress("jln sunter hijau 1 blok y1 no"+i);
            model.setHandphone("081811853"+i);
            model.setEmail("asdasd@yahoo.com");
            database.insertInterviewee(model);
        }
    }

    public KYNIntervieweeListAdapter getAdapter() {
        return adapter;
    }
}
