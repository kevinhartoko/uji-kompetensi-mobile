package com.example.aldoduha.ujikompetensi.utility;

/**
 * Created by aldoduha on 12/18/2017.
 */

public class KYNJSONKey {
    public static String KEY_MESSAGE = "message";
    public static String KEY_JSON = "json";
    public static String KEY_USER_ID = "userId";
    public static String KEY_USERNAME = "username";
    public static String KEY_PASSWORD = "password";
    public static String KEY_RESULT = "status";
    public static String KEY_TOKEN = "token";
    public static String KEY_D = "d";
    public static String KEY_INTERVIEWEE_SERVER_ID = "id";

    public static String VAL_SUCCESS = "success";
    public static String VAL_ERROR = "error";
    public static String VAL_MESSAGE_FAILED = "Permintaan Gagal";

    public static String KEY_SERVER_ID = "id";
    //answer
    public static String KEY_ANSWER_ANSWER = "answer";
    public static String KEY_ANSWER_IS_TRUE = "is_true";
    public static String KEY_ANSWER_FK_INTERVIEWEE = "interviewee";
    public static String KEY_ANSWER_FK_QUESTION = "question";

    //feedback
    public static String KEY_FEEDBACK_FEEDBACK = "feedback";
    public static String KEY_FK_USER_FEEDBACK = "user";
    public static String KEY_FEEDBACK_FK_INTERVIEWEE = "interviewee";

    //question
    public static String KEY_QUESTION_DESCRIPTION = "description";
    public static String KEY_QUESTION_ANSWER_A = "answer_a";
    public static String KEY_QUESTION_ANSWER_B = "answer_b";
    public static String KEY_QUESTION_ANSWER_C = "answer_c";
    public static String KEY_QUESTION_ANSWER_D = "answer_d";
    public static String KEY_QUESTION_TRUE_ANSWER = "true_answer";
    public static String KEY_QUESTION_BOBOT = "bobot";
    public static String KEY_QUESTION_CODE = "code";

    //template
    public static String KEY_TEMPLATE_DESCRIPTION = "description";
    public static String KEY_TEMPLATE_TOTAL_QUESTION = "total_question";

    //template question
    public static String KEY_TEMPLATE_QUESITON_BANYAK_SOAL = "banyak_soal";
    public static String KEY_TEMPLATE_QUESTION_BOBOT = "bobot";
    public static String KEY_TEMPLATE_QUESTION_FK_TEMPLATE = "template";

    //user
    public static String KEY_USER_USERNAME = "username";
    public static String KEY_USER_NAME = "name";
    public static String KEY_USER_PASSWORD = "password";
    public static String KEY_USER_ROLE = "role";

    //interviewee
    public static String KEY_INTERVIEWEE_NAME = "name";
    public static String KEY_INTERVIEWEE_EMAIL = "email";
    public static String KEY_INTERVIEWEE_PHONE = "phone";
    public static String KEY_INTERVIEWEE_DOB = "dob";
    public static String KEY_INTERVIEWEE_SCORE = "score";
    public static String KEY_INTERVIEWEE_GENDER = "gender";
    //generate question
    public static String KEY_TEMPLATE = "template";
}
