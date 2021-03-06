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
    private String username;
    private KYNQuestionModel questionModel;

    public KYNAPISubmitQuestion(Context applicationContext, KYNConnectionListener listener) {
        super(applicationContext, listener);
    }

    @Override
    protected Bundle generateBundleOnRequestSuccess(String responseString) {
            Bundle bundle = new Bundle();
            if(responseString.equalsIgnoreCase("success")){
                bundle.putString(KYNIntentConstant.BUNDLE_KEY_RESULT, "success");
                bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_SUBMIT_QUESTION_SUCCESS);
            }else {
                generateBundleOnRequestFailed(responseString);
                return null;
            }

            return bundle;
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
            JSONObject json = new JSONObject(question);
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
            url = KYNSMPUtilities.requestType + KYNSMPUtilities.host + ":" + KYNSMPUtilities.port + "/" + KYNSMPUtilities.appIdSubmitQuestionRest;
        } else {
            url = KYNSMPUtilities.requestType + KYNSMPUtilities.host + "/" + KYNSMPUtilities.appIdSubmitQuestionRest;
        }
        return url;
    }

    public void setData(String username, KYNQuestionModel questionModel){
        this.username = username;
        this.questionModel = questionModel;
    }
}
