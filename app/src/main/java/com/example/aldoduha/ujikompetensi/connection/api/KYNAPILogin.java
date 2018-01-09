package com.example.aldoduha.ujikompetensi.connection.api;

import android.content.Context;
import android.os.Bundle;

import com.example.aldoduha.ujikompetensi.connection.KYNHTTPPostConnections;
import com.example.aldoduha.ujikompetensi.connection.KYNSMPUtilities;
import com.example.aldoduha.ujikompetensi.connection.listener.KYNConnectionListener;
import com.example.aldoduha.ujikompetensi.model.KYNUserModel;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;
import com.example.aldoduha.ujikompetensi.utility.KYNJSONKey;
import com.sap.smp.rest.ClientConnection;
import com.sap.smp.rest.SMPClientListeners;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aldoduha on 12/18/2017.
 */

public class KYNAPILogin extends KYNHTTPPostConnections {
    private KYNUserModel userModel;

    public KYNAPILogin(Context applicationContext, KYNConnectionListener listener) {
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
                bundle.putString(KYNIntentConstant.BUNDLE_KEY_USERNAME, jsonResponse.getString(KYNJSONKey.KEY_USERNAME));
                bundle.putString(KYNIntentConstant.BUNDLE_KEY_TOKEN, jsonResponse.getString(KYNJSONKey.KEY_TOKEN));
                bundle.putString(KYNIntentConstant.BUNDLE_KEY_JSON, jsonResponse.getString(KYNJSONKey.KEY_JSON));

                bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_LOGIN_SUCCESS);
            }else{
                bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_LOGIN_FAILED);
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
        bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_LOGIN_FAILED);

        return bundle;
    }

    @Override
    protected String generateRequest(){
        try {
            JSONObject json = new JSONObject();
            json.put(KYNJSONKey.KEY_USERNAME, userModel.getUsername());
            json.put(KYNJSONKey.KEY_PASSWORD, userModel.getPassword());

            /*KYNLoginModel loginModel = new KYNLoginModel();
            loginModel.setAppId(KYNConstants.E_LOAN);
            Gson gson = new Gson();
            json.put(KYNJSONKey.KEY_JSON, gson.toJson(loginModel));*/

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
            json.put(KYNJSONKey.KEY_PASSWORD, userModel.getPassword());
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
            url = KYNSMPUtilities.requestType + KYNSMPUtilities.host + ":" + KYNSMPUtilities.port + "/" + KYNSMPUtilities.appIdLoginRest;
        } else {
            url = KYNSMPUtilities.requestType + KYNSMPUtilities.host + "/" + KYNSMPUtilities.appIdLoginRest;
        }
        return url;
    }

    public void setData(KYNUserModel userModel){
        this.userModel = userModel;
    }
}
