package com.example.aldoduha.ujikompetensi.connection.api;

import android.content.Context;
import android.os.Bundle;

import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.connection.KYNHTTPPostConnections;
import com.example.aldoduha.ujikompetensi.connection.KYNSMPUtilities;
import com.example.aldoduha.ujikompetensi.connection.listener.KYNConnectionListener;
import com.example.aldoduha.ujikompetensi.model.KYNFeedbackModel;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;
import com.example.aldoduha.ujikompetensi.utility.KYNJSONKey;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aldoduha on 12/28/2017.
 */

public class KYNAPISubmitFeedback extends KYNHTTPPostConnections {
    private String submitFeedbackUrl = "";
    private String username;
    private KYNFeedbackModel feedbackModel;
    private KYNDatabaseHelper database;

    public KYNAPISubmitFeedback(Context applicationContext, KYNConnectionListener listener) {
        super(applicationContext, listener);
        database = new KYNDatabaseHelper(applicationContext);
    }

    @Override
    protected Bundle generateBundleOnRequestSuccess(String responseString) {
        try {
            Bundle bundle = new Bundle();
            JSONObject jsonResponse = new JSONObject(responseString);
            //jsonResponse = jsonResponse.getJSONObject(KYNJSONKey.KEY_D);
            String result = jsonResponse.getString(KYNJSONKey.KEY_RESULT);

            bundle.putString(KYNIntentConstant.BUNDLE_KEY_RESULT, result);
            bundle.putString(KYNIntentConstant.BUNDLE_KEY_MESSAGE, jsonResponse.getString(KYNJSONKey.KEY_MESSAGE));

            if (result.equalsIgnoreCase(KYNJSONKey.VAL_SUCCESS)) {
                bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_SUBMIT_FEEDBACK_SUCCESS);
            }else{
                bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_SUBMIT_FEEDBACK_FAILED);
            }

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
        bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_SUBMIT_FEEDBACK_FAILED);

        return bundle;
    }

    @Override
    protected String generateRequest(){
        try {
            Gson gson = new GsonBuilder().setDateFormat(KYNIntentConstant.DATE_FORMAT).create();
            String feedback = gson.toJson(feedbackModel);
            JSONObject json = new JSONObject();
            json.put(KYNJSONKey.KEY_USERNAME, username);
            json.put(KYNJSONKey.KEY_JSON, feedback);
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
    protected String getAddtionalURL() {
        submitFeedbackUrl = "SubmitFeedback";
        return submitFeedbackUrl;
    }

    @Override
    protected String getRestUrl() {
        String url;
        if (KYNSMPUtilities.port != null) {
            url = KYNSMPUtilities.requestType + KYNSMPUtilities.host + ":" + KYNSMPUtilities.port + "/" + KYNSMPUtilities.appIdSubmitFeedbackRest;
        } else {
            url = KYNSMPUtilities.requestType + KYNSMPUtilities.host + "/" + KYNSMPUtilities.appIdSubmitFeedbackRest;
        }
        return url;
    }

    @Override
    protected String getFullUrl() {
        String url;
        if (KYNSMPUtilities.port != null) {
            url = KYNSMPUtilities.requestType+KYNSMPUtilities.host+":"+KYNSMPUtilities.port+"/"+KYNSMPUtilities.appIdSubmitFeedback +"/"+ getAddtionalURL();
        }else{
            url = KYNSMPUtilities.requestType+KYNSMPUtilities.host+"/"+KYNSMPUtilities.appIdSubmitFeedback +"/"+ getAddtionalURL();
        }

        return url;
    }

    @Override
    protected String getGetUrl() {
        String url;
        if (KYNSMPUtilities.port != null) {
            url = KYNSMPUtilities.requestType+KYNSMPUtilities.host+":"+KYNSMPUtilities.port+"/"+KYNSMPUtilities.appIdSubmitFeedback;
        }else{
            url = KYNSMPUtilities.requestType+KYNSMPUtilities.host+"/"+KYNSMPUtilities.appIdSubmitFeedback;
        }

        return url;
    }

    @Override
    protected boolean isUseFileUploadUrl() {
        return false;
    }

    public void setData(String username, KYNFeedbackModel feedbackModel){
        this.username = username;
        this.feedbackModel = feedbackModel;
    }
}
