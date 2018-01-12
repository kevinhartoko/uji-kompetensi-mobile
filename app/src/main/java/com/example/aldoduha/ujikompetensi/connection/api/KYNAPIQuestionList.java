package com.example.aldoduha.ujikompetensi.connection.api;

import android.content.Context;
import android.os.Bundle;

import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.connection.KYNHTTPPostConnections;
import com.example.aldoduha.ujikompetensi.connection.KYNSMPUtilities;
import com.example.aldoduha.ujikompetensi.connection.listener.KYNConnectionListener;
import com.example.aldoduha.ujikompetensi.model.KYNQuestionModel;
import com.example.aldoduha.ujikompetensi.model.KYNUserModel;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;
import com.example.aldoduha.ujikompetensi.utility.KYNJSONKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aldoduha on 12/27/2017.
 */

public class KYNAPIQuestionList extends KYNHTTPPostConnections {
    private String category = "";
    private KYNUserModel userModel;
    private KYNDatabaseHelper database;

    public KYNAPIQuestionList(Context applicationContext, KYNConnectionListener listener) {
        super(applicationContext, listener);
        database = new KYNDatabaseHelper(applicationContext);
    }

    @Override
    protected Bundle generateBundleOnRequestSuccess(String responseString) {
        try {
//            Bundle bundle = new Bundle();
//            JSONObject jsonResponse = new JSONObject(responseString);
//            //jsonResponse = jsonResponse.getJSONObject(KYNJSONKey.KEY_D);
//            String result = jsonResponse.getString(KYNJSONKey.KEY_RESULT);
//
//            bundle.putString(KYNIntentConstant.BUNDLE_KEY_RESULT, result);
//            bundle.putString(KYNIntentConstant.BUNDLE_KEY_MESSAGE, jsonResponse.getString(KYNJSONKey.KEY_MESSAGE));
//
//            if (result.equalsIgnoreCase(KYNJSONKey.VAL_SUCCESS)) {
//                bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_QUESTION_LIST_SUCCESS);
//            }else{
//                bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_QUESTION_LIST_FAILED);
//            }
            Bundle bundle = new Bundle();
            JSONArray jsonResponse = new JSONArray(responseString);
            database.deleteQuestion();
            for(int i=0;i<jsonResponse.length();i++){
                KYNQuestionModel questionModel = new KYNQuestionModel((JSONObject)jsonResponse.get(i));
                database.insertQuestion(questionModel);
            }
            bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_QUESTION_LIST_SUCCESS);
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
        bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_QUESTION_LIST_FAILED);

        return bundle;
    }

    @Override
    protected String generateRequest(){
        try {
            JSONObject json = new JSONObject();
            json.put(KYNJSONKey.KEY_CATEGORY, category);
            return json.toString();
        } catch (JSONException e) {

        }
        return null;
    }

    @Override
    protected byte[] generateBodyForHttpPost() {
        try {
            JSONObject json = new JSONObject();
            json.put(KYNJSONKey.KEY_USERNAME, userModel.getUsername());
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
            url = KYNSMPUtilities.requestType + KYNSMPUtilities.host + ":" + KYNSMPUtilities.port + "/" + KYNSMPUtilities.appIdQuestionListRest;
        } else {
            url = KYNSMPUtilities.requestType + KYNSMPUtilities.host + "/" + KYNSMPUtilities.appIdQuestionListRest;
        }
        return url;
    }

    public void setData(String category){
        this.category = category;
    }
}
