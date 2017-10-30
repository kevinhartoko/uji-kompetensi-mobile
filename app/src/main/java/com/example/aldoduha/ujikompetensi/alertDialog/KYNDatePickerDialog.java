package com.example.aldoduha.ujikompetensi.alertDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNDatePickerDialogListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by aldoduha on 10/26/2017.
 */

public class KYNDatePickerDialog extends AlertDialog implements View.OnClickListener {
    private View view;
    private TextView dialogHeader;
    private DatePicker datePicker;
    private Button submitButton;
    private Button cancelButton;
    private String date;
    private String headerTitle;
    private SimpleDateFormat dateformat;
    private KYNDatePickerDialogListener listener;
    private Date dates;
    public KYNDatePickerDialog(Context context, KYNDatePickerDialogListener listener, String headerTitle, String date) {
        super(context);
        this.listener = listener;
        this.headerTitle = headerTitle;
        this.date = date;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getContext()).inflate(R.layout.alert_dialog_date_picker, null);
        setContentView(view);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        loadview();
        initiateDefaultValue();
    }

    private void loadview() {
        dialogHeader = (TextView)view.findViewById(R.id.dialogTitle);
        datePicker = (DatePicker)view.findViewById(R.id.datePicker);
        cancelButton = (Button)view.findViewById(R.id.cancelButton);
        submitButton = (Button)view.findViewById(R.id.submitButton);
    }

    private void initiateDefaultValue() {
        initDateToday();
        submitButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        dialogHeader.setText(headerTitle);
        dateformat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar cal = Calendar.getInstance();
        if(date!=null && !date.equals("")){
            Date setDate = null;
            try {
                setDate = dateformat.parse(date);
            } catch (java.text.ParseException e) {

            }
            cal.setTime(setDate);
            datePicker.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        }else{
            cal.set(Calendar.YEAR,datePicker.getYear());
            cal.set(Calendar.MONTH, datePicker.getMonth());
            cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
            datePicker.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        }
    }

    private void initDateToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,datePicker.getYear());
        cal.set(Calendar.MONTH, datePicker.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
        dates = cal.getTime();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitButton:
                datePicker.getFocusedChild().clearFocus();
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR,datePicker.getYear());
                cal.set(Calendar.MONTH, datePicker.getMonth());
                cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                Date date = cal.getTime();
                listener.onSubmit();
                break;
            case R.id.cancelButton:
                listener.onCancel();
                break;
            default:
                break;
        }
    }

    public String getDate(){
        datePicker.clearFocus();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,datePicker.getYear());
        cal.set(Calendar.MONTH, datePicker.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
        Date date = cal.getTime();
        String dateText = dateformat.format(date);
        return dateText;
    }
}
