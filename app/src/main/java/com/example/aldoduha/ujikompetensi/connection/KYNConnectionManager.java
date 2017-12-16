package com.example.aldoduha.ujikompetensi.connection;

import android.content.Context;

import com.example.aldoduha.ujikompetensi.connection.listener.KYNCustomChallengeListener;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;
import com.sap.mobile.lib.configuration.Preferences;
import com.sap.mobile.lib.request.ConnectivityParameters;
import com.sap.mobile.lib.request.HttpChannelListeners;
import com.sap.mobile.lib.request.HttpsTrustManager;
import com.sap.mobile.lib.request.RequestManager;
import com.sap.mobile.lib.supportability.Logger;
import com.sap.smp.rest.ClientConnection;
import com.sap.smp.rest.SMPException;
import com.sap.smp.rest.UserManager;
import com.sybase.persistence.DataVault;
import com.sybase.persistence.PrivateDataVault;

/**
 * Created by aldoduha on 12/17/2017.
 */

public class KYNConnectionManager {
    public static String appId = "rfb.app.eform";
    public static String domain = "default";
    public static String host;
    public static String port;
    public static int portHttps;
    public static String certName;
    public static String requestType;
    public static String secConfig;
    public static String dataKYN;
    public static int dbVersion;
    ClientConnection clientConnection;
    private Context mContext;
    public static RequestManager requestManager;
    public UserManager userManager;
    private KYNCustomChallengeListener localCustomChallengeListener;
    public ConnectivityParameters param;
    protected DataVault dataVault;

    static {
        configureNEWSIT();
//        configureUATPT();
//        configureNEWUATDemo();
//        configureNEWUAT();
//        configureProdNew();
    }

    /**
     *  Empty method to initialize value
     */
    public static void  init(){}

    private static void configureNEWSIT(){
        KYNSMPUtilities.isHttpRequest = false;
        if(KYNIntentConstant.isUseGateway) {
            KYNSMPUtilities.requestType = requestType = "https://";
            KYNSMPUtilities.host = host = "appapisit02.dev.corp.btpn.co.id";
            KYNSMPUtilities.port = port = "9501";
        }else {
            KYNSMPUtilities.requestType = requestType = "http://";
            //KYNSMPUtilities.host = host = "192.168.43.58";
            //KYNSMPUtilities.host = host = "192.168.43.155";
            //KYNSMPUtilities.host = host = "10.157.205.112";
            //KYNSMPUtilities.host = host = "172.20.10.3";
            KYNSMPUtilities.host = host = "192.168.1.79";
            //KYNSMPUtilities.host = host = "10.157.205.112";
            //KYNSMPUtilities.host = host = "localhost";
            KYNSMPUtilities.port = port = "8080";
        }
        KYNSMPUtilities.portHttps = portHttps = Preferences.DEFAULT_HTTPS_PORT;
        KYNSMPUtilities.secConfig = secConfig = "EFORM-SDB";
        dataKYN = "data.KYN";
        KYNIntentConstant.SSL_PUBLIC_KEY = "30820122300d06092a864886f70d01010105000382010f003082010a0282010100e149f7f5d57ac3047d7d72fe603e08c8ca88172b36d146e81410acb76523a710b7404f7859f1034b91f2a0d9712eb9de36dba4d9cbe4c539bfbe09f68bba6163ddfcf3aecc3dba2131fb0833f60c59ea6a6488c42e77b7cfb99271c55bedd9751479aeff120c9bbf4a15d3f62c2536fe40ba8aeee28a0de36a956cbba6036edddb7bbf3ffd78da95495393561f2bb8b1c47fad918386013b986ff8f5c2ef90380468ffcf22899299587a6826a9dca6247a79ddd3f8cf04aa729c1dfa2813e19bc48ebb4d9a1625426daa5a7acf60041365c222c6dbab0a4efde32ae98bbef6098cbd7adba3d3cc4de5bd44c32c48a7c2f92299f2394f132d4b1e98fc4766c4270203010001";
        KYNIntentConstant.SSL_CHECK_SUM 	= "174653844";
//        KYNConstants.BTPN_API_KEY_VALUE 	= "8428c326-ae66-47c7-9b06-1797ac513911";
        dbVersion = 1;
        KYNIntentConstant.needSS = true;
    }

    public KYNConnectionManager(Context paramContext) {
        this.mContext = paramContext;
        PrivateDataVault.init(mContext);
        initialize();
    }

    private void initialize() {
        if (KYNSMPUtilities.username.equals("") || KYNSMPUtilities.password.equals("")) {
            if (PrivateDataVault.vaultExists(KYNSMPUtilities.dataVaultName)) {
                DataVault dataVault = PrivateDataVault.getVault(KYNSMPUtilities.dataVaultName);
                dataVault.unlock(KYNSMPUtilities.appPasscode, KYNSMPUtilities.appPasscode + KYNSMPUtilities.appSaltcode);

                KYNSMPUtilities.username = dataVault.getString("username");
                KYNSMPUtilities.password = dataVault.getString("password");
            }
        }

        Logger localLogger = new Logger();
        Preferences localPreferences = new Preferences(this.mContext,localLogger);
        param = new ConnectivityParameters();
//        String username= "";
//        String password = "";
//        try {
//            KYNAES.generateKey();
//            username = KYNAES.encryptString(KYNSMPUtilities.username);
//            password = KYNAES.encryptString(KYNSMPUtilities.password);
//        } catch (Exception e) {
//
//        }
        param.setUserName(KYNSMPUtilities.username);
        param.setUserPassword(KYNSMPUtilities.password);
        param.setBaseUrl(requestType + host);
        requestManager = new RequestManager(localLogger, localPreferences,param, 1);
        localCustomChallengeListener = new KYNCustomChallengeListener();
        requestManager.setMutualSSLChallengeListener(new HttpChannelListeners.IMutualSSLChallengeListener() {
            public HttpsTrustManager.HttpsClientCertInfo getClientCertificate() {
                return null;
            }
        });
        requestManager.setSSLChallengeListener(localCustomChallengeListener);
        this.clientConnection = new ClientConnection(this.mContext, appId, domain, secConfig, requestManager);
        this.clientConnection.setConnectionProfile(requestType + host);
        this.userManager = new UserManager(this.clientConnection);
    }

    public void onBoard() {
        try {
            if(userManager.getApplicationConnectionId().equals("")){
                userManager.registerUser(true);
            }else{
                userManager.registerUser(false);
            }

            KYNSMPUtilities.appConnId = userManager.getApplicationConnectionId();
            if(!userManager.getApplicationConnectionId().equals("")){
                PrivateDataVault.init(mContext);

                if(PrivateDataVault.vaultExists(KYNSMPUtilities.getDataValultName())){
                    dataVault = PrivateDataVault.getVault(KYNSMPUtilities.getDataValultName());
                    dataVault.unlock(KYNSMPUtilities.appPasscode, KYNSMPUtilities.appPasscode+ KYNSMPUtilities.appSaltcode);
                }else{
                    dataVault = PrivateDataVault.createVault(KYNSMPUtilities.getDataValultName(), KYNSMPUtilities.appPasscode, KYNSMPUtilities.appPasscode+ KYNSMPUtilities.appSaltcode);
                }
                if(KYNSMPUtilities.appConnId.equals("")){
                    dataVault.setString("appCid", KYNSMPUtilities.userManager.getApplicationConnectionId());
                    KYNSMPUtilities.appConnId = KYNSMPUtilities.userManager.getApplicationConnectionId();
                }else{
                    dataVault.setString("appCid", KYNSMPUtilities.appConnId);
                }
                dataVault.setString("username", KYNSMPUtilities.username);
                dataVault.setString("password", KYNSMPUtilities.password);
                dataVault.setString("host", KYNSMPUtilities.host);
                dataVault.setString("port", KYNSMPUtilities.port);
                dataVault.setString("isHttp", KYNSMPUtilities.isHttpRequest.toString());
                dataVault.lock();
            }
        } catch (SMPException localSMPException) {
            localSMPException.printStackTrace();
            //return;
        }
    }
}
