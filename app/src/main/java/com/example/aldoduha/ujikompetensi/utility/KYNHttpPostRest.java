package com.example.aldoduha.ujikompetensi.utility;

import com.example.aldoduha.ujikompetensi.connection.KYNSMPUtilities;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.X509TrustManager;

/**
 * Created by aldoduha on 12/17/2017.
 */

public class KYNHttpPostRest {
    public static final String HEADER_CONTENT_TYPE_URL_ENCODED = "application/json";
    public static final String HEADER_CONTENT_TYPE_KEY = "Content-Type";
    public static final String HEADER_CONTENT_LENGTH = "Content-Length";
    public static final String POST_METHOD = "POST";

    public static StringBuilder inputStreamToString(InputStream is) throws IOException {
        String line = null;
        StringBuilder result = new StringBuilder();

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        while ((line = br.readLine()) != null)
            result.append(line);

        return result;
    }

    private static HttpsURLConnection startPinning(String postURI, String httpMethod) throws Exception {
        HttpsURLConnection httpConn = null;
        X509TrustManager easyTrustManager = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                System.out.println("here");
            }
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                boolean isValid = false;
                for (int i = 0; i < chain.length; i++) {
                    RSAPublicKey pubkey = (RSAPublicKey) chain[i].getPublicKey();
                    String encoded = new BigInteger(1, pubkey.getEncoded()).toString(16);
                    System.out.println(encoded);
                    if (KYNIntentConstant.SSL_PUBLIC_KEY.equalsIgnoreCase(encoded)) {
                        isValid = true;
                        break;
                    }
                }
                if(!isValid) {
                    throw new IllegalArgumentException("public key invalid");
                }
            }
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        try {
            SSLContext sc = SSLContext.getInstance("TLSv1");
            httpConn = makeAConnection(postURI, httpMethod);
            sc.init(null, new X509TrustManager[]{easyTrustManager}, new java.security.SecureRandom());
            httpConn.setDefaultSSLSocketFactory(sc.getSocketFactory());
            if(!KYNSMPUtilities.isHandShake) {
                httpConn.connect();
                KYNSMPUtilities.isHandShake = true;
                SSLContext sc1 = SSLContext.getInstance("TLSv1");
                httpConn = makeAConnection(postURI, httpMethod);
                sc1.init(null, new X509TrustManager[]{easyTrustManager}, new java.security.SecureRandom());
                httpConn.setDefaultSSLSocketFactory(sc1.getSocketFactory());
            }
            return httpConn;
        } catch (SSLHandshakeException ex) {
            httpConn.disconnect();
            if(!KYNSMPUtilities.isHandShake) {
                KYNSMPUtilities.isHandShake = true;
                SSLContext sc = SSLContext.getInstance("TLSv1");
                httpConn = makeAConnection(postURI, httpMethod);
                sc.init(null, new X509TrustManager[]{easyTrustManager}, new java.security.SecureRandom());
                httpConn.setDefaultSSLSocketFactory(sc.getSocketFactory());
                return httpConn;
            } else {
                throw ex;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private static HttpsURLConnection makeAConnection(String postURI, String httpMethod) throws Exception {
        URL url = new URL(postURI);
        URLConnection conn = url.openConnection();
        if(httpMethod != null && httpMethod.equalsIgnoreCase("POST")) {
            conn.setDoInput(true);
            conn.setDoOutput(true);
        }
        HttpsURLConnection httpConn = (HttpsURLConnection) conn;
        return httpConn;
    }

    public static String postData(String postURI, String data) throws Exception {
        String result = null;
        byte[] bytesOfData = data.getBytes();
        String contentLength = String.valueOf(bytesOfData.length);
        HttpURLConnection httpConn = null;
        try {
            if(KYNIntentConstant.isUseGateway && postURI.toString().startsWith("https")) {
                httpConn = startPinning(postURI, "POST");
            } else {
                URL url = new URL(postURI);
                URLConnection conn = url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                httpConn = (HttpURLConnection) conn;
            }
            httpConn.setRequestMethod(POST_METHOD);
            httpConn.setRequestProperty(HEADER_CONTENT_TYPE_KEY, HEADER_CONTENT_TYPE_URL_ENCODED);
            httpConn.setRequestProperty(HEADER_CONTENT_LENGTH, contentLength);
            httpConn.setConnectTimeout(70000);
            httpConn.setReadTimeout(70000);
            httpConn.connect();

            DataOutputStream dos = null;

            OutputStream outputStream = httpConn.getOutputStream();
            dos = new DataOutputStream(outputStream);
            dos.write(bytesOfData);
            dos.flush();
            dos.close();

            int responseCode = httpConn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream content = httpConn.getInputStream();
                result = KYNHttpPostRest.inputStreamToString(content).toString();
                httpConn.getHeaderFields();
            }
            else {
                throw new IOException("Connection to server failed: " + responseCode + " "
                        + httpConn.getResponseMessage());
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (httpConn != null)
                httpConn.disconnect();
        }
        return result;
    }
}
