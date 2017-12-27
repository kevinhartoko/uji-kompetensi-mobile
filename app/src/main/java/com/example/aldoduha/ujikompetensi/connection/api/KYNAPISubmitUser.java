package com.example.aldoduha.ujikompetensi.connection.api;

import android.content.Context;
import android.os.Bundle;

import com.example.aldoduha.ujikompetensi.connection.KYNHTTPPostConnections;
import com.example.aldoduha.ujikompetensi.connection.KYNSMPUtilities;
import com.example.aldoduha.ujikompetensi.connection.listener.KYNConnectionListener;
import com.example.aldoduha.ujikompetensi.model.KYNUserModel;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;
import com.example.aldoduha.ujikompetensi.utility.KYNJSONKey;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aldoduha on 12/27/2017.
 */

public class KYNAPISubmitUser extends KYNHTTPPostConnections {
    private String submitUserUrl = "";
    private String username;
    private KYNUserModel userModel;

    public KYNAPISubmitUser(Context applicationContext, KYNConnectionListener listener) {
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
                bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_SUBMIT_USER_SUCCESS);
            }else{
                bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_SUBMIT_USER_FAILED);
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
        bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_SUBMIT_USER_FAILED);

        return bundle;
    }

    @Override
    protected String generateRequest(){
        try {
            Gson gson = new GsonBuilder().setDateFormat(KYNIntentConstant.DATE_FORMAT).create();

            String user = gson.toJson(userModel);
            JSONObject json = new JSONObject();
            json.put(KYNJSONKey.KEY_USERNAME, username);
            json.put(KYNJSONKey.KEY_JSON, user);
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
        submitUserUrl = "SubmitUser";
        return submitUserUrl;
    }

    @Override
    protected String getRestUrl() {
        String url;
        if (KYNSMPUtilities.port != null) {
            url = KYNSMPUtilities.requestType + KYNSMPUtilities.host + ":" + KYNSMPUtilities.port + "/" + KYNSMPUtilities.appIdSubmitUserRest;
        } else {
            url = KYNSMPUtilities.requestType + KYNSMPUtilities.host + "/" + KYNSMPUtilities.appIdSubmitUserRest;
        }
        return url;
    }

    @Override
    protected String getFullUrl() {
        String url;
        if (KYNSMPUtilities.port != null) {
            url = KYNSMPUtilities.requestType+KYNSMPUtilities.host+":"+KYNSMPUtilities.port+"/"+KYNSMPUtilities.appIdSubmitUser +"/"+ getAddtionalURL();
        }else{
            url = KYNSMPUtilities.requestType+KYNSMPUtilities.host+"/"+KYNSMPUtilities.appIdSubmitUser +"/"+ getAddtionalURL();
        }

        return url;
    }

    @Override
    protected String getGetUrl() {
        String url;
        if (KYNSMPUtilities.port != null) {
            url = KYNSMPUtilities.requestType+KYNSMPUtilities.host+":"+KYNSMPUtilities.port+"/"+KYNSMPUtilities.appIdSubmitUser;
        }else{
            url = KYNSMPUtilities.requestType+KYNSMPUtilities.host+"/"+KYNSMPUtilities.appIdSubmitUser;
        }

        return url;
    }

    @Override
    protected boolean isUseFileUploadUrl() {
        return false;
    }

    public void setData(String username, KYNUserModel userModel){
        this.username = username;
        this.userModel = userModel;
    }
}
