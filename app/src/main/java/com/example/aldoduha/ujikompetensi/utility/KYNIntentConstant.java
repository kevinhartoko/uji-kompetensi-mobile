package com.example.aldoduha.ujikompetensi.utility;

/**
 * Created by aldoduha on 10/9/2017.
 */

public class KYNIntentConstant {
    public static String SSL_PUBLIC_KEY;
    public static String SSL_CHECK_SUM;
    public static final boolean isUseGateway = true;
    public static boolean needSS = false;

    public static final String BUNDLE_KEY_LOADING_SHOW = "loading_show";
    public static final String BUNDLE_KEY_ALERT_SHOW = "alert_show";
    public static final String BUNDLE_KEY_MESSAGE = "message";
    public static final String BUNDLE_KEY_RESULT = "result";
    public static final String BUNDLE_KEY_CODE = "code";
    public static final String BUNDLE_KEY_TITLE = "title";
    public static final String BUNDLE_KEY_JSON = "json";
    public static final String BUNDLE_KEY_USERNAME = "username";
    public static final String BUNDLE_KEY_TOKEN = "token";

    public static final String INTENT_EXTRA_CODE = "code";
    public static final String INTENT_EXTRA_DATA = "data";

    //action
    public static final String ACTION_LOGIN = "login";

    //category
    public static final String CATEGORY_LOGIN = "login";

    public static final int CODE_FAILED = 0;
    public static final int CODE_FAILED_TOKEN = 215;

    //result code
    public static final int REQUEST_CODE_QUESTION_DETAIL = 1;
    public static final int RESULT_CODE_QUESTION_DETAIL = 2;
    public static final int REQUEST_CODE_USER_DETAIL = 3;
    public static final int RESULT_CODE_USER_DETAIL = 4;
    public static final int REQUEST_CODE_TEMPLATE_DETAIL = 5;
    public static final int RESULT_CODE_TEMPLATE_DETAIL = 6;
    public static final int REQUEST_CODE_INTERVIEWEE_DETAIL = 7;
    public static final int RESULT_CODE_INTERVIEWEE_DETAIL = 8;

    //code for api
    public static final int CODE_LOGIN_SUCCESS = 300;
    public static final int CODE_LOGIN_FAILED = 301;
}
