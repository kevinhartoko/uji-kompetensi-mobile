package com.example.aldoduha.ujikompetensi.connection.api;

import android.content.Context;
import android.os.Bundle;

import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.connection.KYNHTTPPostConnections;
import com.example.aldoduha.ujikompetensi.connection.KYNSMPUtilities;
import com.example.aldoduha.ujikompetensi.connection.listener.KYNConnectionListener;
import com.example.aldoduha.ujikompetensi.model.KYNFeedbackModel;
import com.example.aldoduha.ujikompetensi.model.KYNIntervieweeModel;
import com.example.aldoduha.ujikompetensi.model.KYNQuestionModel;
import com.example.aldoduha.ujikompetensi.model.KYNTemplateQuestionModel;
import com.example.aldoduha.ujikompetensi.model.KYNUserModel;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;
import com.example.aldoduha.ujikompetensi.utility.KYNJSONKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aldoduha on 12/28/2017.
 */

public class KYNAPIIntervieweeDetail extends KYNHTTPPostConnections {
    private KYNIntervieweeModel intervieweeModel;
    private String username ="";
    private KYNDatabaseHelper database;

    public KYNAPIIntervieweeDetail(Context applicationContext, KYNConnectionListener listener) {
        super(applicationContext, listener);
        database = new KYNDatabaseHelper(applicationContext);
    }

    @Override
    protected Bundle generateBundleOnRequestSuccess(String responseString) {
        try {
            Bundle bundle = new Bundle();
            JSONObject jsonResponse = new JSONObject(responseString);
            if(jsonResponse.has(KYNJSONKey.KEY_QUESTION)){
                database.deleteQuestion();
                JSONArray jsonArrayQuestion = new JSONArray(jsonResponse.getString(KYNJSONKey.KEY_QUESTION));
                for(int i=0;i<jsonArrayQuestion.length();i++){
                    KYNQuestionModel questionModel= new KYNQuestionModel((JSONObject)jsonArrayQuestion.get(i));
                    questionModel.setIntervieweeModel(intervieweeModel);
                    database.insertQuestion(questionModel);
                }
            }
            if(jsonResponse.has(KYNJSONKey.KEY_FEEDBACK)){
                database.deleteFeedback();
                JSONArray jsonArrayFeedback = new JSONArray(jsonResponse.getString(KYNJSONKey.KEY_FEEDBACK));
                for(int i=0;i<jsonArrayFeedback.length();i++){
                    KYNFeedbackModel feedbackModel= new KYNFeedbackModel((JSONObject)jsonArrayFeedback.get(i));
                    feedbackModel.setIntervieweeModel(intervieweeModel);
                    database.insertFeedback(feedbackModel);
                }
            }
            bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_INTERVIEWEE_DETAIL_SUCCESS);
            bundle.putString(KYNIntentConstant.BUNDLE_KEY_RESULT, "success");

            return bundle;
        } catch (JSONException e) {

        }

        return null;
    }

    @Override
    protected Bundle generateBundleOnRequestFailed(String responseString) {
        Bundle bundle = new Bundle();

        bundle.putString(KYNIntentConstant.BUNDLE_KEY_RESULT, KYNJSONKey.VAL_ERROR);
        bundle.putString(KYNIntentConstant.BUNDLE_KEY_MESSAGE, KYNJSONKey.VAL_MESSAGE_FAILED);
        bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_INTERVIEWEE_DETAIL_FAILED);

        return bundle;
    }

    @Override
    protected String generateRequest(){
        try {
            JSONObject json = new JSONObject();
            json.put(KYNJSONKey.KEY_INTERVIEWEE_SERVER_ID, intervieweeModel.getServerId());
            return json.toString();
        } catch (JSONException e) {

        }
        return null;
    }

    @Override
    protected byte[] generateBodyForHttpPost() {
        try {
            JSONObject json = new JSONObject();
            json.put(KYNJSONKey.KEY_USERNAME, username);
            JSONObject jsonParent = new JSONObject();
            jsonParent.put(KYNJSONKey.KEY_D, json);
            return jsonParent.toString().getBytes();
        } catch (JSONException e) {

        }
        return null;
    }

    @Override
    protected String getRestUrl() {
        String url;
        if (KYNSMPUtilities.port != null) {
            url = KYNSMPUtilities.requestType + KYNSMPUtilities.host + ":" + KYNSMPUtilities.port + "/" + KYNSMPUtilities.appIdIntervieweeDetailRest;
        } else {
            url = KYNSMPUtilities.requestType + KYNSMPUtilities.host + "/" + KYNSMPUtilities.appIdIntervieweeDetailRest;
        }
        return url;
    }
    public void setData(String username, KYNIntervieweeModel intervieweeModel){
        this.username = username;
        this.intervieweeModel = intervieweeModel;
    }
}
