package com.example.aldoduha.ujikompetensi.connection.api;

import android.content.Context;
import android.os.Bundle;

import com.example.aldoduha.ujikompetensi.connection.KYNHTTPPostConnections;
import com.example.aldoduha.ujikompetensi.connection.KYNSMPUtilities;
import com.example.aldoduha.ujikompetensi.connection.listener.KYNConnectionListener;
import com.example.aldoduha.ujikompetensi.model.KYNQuestionModel;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;
import com.example.aldoduha.ujikompetensi.utility.KYNJSONKey;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aldoduha on 12/27/2017.
 */

public class KYNAPISubmitQuestion extends KYNHTTPPostConnections {
    private String submitQuestionUrl = "";
    private String username;
    private KYNQuestionModel questionModel;

    public KYNAPISubmitQuestion(Context applicationContext, KYNConnectionListener listener) {
        super(applicationContext, listener);
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
                bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_SUBMIT_QUESTION_SUCCESS);
            }else{
                bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_SUBMIT_QUESTION_FAILED);
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
        bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_SUBMIT_QUESTION_FAILED);

        return bundle;
    }

    @Override
    protected String generateRequest(){
        try {
            Gson gson = new GsonBuilder().setDateFormat(KYNIntentConstant.DATE_FORMAT).create();

            String question = gson.toJson(questionModel);
            JSONObject json = new JSONObject();
            json.put(KYNJSONKey.KEY_USERNAME, username);
            json.put(KYNJSONKey.KEY_JSON, question);
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
        submitQuestionUrl = "SubmitQuestion";
        return submitQuestionUrl;
    }

    @Override
    protected String getRestUrl() {
        String url;
        if (KYNSMPUtilities.port != null) {
            url = KYNSMPUtilities.requestType + KYNSMPUtilities.host + ":" + KYNSMPUtilities.port + "/" + KYNSMPUtilities.appIdSubmitQuestionRest;
        } else {
            url = KYNSMPUtilities.requestType + KYNSMPUtilities.host + "/" + KYNSMPUtilities.appIdSubmitQuestionRest;
        }
        return url;
    }

    @Override
    protected String getFullUrl() {
        String url;
        if (KYNSMPUtilities.port != null) {
            url = KYNSMPUtilities.requestType+KYNSMPUtilities.host+":"+KYNSMPUtilities.port+"/"+KYNSMPUtilities.appIdSubmitQuestion +"/"+ getAddtionalURL();
        }else{
            url = KYNSMPUtilities.requestType+KYNSMPUtilities.host+"/"+KYNSMPUtilities.appIdSubmitQuestion +"/"+ getAddtionalURL();
        }

        return url;
    }

    @Override
    protected String getGetUrl() {
        String url;
        if (KYNSMPUtilities.port != null) {
            url = KYNSMPUtilities.requestType+KYNSMPUtilities.host+":"+KYNSMPUtilities.port+"/"+KYNSMPUtilities.appIdSubmitQuestion;
        }else{
            url = KYNSMPUtilities.requestType+KYNSMPUtilities.host+"/"+KYNSMPUtilities.appIdSubmitQuestion;
        }

        return url;
    }

    @Override
    protected boolean isUseFileUploadUrl() {
        return false;
    }

    public void setData(String username, KYNQuestionModel questionModel){
        this.username = username;
        this.questionModel = questionModel;
    }
}
