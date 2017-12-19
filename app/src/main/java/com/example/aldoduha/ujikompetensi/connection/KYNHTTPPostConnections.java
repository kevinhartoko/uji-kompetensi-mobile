package com.example.aldoduha.ujikompetensi.connection;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.R;
import com.example.aldoduha.ujikompetensi.connection.listener.KYNConnectionListener;
import com.example.aldoduha.ujikompetensi.model.KYNUserModel;
import com.example.aldoduha.ujikompetensi.preferences.KYNSharedPreference;
import com.example.aldoduha.ujikompetensi.utility.KYNHttpPostRest;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;
import com.sap.mobile.lib.request.BaseRequest;
import com.sap.mobile.lib.request.INetListener;
import com.sap.mobile.lib.request.IRequest;
import com.sap.mobile.lib.request.IRequestStateElement;
import com.sap.mobile.lib.request.IResponse;
import com.sap.smp.rest.ClientConnection;
import com.sap.smp.rest.SMPClientListeners;
import com.sap.smp.rest.SMPException;
import com.sybase.persistence.DataVault;
import com.sybase.persistence.DataVaultException;
import com.sybase.persistence.PrivateDataVault;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class KYNHTTPPostConnections extends AsyncTask<Void, String, String> implements SMPClientListeners.ISMPUserRegistrationListener {

    protected Context applicationContext;
    protected DataVault dataVault;
    protected boolean isPendingResponse = false;
    protected boolean isCanceled = false;
    private KYNConnectionManager conMan;
    protected KYNConnectionListener listener;

    private String reqToken = "";
    private boolean isRespond = false;
    private boolean isTimerRespond = false;
    private Handler handler = null;
    private Runnable runnable;
    private KYNUserModel session;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        KYNDatabaseHelper database = new KYNDatabaseHelper(applicationContext);
        session = database.getSession();
    }

    @Override
    protected String doInBackground(Void... params) {
        String result = "";

        try {
            String url = getRestUrl();
            String request = generateRequest();
//            Map<String, String> customHeaders = getCustomHeaders(url, request);
            result = KYNHttpPostRest.postData(url, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    int[] asd = new int[4];
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

    private void startTimerRequest() {
        if (handler == null) {
            try {
                handler = new Handler();
            } catch (Exception e) {
                handler = new Handler(Looper.getMainLooper());
            }
        }
        isRespond = false;
        isTimerRespond = false;
        handler.postDelayed(runnable, 70000);
    }

    protected INetListener inetListener = new INetListener() {

        @Override
        public void onSuccess(IRequest request, IResponse response) {
            if (!isTimerRespond) {
                isRespond = true;
                if (handler != null) {
                    handler.removeCallbacks(runnable);
                }
                String responseString = parseSuccessResponse(response);
                if (responseString == null) {

                    Bundle bundle = generateBundleOnRequestFailed(null);
                    bundle.putString(KYNIntentConstant.BUNDLE_KEY_MESSAGE, applicationContext.getResources().getString(R.string.failed_to_connect));

                    if (listener != null) {
                        listener.onSend(bundle, applicationContext);
                    }

                    return;
                }

                Bundle bundle = null;
                try {
                    bundle = generateBundleOnRequestSuccess(responseString);
                } catch (JSONException e) {
                    bundle = generateBundleOnRequestFailed(responseString);

                    if (listener != null) {
                        listener.onSend(bundle, applicationContext);
                    }

                    return;
                }

                if (!isPendingResponse) {
                    if (bundle == null) {
                        bundle = generateBundleOnRequestFailed(responseString);
                    }

                    if (listener != null) {
                        listener.onSend(bundle, applicationContext);
                    }
                }
            }
        }

        @Override
        public void onError(IRequest request, IResponse response, IRequestStateElement state) {
            if (!isTimerRespond) {
                isRespond = true;
                if (handler != null) {
                    handler.removeCallbacks(runnable);
                }
                int status = state.getHttpStatusCode();
                String responseString = parseErrorResponse(response);
                if (status == 401 || status == 404 || status == 403) {
                    KYNSMPUtilities.appConnId = "";
                    Bundle bundle = generateBundleOnRequestFailed(null);
                    bundle.putString(KYNIntentConstant.BUNDLE_KEY_MESSAGE, applicationContext.getResources().getString(R.string.invalid_token));
                    bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_FAILED_TOKEN);
                    if (listener != null) {
                        listener.onSend(bundle, applicationContext);
                    }

                    return;
                }

                if (responseString == null) {

                    Bundle bundle = generateBundleOnRequestFailed(responseString);
                    bundle.putString(KYNIntentConstant.BUNDLE_KEY_MESSAGE, applicationContext.getResources().getString(R.string.failed_to_connect));

                    if (listener != null) {
                        listener.onSend(bundle, applicationContext);
                    }

                    return;
                }

                Bundle bundle = generateBundleOnRequestFailed(responseString);
                if (listener != null) {
                    listener.onSend(bundle, applicationContext);
                }
            }
        }
    };

    protected INetListener inetListenerGet = new INetListener() {

        @Override
        public void onSuccess(IRequest request, IResponse response) {
            if (!isTimerRespond) {
                isRespond = true;
                if (handler != null) {
                    handler.removeCallbacks(runnable);
                }
                reqToken = response.getHeadersMap().get("X-CSRF-Token");
                KYNSharedPreference.setXCSRFToken(applicationContext, reqToken);
                sendRequest();
            }
        }

        @Override
        public void onError(IRequest request, IResponse response, IRequestStateElement state) {
            if (!isTimerRespond) {
                isRespond = true;
                if (handler != null) {
                    handler.removeCallbacks(runnable);
                }
                int status = state.getHttpStatusCode();
                if (status == 401 || status == 404 || status == 403) {
                    KYNSMPUtilities.appConnId = "";
                    Bundle bundle = generateBundleOnRequestFailed(null);
                    bundle.putString(KYNIntentConstant.BUNDLE_KEY_MESSAGE, applicationContext.getResources().getString(R.string.invalid_token));
                    bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_FAILED_TOKEN);
                    if (listener != null) {
                        listener.onSend(bundle, applicationContext);
                    }

                    return;
                }
                if (response != null) {
                    reqToken = response.getHeadersMap().get("X-CSRF-Token");

                    if (reqToken != null && !request.equals("")) {
                        KYNSharedPreference.setXCSRFToken(applicationContext, reqToken);
                        sendRequest();
                        return;
                    }
                }

                //No Token And Bundle
                Bundle bundle = generateBundleOnRequestFailed(null);
                bundle.putString(KYNIntentConstant.BUNDLE_KEY_MESSAGE, applicationContext.getResources().getString(R.string.failed_to_connect));

                if (listener != null) {
                    listener.onSend(bundle, applicationContext);
                }
            }
        }
    };

    public KYNHTTPPostConnections(Context applicationContext, KYNConnectionListener listener) {
        this.applicationContext = applicationContext;
        this.listener = listener;
    }

    private void registerLolipop() {
        conMan = new KYNConnectionManager(applicationContext);
        KYNSharedPreference.setXCSRFToken(applicationContext, "");
        if (PrivateDataVault.vaultExists(KYNSMPUtilities.getDataValultName())) {

            dataVault = PrivateDataVault.getVault(KYNSMPUtilities.getDataValultName());
            dataVault.unlock(KYNSMPUtilities.appPasscode, KYNSMPUtilities.appPasscode + KYNSMPUtilities.appSaltcode);

            KYNSMPUtilities.appConnId = dataVault.getString("appCid");
            KYNSMPUtilities.username = dataVault.getString("username");
            KYNSMPUtilities.password = dataVault.getString("password");

            sendRequest();

            return;
        } else {
            conMan.userManager.setUserRegistrationListener(this);
            conMan.onBoard();
            if (!KYNSMPUtilities.appConnId.equals("")) {
                sendRequest();
            } else {
                if (listener != null) {
                    Bundle bundle = generateBundleOnRequestFailed(null);
                    bundle.putString(KYNIntentConstant.BUNDLE_KEY_MESSAGE, "Gagal Register to Server");
                    bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_FAILED);

                    listener.onSend(bundle, applicationContext);
                }
            }
        }
    }

    private void register() {
        KYNSMPUtilities.configurationForRegistration(applicationContext);
        KYNSharedPreference.setXCSRFToken(applicationContext, "");
        if (PrivateDataVault.vaultExists(KYNSMPUtilities.getDataValultName())) {

            dataVault = PrivateDataVault.getVault(KYNSMPUtilities.getDataValultName());
            dataVault.unlock(KYNSMPUtilities.appPasscode, KYNSMPUtilities.appPasscode + KYNSMPUtilities.appSaltcode);

            KYNSMPUtilities.appConnId = dataVault.getString("appCid");
            sendRequest();

            return;
        }

        try {
            KYNSMPUtilities.userManager.setUserRegistrationListener(this);
            KYNSMPUtilities.userManager.registerUser(false);
        } catch (SMPException e) {

        }
    }

    @Override
    public void onAsyncRegistrationResult(State registrationState, ClientConnection clientConnection, int errCode, String errMsg) {
        try {
            if (registrationState == State.SUCCESS) {

                //Create DataVault
                PrivateDataVault.init(applicationContext);
                KYNSMPUtilities.initializeRequestManager(applicationContext);
                if (PrivateDataVault.vaultExists(KYNSMPUtilities.getDataValultName())) {
                    dataVault = PrivateDataVault.getVault(KYNSMPUtilities.getDataValultName());
                    dataVault.unlock(KYNSMPUtilities.appPasscode, KYNSMPUtilities.appPasscode + KYNSMPUtilities.appSaltcode);
                } else {
                    dataVault = PrivateDataVault.createVault(KYNSMPUtilities.getDataValultName(), KYNSMPUtilities.appPasscode, KYNSMPUtilities.appPasscode + KYNSMPUtilities.appSaltcode);
                }
                if (KYNSMPUtilities.appConnId.equals("")) {
                    dataVault.setString("appCid", KYNSMPUtilities.userManager.getApplicationConnectionId());
                    KYNSMPUtilities.appConnId = KYNSMPUtilities.userManager.getApplicationConnectionId();
                } else {
                    dataVault.setString("appCid", KYNSMPUtilities.appConnId);
                }
                dataVault.setString("username", KYNSMPUtilities.username);
                dataVault.setString("password", KYNSMPUtilities.password);
                dataVault.setString("host", KYNSMPUtilities.host);
                dataVault.setString("port", KYNSMPUtilities.port);
                dataVault.setString("isHttp", KYNSMPUtilities.isHttpRequest.toString());
                dataVault.lock();

                //Success Register then send specific request
                sendRequest();
            } else {
                if (listener != null) {
                    Bundle bundle = generateBundleOnRequestFailed(null);
                    bundle.putString(KYNIntentConstant.BUNDLE_KEY_MESSAGE, applicationContext.getResources().getString(R.string.failed_to_register));
                    bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_FAILED);

                    listener.onSend(bundle, applicationContext);
                }
            }
        } catch (DataVaultException e) {


            if (listener != null) {
                Bundle bundle = generateBundleOnRequestFailed(null);
                bundle.putString(KYNIntentConstant.BUNDLE_KEY_MESSAGE, "Gagal meminta data");//Local Problem, Failed to send request

                listener.onSend(bundle, applicationContext);
            }

        } catch (SMPException e) {


            if (listener != null) {
                Bundle bundle = generateBundleOnRequestFailed(null);
                bundle.putString(KYNIntentConstant.BUNDLE_KEY_MESSAGE, "Ada masalah koneksi");//Connection Error when registration

                listener.onSend(bundle, applicationContext);
            }

        } catch (Exception e) {


            if (listener != null) {
                Bundle bundle = generateBundleOnRequestFailed(null);
                bundle.putString(KYNIntentConstant.BUNDLE_KEY_MESSAGE, "Gagal");

                listener.onSend(bundle, applicationContext);
            }
        }
    }

    public void sendRequest() {
        if (KYNSMPUtilities.appConnId.equals("")) {
            int apiVersion = Build.VERSION.SDK_INT;
            if (apiVersion > Build.VERSION_CODES.KITKAT_WATCH) {
                registerLolipop();
            } else {
                register();
            }
        } else if (!getAddtionalURL().startsWith("Login") && !getAddtionalURL().startsWith("Logout(")
                && !getAddtionalURL().startsWith("VerifyOtp(") && !getAddtionalURL().startsWith("SendOtp(")) {

            if (getAddtionalURL().startsWith("clientlogs")) {
                sendHttpPostRequest();
                return;
            }
            KYNDatabaseHelper database = KYNDatabaseHelper.getInstance(applicationContext);
            KYNUserModel userModel = database.getSession();
            database.close();
            String token = userModel.getToken();
            if (token == null || token.equals("") || token.equalsIgnoreCase("null")) {
                Bundle bundle = new Bundle();
                bundle.putInt(KYNIntentConstant.BUNDLE_KEY_CODE, KYNIntentConstant.CODE_FAILED_TOKEN);
                bundle.putString(KYNIntentConstant.BUNDLE_KEY_MESSAGE, "Anda belum login ke server, silakan melakukan login terlebih dahulu");

                if (listener != null) {
                    listener.onSend(bundle, applicationContext);
                }
                return;
            } else if (reqToken == null || reqToken.equals("")) {
                runnable = new Runnable() {

                    @Override
                    public void run() {
                        if (!isRespond) {
                            isTimerRespond = true;
                            Bundle bundle = generateBundleOnRequestFailed(null);
                            bundle.putString(KYNIntentConstant.BUNDLE_KEY_MESSAGE, applicationContext.getResources().getString(R.string.failed_to_connect));

                            if (listener != null) {
                                listener.onSend(bundle, applicationContext);
                            }
                        }
                    }
                };
                startTimerRequest();
                reqToken = KYNSharedPreference.getXCSRFToken(applicationContext);
                if (reqToken.equals("")) {
                    sendHttpGetRequest();
                } else {
                    sendHttpPostRequest();
                }
            } else {
                runnable = new Runnable() {

                    @Override
                    public void run() {
                        if (!isRespond) {
                            isTimerRespond = true;
                            Bundle bundle = generateBundleOnRequestFailed(null);
                            bundle.putString(KYNIntentConstant.BUNDLE_KEY_MESSAGE, applicationContext.getResources().getString(R.string.failed_to_connect));

                            if (listener != null) {
                                listener.onSend(bundle, applicationContext);
                            }
                        }
                    }
                };
                startTimerRequest();
                Log.d("b","beneran send request");
                sendHttpPostRequest();
            }
        } else if (reqToken == null || reqToken.equals("")) {
            runnable = new Runnable() {

                @Override
                public void run() {
                    if (!isRespond) {
                        isTimerRespond = true;
                        Bundle bundle = generateBundleOnRequestFailed(null);
                        bundle.putString(KYNIntentConstant.BUNDLE_KEY_MESSAGE, applicationContext.getResources().getString(R.string.failed_to_connect));

                        if (listener != null) {
                            listener.onSend(bundle, applicationContext);
                        }
                    }
                }
            };
            startTimerRequest();
            reqToken = KYNSharedPreference.getXCSRFToken(applicationContext);
            if (reqToken.equals("")) {
                sendHttpGetRequest();
            } else {
                sendHttpPostRequest();
            }
        } else {
            runnable = new Runnable() {

                @Override
                public void run() {
                    if (!isRespond) {
                        isTimerRespond = true;
                        Bundle bundle = generateBundleOnRequestFailed(null);
                        bundle.putString(KYNIntentConstant.BUNDLE_KEY_MESSAGE, applicationContext.getResources().getString(R.string.failed_to_connect));

                        if (listener != null) {
                            listener.onSend(bundle, applicationContext);
                        }
                    }
                }
            };
            startTimerRequest();
            sendHttpPostRequest();
        }
    }

    private void sendHttpGetRequest() {
        try {
            BaseRequest getrequest = new BaseRequest();
            int apiVersion = Build.VERSION.SDK_INT;
            if (apiVersion > Build.VERSION_CODES.KITKAT_WATCH) {
                conMan = new KYNConnectionManager(applicationContext);
            } else {
                KYNSMPUtilities.initializeRequestManager(applicationContext);
            }
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("X-SMP-APPCID", KYNSMPUtilities.appConnId);
            headers.put("X-CSRF-Token", "FETCH");
            headers.put("Content-Type", "application/json");
            headers.put("X-Requested-With", "XMLHttpRequest");
            headers.put("Accept", "application/json");

            String url = getGetUrl();

            getrequest.setRequestUrl(url);
            getrequest.setRequestMethod(BaseRequest.REQUEST_METHOD_GET);
            getrequest.setHeaders(headers);
            getrequest.setListener(inetListenerGet);

            if (apiVersion > Build.VERSION_CODES.KITKAT_WATCH) {
                KYNConnectionManager.requestManager.makeRequest(getrequest);
            } else {
                KYNSMPUtilities.reqMan.makeRequest(getrequest);
            }
        } catch (Exception e) {

            if (listener != null) {

                Bundle bundle = generateBundleOnRequestFailed(null);
                bundle.putString(KYNIntentConstant.BUNDLE_KEY_MESSAGE, "Ada masalah Koneksi");//Connection Error when send request

                listener.onSend(bundle, applicationContext);
            }
        }
    }

    private void sendHttpPostRequest() {
        try {
            BaseRequest getrequest = new BaseRequest();

            int apiVersion = Build.VERSION.SDK_INT;
            if (apiVersion > Build.VERSION_CODES.KITKAT_WATCH) {
                conMan = new KYNConnectionManager(applicationContext);
            } else {
                KYNSMPUtilities.initializeRequestManager(applicationContext);
            }

            Map<String, String> headers = new HashMap<String, String>();
            headers.put("X-SMP-APPCID", KYNSMPUtilities.appConnId);
            headers.put("X-CSRF-Token", reqToken);
            if (getAddtionalURL().startsWith("clientlogs")) {
                headers.put("Content-Type", "multipart/form-data");
            } else {
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
            }

            String url = getFullUrl();

            getrequest.setRequestUrl(url);
            getrequest.setRequestMethod(BaseRequest.REQUEST_METHOD_POST);
            getrequest.setHeaders(headers);
            getrequest.setData(generateBodyForHttpPost());
            getrequest.setListener(inetListener);

            if (!isCanceled) {
                if (apiVersion > Build.VERSION_CODES.KITKAT_WATCH) {
                    Log.d("b","makeRequest 1");
                    KYNConnectionManager.requestManager.makeRequest(getrequest);
                } else {
                    Log.d("b","makeRequest 2");
                    KYNSMPUtilities.reqMan.makeRequest(getrequest);
                }
            }
        } catch (Exception e) {

            if (listener != null) {

                Bundle bundle = generateBundleOnRequestFailed(null);
                bundle.putString(KYNIntentConstant.BUNDLE_KEY_MESSAGE, "Ada masalah koneksi");//Connection Error when send request

                listener.onSend(bundle, applicationContext);
            }
        }
    }

    private String parseSuccessResponse(IResponse response) {
        String responseString = "";

        try {

            DataInputStream dis = new DataInputStream(response.getEntity().getContent());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int length = 0;
            while ((length = dis.read(data)) != -1) {
                bos.write(data, 0, length);
            }

            responseString = new String(bos.toByteArray());

        } catch (IllegalStateException e) {


        } catch (IOException e) {


        } catch (Exception e) {


        }

        if (responseString.equals("")) {
            return null;
        }

        return responseString;
    }

    private String parseErrorResponse(IResponse response) {
        String responseString = "";
        if (response == null) {
            return null;
        }

        try {
            DataInputStream dis = new DataInputStream(response.getEntity().getContent());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int length = 0;
            while ((length = dis.read(data)) != -1) {
                bos.write(data, 0, length);
            }

            responseString = new String(bos.toByteArray());
        } catch (IllegalStateException e) {


        } catch (IOException e) {


        }

        if (responseString.equals("")) {
            return null;
        }

        return responseString;
    }

//    protected String generateSignature(String relativeUrl, String reqBodyString, String timestamp) {
//        try {
//            StringBuilder sb = new StringBuilder();
//            sb.append("POST").append(KYNConstants.COLON).append(relativeUrl).append(KYNConstants.COLON);
//            sb.append(KYNConstants.BTPN_API_KEY_VALUE).append(KYNConstants.COLON).append(timestamp);
//            sb.append(KYNConstants.COLON).append(reqBodyString);
//            String sbTrim = sb.toString().replaceAll("\\s", "");
//            Mac mac = Mac.getInstance(KYNConstants.HMAC_256);
//            mac.init(new SecretKeySpec(session.getSecret().getBytes(), KYNConstants.HMAC_256));
//            byte[] byteResult = mac.doFinal(sbTrim.getBytes());
//            return new String(Base64.encodeBase64(byteResult));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "";
//    }

//    protected Map<String, String> getCustomHeaders(String url, String requestBody) {
//        Map<String, String> map = new HashMap<>();
//        String timestamp = new SimpleDateFormat(KYNConstants.HMAC_DATE_PATTERN).format(Calendar.getInstance().getTime());
//        map.put(KYNJSONKey.BTPN_API_KEY, KYNConstants.BTPN_API_KEY_VALUE);
//        map.put(KYNJSONKey.BTPN_TIMESTAMP, timestamp);
//        map.put(KYNJSONKey.BTPN_JWT, session.getJwt());
//        map.put(KYNJSONKey.BTPN_SIGNATURE, generateSignature(relativizeURL(url), requestBody, timestamp));
//        return map;
//    }

    private static String relativizeURL(String url) {
        try {
            String context = KYNSMPUtilities.requestType+KYNSMPUtilities.host+":"+KYNSMPUtilities.port;
            return url.substring(context.length(), url.length());
        } catch (Exception e) {}
        return "";
    }

    protected abstract Bundle generateBundleOnRequestSuccess(String responseString) throws JSONException;
    protected abstract Bundle generateBundleOnRequestFailed(String responseString);
    protected abstract byte[] generateBodyForHttpPost();
    protected abstract String getAddtionalURL();
    protected abstract String getFullUrl();
    protected abstract String getGetUrl();
    protected abstract boolean isUseFileUploadUrl();
    protected abstract String generateRequest();
    protected abstract String getRestUrl();
}
