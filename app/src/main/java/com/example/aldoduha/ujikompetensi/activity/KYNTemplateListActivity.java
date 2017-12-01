package com.example.aldoduha.ujikompetensi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aldoduha.ujikompetensi.KYNBaseActivity;
import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.controller.KYNTemplateListController;
import com.example.aldoduha.ujikompetensi.alertDialog.KYNConfirmationAlertDialog;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNConfirmationAlertDialogListener;
import com.example.aldoduha.ujikompetensi.model.KYNQuestionModel;
import com.example.aldoduha.ujikompetensi.model.KYNTemplateModel;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

import java.util.List;

/**
 * Created by aldoduha on 11/12/2017.
 */

public class KYNTemplateListActivity extends KYNBaseActivity {
    private KYNTemplateListController controller;
    private KYNTemplateListActivity activity;
    private KYNDatabaseHelper database;
    private KYNConfirmationAlertDialog confirmationAlertDialog;
    private Button backButton;
    private Button addButton;
    private TableLayout tableLayout;
    private TableRow tableRow;
    private TextView textViewNama;
    private TextView textViewJumlahSoal;
    private TextView textViewFunction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        this.database = new KYNDatabaseHelper(activity);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_list);
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
            case KYNIntentConstant.REQUEST_CODE_TEMPLATE_DETAIL:
                List<KYNTemplateModel> templateModels = database.getTemplateList();
                generateTable(templateModels);
                break;
            default:
                break;
        }
    }

    private void loadview() {
        backButton = (Button) findViewById(R.id.buttonKembali);
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        tableRow = (TableRow) findViewById(R.id.tableRow);
        addButton = (Button) findViewById(R.id.buttonTambah);
        textViewNama = (TextView) findViewById(R.id.textViewNama);
        textViewJumlahSoal = (TextView) findViewById(R.id.textViewJumlah);
        textViewFunction = (TextView) findViewById(R.id.textViewFunction);
    }

    private void initiateDefaultValue() {
        controller = new KYNTemplateListController(this);
    }

    private void setMethod() {
        backButton.setOnClickListener(controller);
        addButton.setOnClickListener(controller);
    }

    public void generateTable(List<KYNTemplateModel> templates) {
        tableLayout.removeAllViews();
        tableLayout.addView(tableRow);
        for (final KYNTemplateModel templateModel : templates) {
            final TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            tableRow.setOrientation(LinearLayout.HORIZONTAL);
            tableRow.setWeightSum(1);
            // Creation  button edit
            final Button button = new Button(this);
            button.setText("Edit");
            button.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT));
            button.setWidth(textViewFunction.getWidth());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Long id = templateModel.getId();
                    viewClicked(id);
                }
            });
            // Creation textView nama
            final TextView textNama = new TextView(this);
            textNama.setText(templateModel.getNama());
            textNama.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT));
            textNama.setGravity(Gravity.CENTER);
            textNama.setWidth(textViewNama.getWidth());
            // Creation textView Bobot
            final TextView textJumlah = new TextView(this);
            textJumlah.setText(templateModel.getJumlahSoal()+"");
            textJumlah.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT));
            textJumlah.setGravity(Gravity.CENTER);
            textJumlah.setWidth(textViewJumlahSoal.getWidth());

            tableRow.addView(button);
            tableRow.addView(textNama);
            tableRow.addView(textJumlah);

            tableLayout.addView(tableRow);
        }
    }

    public void viewClicked(Long id) {
        Bundle b = new Bundle();
        b.putLong("templateId", id);
        Intent i = new Intent(this, KYNTemplateDetailActivity.class);
        i.putExtras(b);
        this.startActivityForResult(i, KYNIntentConstant.REQUEST_CODE_TEMPLATE_DETAIL);
    }

    public void showOnBackPressAlertDialog(KYNConfirmationAlertDialogListener listener) {
        if (confirmationAlertDialog == null) {
            confirmationAlertDialog = new KYNConfirmationAlertDialog(this, listener, "Apakah anda ingin keluar?");
        }
        confirmationAlertDialog.setCancelable(false);
        confirmationAlertDialog.setCanceledOnTouchOutside(false);
        confirmationAlertDialog.show();
    }

    public void onAddButtonClicked() {
        Bundle b = new Bundle();
        Intent i = new Intent(this, KYNTemplateDetailActivity.class);
        i.putExtras(b);
        this.startActivityForResult(i, KYNIntentConstant.REQUEST_CODE_TEMPLATE_DETAIL);
    }
}
