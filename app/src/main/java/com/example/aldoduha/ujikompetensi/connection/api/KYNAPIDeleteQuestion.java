package com.example.aldoduha.ujikompetensi.connection.api;

import android.content.Context;
import android.os.Bundle;

import com.example.aldoduha.ujikompetensi.connection.KYNHTTPPostConnections;
import com.example.aldoduha.ujikompetensi.connection.KYNSMPUtilities;
import com.example.aldoduha.ujikompetensi.connection.listener.KYNConnectionListener;
import com.example.aldoduha.ujikompetensi.model.KYNQuestionModel;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;
import com.example.aldoduha.ujikompetensi.utility.KYNJSONKey;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aldoduha on 1/10/2018.
 */

public class KYNAPIDeleteQuestion extends KYNHTTPPostConnections {
    private String username;
    private KYNQuestionModel questionModel;

    public KYNAPIDeleteQuestion(Context applicationContext, KYNConnectionListener listener) {
        super(applicationContext, listener);
    }

    @Override
    protected Bundle generateBundleOnRequestSuccess(String responseString) {
        Bundle bundle = new Bundle();
        if(responseString.equalsIgnoreCase("success")){
            bundle.putString(KYNIntentConstant.BUNDLE_KEY_RESULT, "success");
            bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_DELETE_QUESTION_SUCCESS);
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
        bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_DELETE_QUESTION_FAILED);

        return bundle;
    }

    @Override
    protected String generateRequest(){
        try {
            JSONObject json = new JSONObject();
            json.put(KYNJSONKey.KEY_SERVER_ID, questionModel.getServerId());
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
            url = KYNSMPUtilities.requestType + KYNSMPUtilities.host + ":" + KYNSMPUtilities.port + "/" + KYNSMPUtilities.appIdDeleteQuestionRest;
        } else {
            url = KYNSMPUtilities.requestType + KYNSMPUtilities.host + "/" + KYNSMPUtilities.appIdDeleteQuestionRest;
        }
        return url;
    }

    public void setData(String username, KYNQuestionModel questionModel){
        this.username = username;
        this.questionModel = questionModel;
    }
}