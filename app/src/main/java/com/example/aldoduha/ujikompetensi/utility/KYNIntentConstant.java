package com.example.aldoduha.ujikompetensi.utility;

/**
 * Created by aldoduha on 10/9/2017.
 */

public class    KYNIntentConstant {
    public static String SSL_PUBLIC_KEY;
    public static String SSL_CHECK_SUM;
    public static final boolean isUseGateway = true;
    public static boolean needSS = false;
    public static final String DATE_FORMAT = "dd-MM-yyyy";

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
    public static final String INTENT_EXTRA_USERNAME = "username";
    public static final String INTENT_EXTRA_STRING = "string";

    //action
    public static final String ACTION_LOGIN = "login";
    public static final String ACTION_LOGOUT = "logout";
    public static final String ACTION_USER_DETAIL = "user_detail";
    public static final String ACTION_INTERVIEWEE_DETAIL = "interviewee_detail";
    public static final String ACTION_QUESTION_DETAIL = "question_detail";
    public static final String ACTION_TEMPLATE_DETAIL = "template_detail";
    public static final String ACTION_USER_LIST = "user_list";
    public static final String ACTION_QUESTION_LIST = "question_list";
    public static final String ACTION_INTERVIEWEE_LIST = "interviewee_list";
    public static final String ACTION_TEMPLATE_LIST = "template_list";
    public static final String ACTION_SUBMIT_INTERVIEWEE_DATA = "submit_interviewee_data";
    public static final String ACTION_SUBMIT_QUESTION = "submit_question";
    public static final String ACTION_SUBMIT_USER = "submit_user";
    public static final String ACTION_SUBMIT_TEMPLATE = "submit_template";
    public static final String ACTION_SUBMIT_FEEDBACK = "submit_feedback";
    public static final String ACTION_GENERATE_QUESTION = "generate_question";

    //category
    public static final String CATEGORY_LOGIN = "category_login";
    public static final String CATEGORY_LOGOUT = "category_logout";
    public static final String CATEGORY_USER_DETAIL = "category_user_detail";
    public static final String CATEGORY_INTERVIEWEE_DETAIL = "category_interviewee_detail";
    public static final String CATEGORY_QUESTION_DETAIL = "category_question_detail";
    public static final String CATEGORY_TEMPLATE_DETAIL = "category_template_detail";
    public static final String CATEGORY_USER_LIST = "category_user_list";
    public static final String CATEGORY_QUESTION_LIST = "category_question_list";
    public static final String CATEGORY_INTERVIEWEE_LIST = "category_interviewee_list";
    public static final String CATEGORY_TEMPLATE_LIST = "category_template_list";
    public static final String CATEGORY_SUBMIT_INTERVIEWEE_DATA = "category_submit_interviewee_data";
    public static final String CATEGORY_SUBMIT_QUESTION = "category_submit_question";
    public static final String CATEGORY_SUBMIT_USER = "category_submit_user";
    public static final String CATEGORY_SUBMIT_TEMPLATE = "category_submit_template";
    public static final String CATEGORY_SUBMIT_FEEDBACK = "category_submit_feedback";
    public static final String CATEGORY_GENERATE_QEUSTION = "category_generate_question";

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
    public static final int CODE_LOGOUT_SUCCESS = 302;
    public static final int CODE_LOGOUT_FAILED = 303;
    public static final int CODE_USER_DETAIL_SUCCESS = 304;
    public static final int CODE_USER_DETAIL_FAILED = 305;
    public static final int CODE_INTERVIEWEE_DETAIL_SUCCESS = 306;
    public static final int CODE_INTERVIEWEE_DETAIL_FAILED = 307;
    public static final int CODE_QUESTION_DETAIL_SUCCESS = 308;
    public static final int CODE_QUESTION_DETAIL_FAILED = 309;
    public static final int CODE_TEMPLATE_DETAIL_SUCCESS = 310;
    public static final int CODE_TEMPLATE_DETAIL_FAILED = 311;
    public static final int CODE_USER_LIST_SUCCESS = 312;
    public static final int CODE_USER_LIST_FAILED = 313;
    public static final int CODE_QUESTION_LIST_SUCCESS = 314;
    public static final int CODE_QUESTION_LIST_FAILED = 315;
    public static final int CODE_TEMPLATE_LIST_SUCCESS = 316;
    public static final int CODE_TEMPLATE_LIST_FAILED = 317;
    public static final int CODE_INTERVIEWEE_LIST_SUCCESS = 318;
    public static final int CODE_INTERVIEWEE_LIST_FAILED = 319;
    public static final int CODE_SUBMIT_INTERVIEWEE_DATA_SUCCESS = 320;
    public static final int CODE_SUBMIT_INTERVIEWEE_DATA_FAILED = 321;
    public static final int CODE_SUBMIT_QUESTION_SUCCESS = 322;
    public static final int CODE_SUBMIT_QUESTION_FAILED = 323;
    public static final int CODE_SUBMIT_USER_SUCCESS = 324;
    public static final int CODE_SUBMIT_USER_FAILED = 325;
    public static final int CODE_SUBMIT_TEMPLATE_SUCCESS = 326;
    public static final int CODE_SUBMIT_TEMPLATE_FAILED = 327;
    public static final int CODE_SUBMIT_FEEDBACK_SUCCESS = 328;
    public static final int CODE_SUBMIT_FEEDBACK_FAILED = 329;
    public static final int CODE_GENERATE_QUESTION_SUCCESS = 330;
    public static final int CODE_GENERATE_QUESTION_FAILED = 331;
}
