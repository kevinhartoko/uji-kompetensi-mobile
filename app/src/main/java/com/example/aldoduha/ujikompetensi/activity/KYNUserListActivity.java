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
import com.example.aldoduha.ujikompetensi.activity.controller.KYNUserListController;
import com.example.aldoduha.ujikompetensi.alertDialog.KYNConfirmationAlertDialog;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNConfirmationAlertDialogListener;
import com.example.aldoduha.ujikompetensi.model.KYNUserModel;

import java.util.List;

/**
 * Created by aldoduha on 11/5/2017.
 */

public class KYNUserListActivity extends KYNBaseActivity{
    private KYNUserListController controller;
    private KYNUserListActivity activity;
    private KYNDatabaseHelper database;
    private KYNConfirmationAlertDialog confirmationAlertDialog;
    private Button backButton;
    private Button addButton;
    private TableLayout tableLayout;
    private TableRow tableRow;
    private TextView textViewNama;
    private TextView textViewUsername;
    private TextView textViewRole;
    private TextView textViewFunction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        this.database = new KYNDatabaseHelper(activity);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
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
    private void loadview(){
        backButton = (Button) findViewById(R.id.buttonKembali);
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        tableRow = (TableRow) findViewById(R.id.tableRow);
        addButton = (Button) findViewById(R.id.buttonTambah);
        textViewNama = (TextView)findViewById(R.id.textViewNama);
        textViewUsername =(TextView)findViewById(R.id.textViewUsername);
        textViewRole = (TextView)findViewById(R.id.textViewRole);
        textViewFunction = (TextView)findViewById(R.id.textViewFunction);
    }

    private void initiateDefaultValue(){
        controller = new KYNUserListController(this);
    }

    private void setMethod(){
        backButton.setOnClickListener(controller);
        addButton.setOnClickListener(controller);
    }

    public void generateTable(List<KYNUserModel> users) {
        tableLayout.removeAllViews();
        tableLayout.addView(tableRow);
        for (final KYNUserModel userModel : users) {
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
                    Long id = userModel.getId();
                    viewClicked(id);
                }
            });
            // Creation textView nama
            final TextView textNama = new TextView(this);
            textNama.setText(userModel.getNama());
            textNama.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT));
            textNama.setGravity(Gravity.CENTER);
            textNama.setWidth(textViewNama.getWidth());
            // Creation textView Username
            final TextView textUsername = new TextView(this);
            textUsername.setText(userModel.getUsername());
            textUsername.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT));
            textUsername.setGravity(Gravity.CENTER);
            textUsername.setWidth(textViewUsername.getWidth());
            // Creation textView Role
            final TextView textRole = new TextView(this);
            textRole.setText(userModel.getRole());
            textRole.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT));
            textRole.setGravity(Gravity.CENTER);
            textRole.setWidth(textViewRole.getWidth());

            tableRow.addView(new TextView(this));
            tableRow.addView(new TextView(this));
            tableRow.addView(button);
            tableRow.addView(textNama);
            tableRow.addView(textUsername);
            tableRow.addView(textRole);

            tableLayout.addView(tableRow);
        }
    }
    public void viewClicked(Long id) {
        Bundle b = new Bundle();
        b.putLong("kirimId", id);
        Intent i = new Intent(this, KYNUserDetailActivity.class);
        i.putExtras(b);
        this.startActivity(i);
    }
    public void showOnBackPressAlertDialog(KYNConfirmationAlertDialogListener listener) {
        if (confirmationAlertDialog == null) {
            confirmationAlertDialog = new KYNConfirmationAlertDialog(this, listener, "");
        }
        confirmationAlertDialog.setCancelable(false);
        confirmationAlertDialog.setCanceledOnTouchOutside(false);
        confirmationAlertDialog.show();
    }
    public void onAddButtonClicked() {
        Bundle b = new Bundle();
        Intent i = new Intent(this, KYNUserDetailActivity.class);
        i.putExtras(b);
        this.startActivity(i);
    }
}
