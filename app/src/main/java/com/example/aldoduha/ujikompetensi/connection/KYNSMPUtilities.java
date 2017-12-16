package com.example.aldoduha.ujikompetensi.connection;

import android.content.Context;

import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;
import com.sap.mobile.lib.configuration.Constants;
import com.sap.mobile.lib.configuration.Preferences;
import com.sap.mobile.lib.configuration.PreferencesException;
import com.sap.mobile.lib.parser.IODataSchema;
import com.sap.mobile.lib.parser.IODataServiceDocument;
import com.sap.mobile.lib.parser.Parser;
import com.sap.mobile.lib.parser.ParserException;
import com.sap.mobile.lib.request.ConnectivityParameters;
import com.sap.mobile.lib.request.HttpChannelListeners;
import com.sap.mobile.lib.request.RequestManager;
import com.sap.mobile.lib.supportability.Logger;
import com.sap.smp.rest.AppSettings;
import com.sap.smp.rest.ClientConnection;
import com.sap.smp.rest.UserManager;
import com.sybase.persistence.DataVault;
import com.sybase.persistence.PrivateDataVault;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;

/**
 * Created by aldoduha on 12/17/2017.
 */

public class KYNSMPUtilities {
    public static int dbVer;

    public static String username = "";
    public static String password = "";
    public static String appPasscode = "a#sb$2sdv";
    public static String appSaltcode = "Dnx3srik1zse0e";

    public static Boolean isHttpRequest;
    public static String requestType;
    public static String host;
    public static String port;
    public static int portHttps;
    
    public static String secConfig = "";
    public static String appId = "";
    
    public static String domain = "default";
    public static String appConnId = "";
    public static Logger logger;
    public static Preferences pref;
    public static ConnectivityParameters param;
    public static RequestManager reqMan;
    public static Parser parser;
    public static String certificateFileName = "meap-uat.cer";

    public static IODataServiceDocument serviceDoc = null;
    public static IODataSchema metaDoc = null;

    public static final String dataVaultName = "data_vault_pantara_sinaya";

    public static ClientConnection clientConnection = null;
    public static UserManager userManager = null;
    public static AppSettings appSettings = null;

    //	public static String SECRET_SIGNATURE = "";
    public static String JWT = "";
//	public static String KEY = "";

    public static String getDataValultName() {
        return dataVaultName + appId + username;
    }

    public static void initializeRequestManager(final Context context) {
        if (reqMan != null) {
            return;
        }

        if (username.equals("") || password.equals("")) {
            if (PrivateDataVault.vaultExists(KYNSMPUtilities.dataVaultName)) {
                DataVault dataVault = PrivateDataVault
                        .getVault(KYNSMPUtilities.dataVaultName);
                dataVault.unlock(KYNSMPUtilities.appPasscode,
                        KYNSMPUtilities.appPasscode
                                + KYNSMPUtilities.appSaltcode);

                KYNSMPUtilities.username = dataVault.getString("username");
                KYNSMPUtilities.password = dataVault.getString("password");
            }
        }

        if (username.equals("") || password.equals("")) {
            return;
        }

        try {
            logger = new Logger();
            pref = new Preferences(context, logger);
            pref.setStringPreference(Preferences.CONNECTIVITY_HANDLER_CLASS_NAME, Constants.HTTP_HANDLER_CLASS);
            pref.setBooleanPreference(Preferences.REQUEST_ENABLE_SNI_TLS_FACTORY, true);
            pref.setIntPreference(Preferences.CONNECTIVITY_HTTPS_PORT,portHttps);
            pref.setBooleanPreference(Preferences.PERSISTENCE_SECUREMODE, false);
            pref.setIntPreference(Preferences.CONNECTIVITY_CONNTIMEOUT,1200000);
            pref.setIntPreference(Preferences.CONNECTIVITY_SCONNTIMEOUT,1200000);

            param = new ConnectivityParameters();
            param.setUserName(username);
            param.setUserPassword(password);
            param.enableXsrf(true);

            try {
                parser = new Parser(pref, logger);
            } catch (ParserException e) {

            }

            reqMan = new RequestManager(logger, pref, param, 1);

            reqMan.setSSLChallengeListener(new HttpChannelListeners.ISSLChallengeListener() {
                @Override
                public boolean isServerTrusted(X509Certificate[] certificate) {
                    return checkServerTrust(certificate);
                }
            });
        } catch (PreferencesException e) {
            final Writer result = new StringWriter();
            final PrintWriter printWriter = new PrintWriter(result);
            e.printStackTrace(printWriter);
//            String stacktrace = result.toString();
//            KYNFileLogger.getInstance().addString("REQ_MAN_INIT", stacktrace);
//            KYNFileLogger.getInstance().writeAndReset("REQ_MAN_INIT");
            e.printStackTrace();
        }
    }

    public static void configurationForRegistration(Context applicationContext) {
        PrivateDataVault.init(applicationContext);
        KYNConnectionManager.init();
        initializeRequestManager(applicationContext);
        if(reqMan != null){
            KYNConnectionManager.init();
            clientConnection = new ClientConnection(applicationContext, appId, domain, secConfig, reqMan);
            clientConnection.setConnectionProfile(requestType + host);
            userManager = new UserManager(clientConnection);
            appSettings = new AppSettings(clientConnection);
            reqMan = null;

            clientConnection.setConnectionProfile(isHttpRequest, host, port, null, null);
        }else{

        }
    }

    private static boolean checkServerTrust(X509Certificate[] certificate) {
        // Hack ahead: BigInteger and toString(). We know a DER encoded Public
        // Key starts with 0x30 (ASN.1 SEQUENCE and CONSTRUCTED), so there is
        // no leading 0x00 to drop.
        boolean expected = false;
        for (int i = 0; i < certificate.length; i++) {
            RSAPublicKey pubkey = (RSAPublicKey) certificate[i].getPublicKey();
            String encoded = new BigInteger(1, pubkey.getEncoded()).toString(16);
            // String checkSum = KYNGenerator.generateCheckSum(certificate[i].getSignature());
            // Pin it!
            if (KYNIntentConstant.SSL_PUBLIC_KEY.equalsIgnoreCase(encoded)) {
                expected = true;
                break;
            }
        }
        return expected;
    }
}
