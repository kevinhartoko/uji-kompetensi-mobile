package com.example.aldoduha.ujikompetensi.connection.api;

import android.content.Context;
import android.os.Bundle;

import com.example.aldoduha.ujikompetensi.connection.KYNHTTPPostConnections;
import com.example.aldoduha.ujikompetensi.connection.KYNSMPUtilities;
import com.example.aldoduha.ujikompetensi.connection.listener.KYNConnectionListener;
import com.example.aldoduha.ujikompetensi.model.KYNFeedbackModel;
import com.example.aldoduha.ujikompetensi.model.KYNQuestionModel;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;
import com.example.aldoduha.ujikompetensi.utility.KYNJSONKey;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aldoduha on 1/11/2018.
 */

public class KYNAPIDeleteFeedback extends KYNHTTPPostConnections {
    private String username;
    private KYNFeedbackModel feedbackModel;

    public KYNAPIDeleteFeedback(Context applicationContext, KYNConnectionListener listener) {
        super(applicationContext, listener);
    }

    @Override
    protected Bundle generateBundleOnRequestSuccess(String responseString) {
//        try {
//            Bundle bundle = new Bundle();
//            JSONObject jsonResponse = new JSONObject(responseString);
//            //jsonResponse = jsonResponse.getJSONObject(KYNJSONKey.KEY_D);
//            String result = jsonResponse.getString(KYNJSONKey.KEY_RESULT);
//
//            bundle.putString(KYNIntentConstant.BUNDLE_KEY_RESULT, result);
//            bundle.putString(KYNIntentConstant.BUNDLE_KEY_MESSAGE, jsonResponse.getString(KYNJSONKey.KEY_MESSAGE));
//
//            if (result.equalsIgnoreCase(KYNJSONKey.VAL_SUCCESS)) {
//                bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_DELETE_FEEDBACK_SUCCESS);
//            }else{
//                bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_DELETE_FEEDBACK_FAILED);
//            }
//
//            return bundle;
//        } catch (JSONException e) {
//
//        }
//
//        return null;
        Bundle bundle = new Bundle();
        if(responseString.equalsIgnoreCase("success")){
            bundle.putString(KYNIntentConstant.BUNDLE_KEY_RESULT, "success");
            bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_DELETE_FEEDBACK_SUCCESS);
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
        bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_DELETE_FEEDBACK_FAILED);

        return bundle;
    }

    @Override
    protected String generateRequest(){
        try {
            JSONObject json = new JSONObject();
            json.put(KYNJSONKey.KEY_SERVER_ID, feedbackModel.getServerId());
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
            url = KYNSMPUtilities.requestType + KYNSMPUtilities.host + ":" + KYNSMPUtilities.port + "/" + KYNSMPUtilities.appIdDeleteFeedbackRest;
        } else {
            url = KYNSMPUtilities.requestType + KYNSMPUtilities.host + "/" + KYNSMPUtilities.appIdDeleteFeedbackRest;
        }
        return url;
    }

    public void setData(String username, KYNFeedbackModel feedbackModel){
        this.username = username;
        this.feedbackModel = feedbackModel;
    }
}