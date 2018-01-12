package com.example.aldoduha.ujikompetensi.connection.api;

import android.content.Context;
import android.os.Bundle;

import com.example.aldoduha.ujikompetensi.connection.KYNHTTPPostConnections;
import com.example.aldoduha.ujikompetensi.connection.KYNSMPUtilities;
import com.example.aldoduha.ujikompetensi.connection.listener.KYNConnectionListener;
import com.example.aldoduha.ujikompetensi.model.KYNUserModel;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;
import com.example.aldoduha.ujikompetensi.utility.KYNJSONKey;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aldoduha on 12/22/2017.
 */

public class KYNAPILogout extends KYNHTTPPostConnections {
    private String logoutUrl = "";
    private KYNUserModel userModel;

    public KYNAPILogout(Context applicationContext, KYNConnectionListener listener) {
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
//                bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_LOGOUT_SUCCESS);
//            }else{
//                bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_LOGOUT_FAILED);
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
            bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_LOGOUT_SUCCESS);
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
        bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_LOGOUT_FAILED);

        return bundle;
    }

    @Override
    protected String generateRequest(){
        try {
            JSONObject json = new JSONObject();
            json.put(KYNJSONKey.KEY_USERNAME, userModel.getUsername());
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
            url = KYNSMPUtilities.requestType + KYNSMPUtilities.host + ":" + KYNSMPUtilities.port + "/" + KYNSMPUtilities.appIdLogoutRest;
        } else {
            url = KYNSMPUtilities.requestType + KYNSMPUtilities.host + "/" + KYNSMPUtilities.appIdLogoutRest;
        }
        return url;
    }

    public void setData(KYNUserModel userModel){
        this.userModel = userModel;
    }
}
