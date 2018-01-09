package com.example.aldoduha.ujikompetensi.connection;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.aldoduha.ujikompetensi.connection.listener.KYNConnectionListener;
import com.example.aldoduha.ujikompetensi.utility.KYNHttpPostRest;
import com.sap.smp.rest.SMPClientListeners;

import org.json.JSONException;

public abstract class KYNHTTPPostConnections extends AsyncTask<Void, String, String> {

    protected Context applicationContext;
    protected boolean isPendingResponse = false;
    protected KYNConnectionListener listener;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        String result = "";

        try {
            String url = getRestUrl();
            String request = generateRequest();
            result = KYNHttpPostRest.postData(url, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            Bundle bundle = null;
            try {
                bundle = generateBundleOnRequestSuccess(result);
            } catch (JSONException e) {
                bundle = generateBundleOnRequestFailed(result);

                if (listener != null) {
                    listener.onSend(bundle, applicationContext);
                }

                return;
            }

            if (!isPendingResponse) {
                if (bundle == null) {
                    bundle = generateBundleOnRequestFailed(result);
                }

                if (listener != null) {
                    listener.onSend(bundle, applicationContext);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public KYNHTTPPostConnections(Context applicationContext, KYNConnectionListener listener) {
        this.applicationContext = applicationContext;
        this.listener = listener;
    }

    protected abstract Bundle generateBundleOnRequestSuccess(String responseString) throws JSONException;
    protected abstract Bundle generateBundleOnRequestFailed(String responseString);
    protected abstract byte[] generateBodyForHttpPost();
    protected abstract String generateRequest();
    protected abstract String getRestUrl();
}
