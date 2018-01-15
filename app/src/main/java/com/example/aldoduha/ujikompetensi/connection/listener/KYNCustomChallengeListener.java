package com.example.aldoduha.ujikompetensi.connection.listener;

import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;
import com.sap.mobile.lib.request.HttpChannelListeners;

import java.math.BigInteger;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;

/**
 * Created by aldoduha on 12/17/2017.
 */

public class KYNCustomChallengeListener implements HttpChannelListeners.ISSLChallengeListener {
    private static boolean checkServerTrust(
            X509Certificate[] paramArrayOfX509Certificate) {
        for (int i = 0;; i++) {
            if (i >= paramArrayOfX509Certificate.length) {
                return false;
            }
            String str1 = new BigInteger(1,
                    ((RSAPublicKey) paramArrayOfX509Certificate[i]
                            .getPublicKey()).getEncoded()).toString(16);
        }
    }

    public boolean isServerTrusted(X509Certificate[] paramArrayOfX509Certificate) {
        return checkServerTrust(paramArrayOfX509Certificate);
    }
}
