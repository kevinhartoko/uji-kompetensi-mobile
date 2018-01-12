package com.example.aldoduha.ujikompetensi.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.aldoduha.ujikompetensi.Fragment.Controller.KYNQuestionIdentityController;
import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.activity.KYNQuestionFormActivity;
import com.example.aldoduha.ujikompetensi.alertDialog.KYNDatePickerDialog;
import com.example.aldoduha.ujikompetensi.alertDialog.listener.KYNDatePickerDialogListener;
import com.example.aldoduha.ujikompetensi.connection.api.listener.KYNServiceConnection;
import com.example.aldoduha.ujikompetensi.model.KYNIntervieweeModel;
import com.example.aldoduha.ujikompetensi.model.KYNTemplateModel;
import com.example.aldoduha.ujikompetensi.model.KYNUserModel;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by aldoduha on 10/26/2017.
 */

public class KYNQuestionFormIdentityFragment extends KYNBaseFragment {
    private KYNQuestionIdentityController controller;
    private KYNDatabaseHelper database;
    private EditText editTextNama;
    private EditText editTextEmail;
    private EditText editTextHandphone;
    private EditText editTextAddress;
    private TextView textViewDOB;
    private Button buttonLanjut;
    private KYNDatePickerDialog datePickerDialog;
    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    private KYNQuestionFormActivity activity;
    private KYNIntervieweeModel intervieweeModel;
    private RadioButton radioLakilaki;
    private RadioButton radioPerempuan;
    private TextView genderErrorTextView;
    private Spinner templateSpinner;
    private Spinner categorySpinner;
    private ArrayAdapter adapter;
    private TextView templateErrorMessage;
    private TextView categoryErrorMessage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_question_form_identity, null);
        loadview();
        initiateDefaultValue();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setValueToView();
        if(controller != null)
            controller.registerLocalBroadCastReceiver();
    }

    @Override
    public void onPause() {
        if(controller != null)
            controller.unregisterLocalBroadCastReceiver();
        super.onPause();
    }

    private void loadview(){
        editTextNama = (EditText)view.findViewById(R.id.edittextNama);
        editTextEmail = (EditText)view.findViewById(R.id.edittextEmail);
        editTextHandphone = (EditText)view.findViewById(R.id.edittextHandphone);
        editTextAddress = (EditText)view.findViewById(R.id.edittextAddress);
        textViewDOB = (TextView)view.findViewById(R.id.textviewDOB);
        buttonLanjut = (Button)view.findViewById(R.id.btnLanjut);
        radioLakilaki = (RadioButton)view.findViewById(R.id.radioLakilaki);
        radioPerempuan = (RadioButton)view.findViewById(R.id.radioPerempuan);
        genderErrorTextView = (TextView)view.findViewById(R.id.genderErrorTextview);
        templateSpinner = (Spinner)view.findViewById(R.id.templateSpinner);
        categorySpinner = (Spinner)view.findViewById(R.id.categorySpinner);
        templateErrorMessage = (TextView)view.findViewById(R.id.template_error_message);
        categoryErrorMessage = (TextView)view.findViewById(R.id.category_error_message);
    }
    private void initiateDefaultValue(){
        activity = (KYNQuestionFormActivity)getActivity();
        database = new KYNDatabaseHelper(activity);
        controller = new KYNQuestionIdentityController(this);
        intervieweeModel = activity.getIntervieweeModel();
        buttonLanjut.setOnClickListener(controller);
        textViewDOB.setOnClickListener(controller);
    }
    public void setValueToModel(){
        intervieweeModel.setNama(editTextNama.getText().toString());
        intervieweeModel.setEmail(editTextEmail.getText().toString());
        intervieweeModel.setAddress(editTextAddress.getText().toString());
        intervieweeModel.setHandphone(editTextHandphone.getText().toString());
        if (!textViewDOB.getText().toString().isEmpty() && !textViewDOB.getText().toString().trim().equals("")) {
            try {
                intervieweeModel.setDob(format.parse(textViewDOB.getText().toString().trim()));
            } catch (ParseException e) {

            }
        }
        if(radioLakilaki.isChecked()){
            intervieweeModel.setGender("L");
        }else{
            intervieweeModel.setGender("P");
        }
        intervieweeModel.setCategory(categorySpinner.getSelectedItem().toString());
        if(intervieweeModel.getId()==null) {
            Long id = database.insertInterviewee(intervieweeModel);
            intervieweeModel.setId(id);
            activity.setIntervieweeId(id);
            activity.setIntervieweeModel(intervieweeModel);
            database.updateQuestionIntervieweeId(id);
        } else {
            database.updateInterviewee(intervieweeModel);
            activity.setIntervieweeModel(intervieweeModel);
        }
    }

    public void setValueToView(){
        if(intervieweeModel.getNama()!=null && !intervieweeModel.getNama().equals("")){
            editTextNama.setText(intervieweeModel.getNama());
        }else{
            editTextNama.setText("");
        }
        if(intervieweeModel.getEmail()!=null && !intervieweeModel.getEmail().equals("")){
            editTextEmail.setText(intervieweeModel.getEmail());
        }else{
            editTextEmail.setText("");
        }
        if(intervieweeModel.getHandphone()!=null && !intervieweeModel.getHandphone().equals("")){
            editTextHandphone.setText(intervieweeModel.getHandphone());
        }else{
            editTextHandphone.setText("");
        }
        if(intervieweeModel.getAddress()!=null && !intervieweeModel.getAddress().equals("")){
            editTextAddress.setText(intervieweeModel.getAddress());
        }else{
            editTextAddress.setText("");
        }
        if (intervieweeModel.getDob() != null && !intervieweeModel.getDob().equals("")) {
            textViewDOB.setText(format.format(intervieweeModel.getDob()));
        }
    }

    public void generateQuestion(){
        String template = templateSpinner.getSelectedItem().toString();
        String category = categorySpinner.getSelectedItem().toString();

        activity.showLoadingDialog(activity.getResources().getString(R.string.loading));
        KYNUserModel session = database.getSession();
        Intent intent = new Intent(activity, KYNServiceConnection.class);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_DATA, intervieweeModel);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_TEMPLATE, template);
        intent.putExtra(KYNIntentConstant.INTENT_EXTRA_CATEGORY, category);
        intent.setAction(KYNIntentConstant.ACTION_GENERATE_QUESTION);
        intent.addCategory(KYNIntentConstant.CATEGORY_GENERATE_QEUSTION);
        activity.startService(intent);
    }

    public static boolean isEmailValid(String email){
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }

    public boolean validate(){
        boolean result = true;
        String nama = editTextNama.getText().toString();
        String email = editTextEmail.getText().toString();
        String handphone = editTextHandphone.getText().toString();
        String address = editTextAddress.getText().toString();
        String dob = textViewDOB.getText().toString();
        String template = templateSpinner.getSelectedItem().toString();
        String category = categorySpinner.getSelectedItem().toString();

        editTextNama.setError(null);
        editTextEmail.setError(null);
        editTextHandphone.setError(null);
        editTextAddress.setError(null);
        textViewDOB.setError(null);
        if(nama==null||nama.equals("")){
            editTextNama.setError(Html.fromHtml("Name must be filled"));
            result=false;
        }
        if(email==null||email.equals("")){
            editTextEmail.setError(Html.fromHtml("Email must be filled"));
            result=false;
        }else{
            if(!isEmailValid(email)){
                editTextEmail.setError(Html.fromHtml("Invalid email format"));
                result=false;
            }
        }
        if(handphone==null||handphone.equals("")){
            editTextHandphone.setError(Html.fromHtml("Handphone must be filled"));
            result=false;
        }
        if(address==null||address.equals("")){
            editTextAddress.setError(Html.fromHtml("Address must be filled"));
            result=false;
        }
        if(dob==null||dob.equals("")){
            textViewDOB.setError(Html.fromHtml("DOB must be filled"));
            result=false;
        }
        if(!radioLakilaki.isChecked() && !radioPerempuan.isChecked()){
            genderErrorTextView.setVisibility(View.VISIBLE);
            result = false;
        }else{
            genderErrorTextView.setVisibility(View.GONE);
        }
        if(template==null||template.equals("")||template.equalsIgnoreCase("pilih")){
            templateErrorMessage.setVisibility(View.VISIBLE);
            result=false;
        }else{
            templateErrorMessage.setVisibility(View.GONE);
        }
        if(category==null||category.equals("")||category.equalsIgnoreCase("pilih")){
            categoryErrorMessage.setVisibility(View.VISIBLE);
            result=false;
        }else{
            categoryErrorMessage.setVisibility(View.GONE);
        }
        return result;
    }

    public void initiateTemplate(){
        List<String> listTemplate = new ArrayList<>();
        List<KYNTemplateModel> models = database.getTemplateList();
        for (KYNTemplateModel model : models){
            listTemplate.add(model.getNama());
        }
        adapter = new ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, listTemplate);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        templateSpinner.setAdapter(adapter);
    }

    public void initiateCategory(){
        adapter = new ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, KYNIntentConstant.category);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
    }

    public void showDialog(KYNDatePickerDialogListener listener, String headerTitle, String date){
        datePickerDialog = new KYNDatePickerDialog(activity, listener, headerTitle, date);
        datePickerDialog.setCanceledOnTouchOutside(false);
        datePickerDialog.show();
    }

    public void dismissDialog(){
        datePickerDialog.dismiss();
    }

    public String getDateValue(){
        if (datePickerDialog == null) {
            return null;
        }
        return datePickerDialog.getDate();
    }

    public EditText getEditTextNama() {
        return editTextNama;
    }

    public EditText getEditTextEmail() {
        return editTextEmail;
    }

    public EditText getEditTextHandphone() {
        return editTextHandphone;
    }

    public EditText getEditTextAddress() {
        return editTextAddress;
    }

    public TextView getTextViewDOB() {
        return textViewDOB;
    }

    public KYNQuestionFormActivity getNewActivity() {
        return activity;
    }

    public void setActivity(KYNQuestionFormActivity activity) {
        this.activity = activity;
    }
}
