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
    public static boolean isConnectServer = false;

    public static String username = "";
    public static String password = "";
    public static String appPasscode = "a#sb$2sdv";
    public static String appSaltcode = "Dnx3srik1zse0e";

    public static Boolean isHttpRequest;
    public static String requestType;
    public static String host;
    public static String port;
    public static int portHttps;

    public static String appIdLoginRest = "/users/login";
    public static String appIdLogoutRest = "eformws/rest/user/logout";
    public static String appIdUserListRest = "/users/getUserList";
    public static String appIdQuestionListRest = "/questions/getQuestionList";
    public static String appIdTemplateListRest = "/templates/getTemplateList";
    public static String appIdIntervieweeListRest = "/interviewee/getIntervieweeList";
    public static String appIdGenerateQuestionRest = "/questions/generateQuestion";
    public static String appIdSubmitQuestionRest = "/questions/saveQuestion";
    public static String appIdSubmitUserRest = "/users/saveUser";
    public static String appIdSubmitTemplateRest = "/templates/saveTemplate";
    public static String appIdSubmitFeedbackRest = "/feedbacks/saveFeedback";
    public static String appIdSubmitIntervieweeDataRest = "eformws/rest/user/submitintervieweedata";
    public static String appIdIntervieweeDetailRest = "/interviewee/findDetailInterviewee";
    public static String appIdDeleteUserRest = "/users/deleteUser   ";
    public static String appIdDeleteQuestionRest = "/questions/deleteQuestion";
    public static String appIdDeleteTemplateRest = "/templates/deleteTemplate";
    
    public static String secConfig = "";
    public static String appId = "";
    
    public static String appConnId = "";

    public static final String dataVaultName = "data_vault_uji_kompetensi";

    public static UserManager userManager = null;

    public static String getDataValultName() {
        return dataVaultName + appId + username;
    }
}
