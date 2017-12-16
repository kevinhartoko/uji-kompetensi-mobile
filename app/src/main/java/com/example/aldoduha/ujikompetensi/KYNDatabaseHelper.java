package com.example.aldoduha.ujikompetensi;

import android.content.ContentValues;
import android.content.Context;

import com.example.aldoduha.ujikompetensi.model.KYNFeedbackModel;
import com.example.aldoduha.ujikompetensi.model.KYNIntervieweeModel;
import com.example.aldoduha.ujikompetensi.model.KYNQuestionModel;
import com.example.aldoduha.ujikompetensi.model.KYNTemplateModel;
import com.example.aldoduha.ujikompetensi.model.KYNTemplateQuestionModel;
import com.example.aldoduha.ujikompetensi.model.KYNUserModel;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aldoduha on 10/9/2017.
 */

public class KYNDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "uji_kompetensi.db";
    private static final String LEGACY_DATABASE_NAME = "constants-ujikompetensi.db";
    private static final byte[] PASSPHRASE = new byte[]{65, 13, -32, 64, -89, 23, -53, -50, -80, 54, -10, -40};
    private static final int SCHEMA = 1;
    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    private static final String TABLE_INTERVIEWEE = "interviewee";
    private static final String TABLE_QUESTION = "question";
    private static final String TABLE_USER = "user";
    private static final String TABLE_SESSION = "session";
    private static final String TABLE_TEMPLATE = "template";
    private static final String TABLE_TEMPLATE_QUESTION = "template_question";
    private static final String TABLE_FEEDBACK = "feedback";

    //interviewee
    private static final String INTERVIEWEE_ID = "id";
    private static final String INTERVIEWEE_NAMA = "nama";
    private static final String INTERVIEWEE_EMAIL = "email";
    private static final String INTERVIEWEE_HANDPHONE = "handphone";
    private static final String INTERVIEWEE_ADDRESS = "address";
    private static final String INTERVIEWEE_DOB = "dob";
    private static final String INTERVIEWEE_GENDER = "gender";

    //question
    private static final String QUESTION_ID = "id";
    private static final String QUESTION_FK_INTERVIEWEE = "fk_interviewee";
    private static final String QUESTION_NAME = "name";
    private static final String QUESTION_QUESTION = "question";
    private static final String QUESTION_ANSWER_1 = "answer1";
    private static final String QUESTION_ANSWER_2 = "answer2";
    private static final String QUESTION_ANSWER_3 = "answer3";
    private static final String QUESTION_ANSWER_4 = "answer4";
    private static final String QUESTION_BOBOT = "bobot";
    private static final String QUESTION_INTERVIEWEE_ANSWER = "interviewee_answer";
    private static final String QUESTION_KEY_ANSWER = "key_answer";

    //user
    private static final String USER_ID = "id";
    private static final String USER_NAMA = "nama";
    private static final String USER_USERNAME = "username";
    private static final String USER_ROLE = "role";
    private static final String USER_PASSWORD = "password";

    //session
    private static final String SESSION_ID = "id";
    private static final String SESSION_NAMA = "nama";
    private static final String SESSION_USERNAME = "username";
    private static final String SESSION_ROLE = "role";
    private static final String SESSION_PASSWORD = "password";
    private static final String SESSION_TOKEN = "token";

    //template
    private static final String TEMPLATE_ID = "id";
    private static final String TEMPLATE_NAMA = "nama";
    private static final String TEMPLATE_JUMLAH_SOAL = "jumlah_soal";

    //template question
    private static final String TEMPLATE_QUESTION_ID = "id";
    private static final String TEMPLATE_QUESTION_FK_TEMPLATE = "fk_template";
    private static final String TEMPLATE_QUESTION_JUMLAH_SOAL = "jumlah_soal";
    private static final String TEMPLATE_QUESTION_BOBOT = "bobot";

    //feedback
    private static final String FEEDBACK_ID = "id";
    private static final String FEEDBACK_DESCRIPTION = "description";
    private static final String FEEDBACK_NAME = "name";
    private static final String FEEDBACK_FK_INTERVIEWEE = "fk_interviewee";

    private static final String QUERY_CREATE_TABLE_INTERVIEWEE =
            "CREATE TABLE " + TABLE_INTERVIEWEE + " (" +
                    INTERVIEWEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    INTERVIEWEE_NAMA + " TEXT, " +
                    INTERVIEWEE_EMAIL + " TEXT, " +
                    INTERVIEWEE_HANDPHONE + " TEXT, " +
                    INTERVIEWEE_ADDRESS + " TEXT, " +
                    INTERVIEWEE_GENDER + " TEXT, " +
                    INTERVIEWEE_DOB + " NUMERIC);";

    private static final String QUERY_CREATE_TABLE_QUESTION =
            "CREATE TABLE " + TABLE_QUESTION + " (" +
                    QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    QUESTION_FK_INTERVIEWEE + " INTEGER, " +
                    QUESTION_NAME + " TEXT, " +
                    QUESTION_QUESTION + " TEXT, " +
                    QUESTION_ANSWER_1 + " TEXT, " +
                    QUESTION_ANSWER_2 + " TEXT, " +
                    QUESTION_ANSWER_3 + " TEXT, " +
                    QUESTION_ANSWER_4 + " TEXT, " +
                    QUESTION_INTERVIEWEE_ANSWER + " TEXT, " +
                    QUESTION_BOBOT + " NUMERIC, " +
                    QUESTION_KEY_ANSWER + " TEXT);";

    private static final String QUERY_CREATE_TABLE_USER =
            "CREATE TABLE " + TABLE_USER + " (" +
                    USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_NAMA + " TEXT, " +
                    USER_USERNAME + " TEXT, " +
                    USER_PASSWORD + " TEXT, " +
                    USER_ROLE + " TEXT);";

    private static final String QUERY_CREATE_TABLE_SESSION =
            "CREATE TABLE " + TABLE_SESSION + " (" +
                    SESSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SESSION_NAMA + " TEXT, " +
                    SESSION_USERNAME + " TEXT, " +
                    SESSION_PASSWORD + " TEXT, " +
                    SESSION_TOKEN + " TEXT, " +
                    SESSION_ROLE + " TEXT);";

    private static final String QUERY_CREATE_TABLE_TEMPLATE =
            "CREATE TABLE " + TABLE_TEMPLATE + " (" +
                    TEMPLATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TEMPLATE_NAMA + " TEXT, " +
                    TEMPLATE_JUMLAH_SOAL + " NUMERIC);";

    private static final String QUERY_CREATE_TABLE_TEMPLATE_QUESTION =
            "CREATE TABLE " + TABLE_TEMPLATE_QUESTION + " (" +
                    TEMPLATE_QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TEMPLATE_QUESTION_FK_TEMPLATE + " INTEGER, " +
                    TEMPLATE_QUESTION_BOBOT + " NUMERIC, " +
                    TEMPLATE_QUESTION_JUMLAH_SOAL + " NUMERIC);";

    private static final String QUERY_CREATE_TABLE_FEEDBACK =
            "CREATE TABLE " + TABLE_FEEDBACK + " (" +
                    FEEDBACK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FEEDBACK_FK_INTERVIEWEE + " INTEGER, " +
                    FEEDBACK_DESCRIPTION + " TEXT, " +
                    FEEDBACK_NAME + " TEXT);";

    private Context context;

    public KYNDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
        this.context = context;
    }

    private static KYNDatabaseHelper instance;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_TABLE_INTERVIEWEE);
        db.execSQL(QUERY_CREATE_TABLE_QUESTION);
        db.execSQL(QUERY_CREATE_TABLE_USER);
        db.execSQL(QUERY_CREATE_TABLE_SESSION);
        db.execSQL(QUERY_CREATE_TABLE_TEMPLATE);
        db.execSQL(QUERY_CREATE_TABLE_TEMPLATE_QUESTION);
        db.execSQL(QUERY_CREATE_TABLE_FEEDBACK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            default:
                break;
        }
    }

    static void encrypt(Context ctxt) {
        SQLiteDatabase.loadLibs(ctxt);

        File dbFile = ctxt.getDatabasePath(DATABASE_NAME);
        File legacyFile = ctxt.getDatabasePath(LEGACY_DATABASE_NAME);

        if (!dbFile.exists() && legacyFile.exists()) {
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(legacyFile,
                    "", null);

            db.rawExecSQL(String.format(
                    "ATTACH DATABASE '%s' AS encrypted KEY '%s';",
                    dbFile.getAbsolutePath(), PASSPHRASE));
            db.rawExecSQL("SELECT sqlcipher_export('encrypted')");
            db.rawExecSQL("DETACH DATABASE encrypted;");

            int version = db.getVersion();

            db.close();

            db = SQLiteDatabase.openOrCreateDatabase(dbFile, new String(PASSPHRASE), null);
            db.setVersion(version);
            db.close();

            legacyFile.delete();
        }
    }

    public static KYNDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new KYNDatabaseHelper(context.getApplicationContext());
            encrypt(context);
        }
        return instance;
    }

    @Override
    public synchronized void close() {
        super.close();
    }

    SQLiteDatabase getReadableDatabase() {
        return (super.getReadableDatabase(new String(PASSPHRASE)));
    }

    SQLiteDatabase getWritableDatabase() {
        return (super.getWritableDatabase(new String(PASSPHRASE)));
    }

    //insert
    public Long insertInterviewee(KYNIntervieweeModel model) {
        if (model == null) {
            return null;
        }

        Long id = -1l;

        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(INTERVIEWEE_NAMA, model.getNama());
            values.put(INTERVIEWEE_EMAIL, model.getEmail());
            values.put(INTERVIEWEE_HANDPHONE, model.getHandphone());
            values.put(INTERVIEWEE_ADDRESS, model.getAddress());
            if (model.getDob() != null) {
                values.put(INTERVIEWEE_DOB, format.format(model.getDob()));
            } else {
                values.put(INTERVIEWEE_DOB, "");
            }
            values.put(INTERVIEWEE_GENDER, model.getGender());

            id = db.insert(TABLE_INTERVIEWEE, null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            if (db.isOpen()) {
                db.close();
            }
        }
        return id;
    }

    public Long insertQuestion(KYNQuestionModel model) {
        if (model == null) {
            return null;
        }

        Long id = -1l;
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(QUESTION_NAME, model.getName());
            values.put(QUESTION_QUESTION, model.getQuestion());
            values.put(QUESTION_ANSWER_1, model.getAnswer1());
            values.put(QUESTION_ANSWER_2, model.getAnswer2());
            values.put(QUESTION_ANSWER_3, model.getAnswer3());
            values.put(QUESTION_ANSWER_4, model.getAnswer4());
            values.put(QUESTION_INTERVIEWEE_ANSWER, model.getIntervieweeAnswer());
            values.put(QUESTION_KEY_ANSWER, model.getKeyAnswer());
            values.put(QUESTION_BOBOT, model.getBobot());
            if (model.getIntervieweeModel() != null && model.getIntervieweeModel().getId() != null) {
                values.put(QUESTION_FK_INTERVIEWEE, model.getIntervieweeModel().getId());
            }

            id = db.insert(TABLE_QUESTION, null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            if (db.isOpen()) {
                db.close();
            }
        }
        return id;
    }

    public Long insertUser(KYNUserModel model) {
        if (model == null) {
            return null;
        }

        Long id = -1l;

        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(USER_NAMA, model.getNama());
            values.put(USER_USERNAME, model.getUsername());
            values.put(USER_PASSWORD, model.getPassword());
            values.put(USER_ROLE, model.getRole());

            id = db.insert(TABLE_USER, null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            if (db.isOpen()) {
                db.close();
            }
        }
        return id;
    }

    public Long insertSession(KYNUserModel model) {
        if (model == null) {
            return null;
        }

        Long id = -1l;

        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(SESSION_NAMA, model.getNama());
            values.put(SESSION_USERNAME, model.getUsername());
            values.put(SESSION_PASSWORD, model.getPassword());
            values.put(SESSION_ROLE, model.getRole());
            values.put(SESSION_TOKEN, model.getToken());

            id = db.insert(TABLE_SESSION, null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            if (db.isOpen()) {
                db.close();
            }
        }
        return id;
    }

    public Long insertTemplate(KYNTemplateModel model) {
        if (model == null) {
            return null;
        }

        Long id = -1l;

        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(TEMPLATE_NAMA, model.getNama());
            values.put(TEMPLATE_JUMLAH_SOAL, model.getJumlahSoal());

            id = db.insert(TABLE_TEMPLATE, null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            if (db.isOpen()) {
                db.close();
            }
        }
        return id;
    }

    public Long insertTemplateQuestion(KYNTemplateQuestionModel model) {
        if (model == null) {
            return null;
        }

        Long id = -1l;

        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(TEMPLATE_QUESTION_BOBOT, model.getBobot());
            values.put(TEMPLATE_QUESTION_JUMLAH_SOAL, model.getJumlahSoal());
            values.put(TEMPLATE_QUESTION_FK_TEMPLATE, model.getTemplateModel().getId());

            id = db.insert(TABLE_TEMPLATE_QUESTION, null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            if (db.isOpen()) {
                db.close();
            }
        }
        return id;
    }

    public Long insertFeedback(KYNFeedbackModel model) {
        if (model == null) {
            return null;
        }

        Long id = -1l;

        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(FEEDBACK_DESCRIPTION, model.getDescription());
            values.put(FEEDBACK_NAME, model.getName());
            values.put(FEEDBACK_FK_INTERVIEWEE, model.getIntervieweeModel().getId());

            id = db.insert(TABLE_FEEDBACK, null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            if (db.isOpen()) {
                db.close();
            }
        }
        return id;
    }

    //update
    public void updateInterviewee(KYNIntervieweeModel model) {
        if (model == null) {
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();

            values.put(INTERVIEWEE_NAMA, model.getNama());
            values.put(INTERVIEWEE_EMAIL, model.getEmail());
            values.put(INTERVIEWEE_HANDPHONE, model.getHandphone());
            values.put(INTERVIEWEE_ADDRESS, model.getAddress());
            if (model.getDob() != null) {
                values.put(INTERVIEWEE_DOB, format.format(model.getDob()));
            } else {
                values.put(INTERVIEWEE_DOB, "");
            }
            values.put(INTERVIEWEE_GENDER, model.getGender());

            db.update(TABLE_INTERVIEWEE, values, INTERVIEWEE_ID + " =? ", new String[]{model.getId() + ""});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void updateQuestion(KYNQuestionModel model) {
        if (model == null) {
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();

            values.put(QUESTION_NAME, model.getName());
            values.put(QUESTION_QUESTION, model.getQuestion());
            values.put(QUESTION_ANSWER_1, model.getAnswer1());
            values.put(QUESTION_ANSWER_2, model.getAnswer2());
            values.put(QUESTION_ANSWER_3, model.getAnswer3());
            values.put(QUESTION_ANSWER_4, model.getAnswer4());
            values.put(QUESTION_INTERVIEWEE_ANSWER, model.getIntervieweeAnswer());
            values.put(QUESTION_KEY_ANSWER, model.getKeyAnswer());
            if (model.getIntervieweeModel() != null && model.getIntervieweeModel().getId() != null) {
                values.put(QUESTION_FK_INTERVIEWEE, model.getIntervieweeModel().getId());
            }
            values.put(QUESTION_BOBOT, model.getBobot());

            db.update(TABLE_QUESTION, values, QUESTION_ID + " =? ", new String[]{model.getId() + ""});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void updateQuestionIntervieweeAnswer(KYNQuestionModel model) {
        if (model == null) {
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();

            values.put(QUESTION_INTERVIEWEE_ANSWER, model.getIntervieweeAnswer());

            db.update(TABLE_QUESTION, values, QUESTION_ID + " =? ", new String[]{model.getId() + ""});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void updateQuestionIntervieweeId(Long intervieweeId) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(QUESTION_FK_INTERVIEWEE, intervieweeId);

            db.update(TABLE_QUESTION, values, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void updateUser(KYNUserModel model) {
        if (model == null) {
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();

            values.put(USER_NAMA, model.getNama());
            values.put(USER_USERNAME, model.getUsername());
            values.put(USER_PASSWORD, model.getPassword());
            values.put(USER_ROLE, model.getRole());

            db.update(TABLE_USER, values, USER_ID + " =? ", new String[]{model.getId() + ""});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void updateSession(KYNUserModel model) {
        if (model == null) {
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();

            values.put(SESSION_NAMA, model.getNama());
            values.put(SESSION_USERNAME, model.getUsername());
            values.put(SESSION_PASSWORD, model.getPassword());
            values.put(SESSION_ROLE, model.getRole());
            values.put(SESSION_TOKEN, model.getToken());

            db.update(TABLE_SESSION, values, SESSION_ID + " =? ", new String[]{model.getId() + ""});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void updateTemplate(KYNTemplateModel model) {
        if (model == null) {
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();

            values.put(TEMPLATE_NAMA, model.getNama());
            values.put(TEMPLATE_JUMLAH_SOAL, model.getJumlahSoal());

            db.update(TABLE_TEMPLATE, values, TEMPLATE_ID + " =? ", new String[]{model.getId() + ""});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    //get
    public KYNIntervieweeModel getInterviewee(Long intervieweeId) {
        KYNIntervieweeModel result = new KYNIntervieweeModel();
        String mQuery = "SELECT * FROM " + TABLE_INTERVIEWEE + " WHERE " + INTERVIEWEE_ID + "='" + intervieweeId + "'";
        SQLiteDatabase mReadableDatabase = getReadableDatabase();
        Cursor mCursor = mReadableDatabase.rawQuery(mQuery, null);
        try {
            if (mCursor.moveToFirst()) {
                result.setId(mCursor.getLong(mCursor.getColumnIndex(INTERVIEWEE_ID)));
                result.setNama(mCursor.getString(mCursor.getColumnIndex(INTERVIEWEE_NAMA)));
                result.setEmail(mCursor.getString(mCursor.getColumnIndex(INTERVIEWEE_EMAIL)));
                result.setAddress(mCursor.getString(mCursor.getColumnIndex(INTERVIEWEE_ADDRESS)));
                try {
                    result.setDob(format.parse(mCursor.getString(mCursor.getColumnIndex(INTERVIEWEE_DOB))));
                } catch (Exception e) {

                }
                result.setHandphone(mCursor.getString(mCursor.getColumnIndex(INTERVIEWEE_HANDPHONE)));
                result.setGender(mCursor.getString(mCursor.getColumnIndex(INTERVIEWEE_GENDER)));
            }
        } catch (Exception e) {

        } finally {
            mCursor.close();
        }
        return result;
    }

    public List<KYNIntervieweeModel> getListInterviewee() {
        ArrayList result = new ArrayList();
        String mQuery = "SELECT * FROM " + TABLE_INTERVIEWEE;
        SQLiteDatabase mReadableDatabase = getReadableDatabase();
        Cursor mCursor = mReadableDatabase.rawQuery(mQuery, null);
        try {
            if (mCursor.moveToFirst()) {
                do {
                    KYNIntervieweeModel model = new KYNIntervieweeModel();
                    model.setId(mCursor.getLong(mCursor.getColumnIndex(INTERVIEWEE_ID)));
                    model.setNama(mCursor.getString(mCursor.getColumnIndex(INTERVIEWEE_NAMA)));
                    model.setEmail(mCursor.getString(mCursor.getColumnIndex(INTERVIEWEE_EMAIL)));
                    model.setAddress(mCursor.getString(mCursor.getColumnIndex(INTERVIEWEE_ADDRESS)));
                    try {
                        model.setDob(format.parse(mCursor.getString(mCursor.getColumnIndex(INTERVIEWEE_DOB))));
                    } catch (Exception e) {

                    }
                    model.setHandphone(mCursor.getString(mCursor.getColumnIndex(INTERVIEWEE_HANDPHONE)));
                    model.setGender(mCursor.getString(mCursor.getColumnIndex(INTERVIEWEE_GENDER)));
                    result.add(model);
                }while (mCursor.moveToNext());
            }
        } catch (Exception e) {

        } finally {
            mCursor.close();
        }
        return result;
    }

    public KYNQuestionModel getQuestion(Long questionId) {
        KYNQuestionModel result = new KYNQuestionModel();
        String mQuery = "SELECT * FROM " + TABLE_QUESTION + " WHERE " + QUESTION_ID + "='" + questionId + "'";
        SQLiteDatabase mReadableDatabase = getReadableDatabase();
        Cursor mCursor = mReadableDatabase.rawQuery(mQuery, null);
        try {
            if (mCursor.moveToFirst()) {
                result.setId(mCursor.getLong(mCursor.getColumnIndex(QUESTION_ID)));
                result.setName(mCursor.getString(mCursor.getColumnIndex(QUESTION_NAME)));
                result.setQuestion(mCursor.getString(mCursor.getColumnIndex(QUESTION_QUESTION)));
                result.setAnswer1(mCursor.getString(mCursor.getColumnIndex(QUESTION_ANSWER_1)));
                result.setAnswer2(mCursor.getString(mCursor.getColumnIndex(QUESTION_ANSWER_2)));
                result.setAnswer3(mCursor.getString(mCursor.getColumnIndex(QUESTION_ANSWER_3)));
                result.setAnswer4(mCursor.getString(mCursor.getColumnIndex(QUESTION_ANSWER_4)));
                result.setBobot(mCursor.getInt(mCursor.getColumnIndex(QUESTION_BOBOT)));
                result.setIntervieweeAnswer(mCursor.getString(mCursor.getColumnIndex(QUESTION_INTERVIEWEE_ANSWER)));
                result.setKeyAnswer(mCursor.getString(mCursor.getColumnIndex(QUESTION_KEY_ANSWER)));
                result.setIntervieweeModel(getInterviewee(mCursor.getLong(mCursor.getColumnIndex(QUESTION_FK_INTERVIEWEE))));
            }
        } catch (Exception e) {

        } finally {
            mCursor.close();
        }
        return result;
    }

    public List<KYNQuestionModel> getListQuestion() {
        ArrayList result = new ArrayList();
        String mQuery = "SELECT * FROM " + TABLE_QUESTION;
        SQLiteDatabase mReadableDatabase = getReadableDatabase();
        Cursor mCursor = mReadableDatabase.rawQuery(mQuery, null);
        try {
            if (mCursor.moveToFirst()) {
                do {
                    KYNQuestionModel model = new KYNQuestionModel();
                    model.setId(mCursor.getLong(mCursor.getColumnIndex(QUESTION_ID)));
                    model.setName(mCursor.getString(mCursor.getColumnIndex(QUESTION_NAME)));
                    model.setQuestion(mCursor.getString(mCursor.getColumnIndex(QUESTION_QUESTION)));
                    model.setAnswer1(mCursor.getString(mCursor.getColumnIndex(QUESTION_ANSWER_1)));
                    model.setAnswer2(mCursor.getString(mCursor.getColumnIndex(QUESTION_ANSWER_2)));
                    model.setAnswer3(mCursor.getString(mCursor.getColumnIndex(QUESTION_ANSWER_3)));
                    model.setAnswer4(mCursor.getString(mCursor.getColumnIndex(QUESTION_ANSWER_4)));
                    model.setBobot(mCursor.getInt(mCursor.getColumnIndex(QUESTION_BOBOT)));
                    model.setIntervieweeAnswer(mCursor.getString(mCursor.getColumnIndex(QUESTION_INTERVIEWEE_ANSWER)));
                    model.setKeyAnswer(mCursor.getString(mCursor.getColumnIndex(QUESTION_KEY_ANSWER)));
                    model.setIntervieweeModel(getInterviewee(mCursor.getLong(mCursor.getColumnIndex(QUESTION_FK_INTERVIEWEE))));

                    result.add(model);
                } while (mCursor.moveToNext());
            }
        } catch (Exception e) {

        } finally {
            mCursor.close();
        }
        return result;
    }

    public List<KYNQuestionModel> getListQuestion(Long intervieweeId) {
        ArrayList result = new ArrayList();
        String mQuery = "SELECT * FROM " + TABLE_QUESTION + " WHERE " + QUESTION_FK_INTERVIEWEE + "='" + intervieweeId + "'";
        SQLiteDatabase mReadableDatabase = getReadableDatabase();
        Cursor mCursor = mReadableDatabase.rawQuery(mQuery, null);
        try {
            if (mCursor.moveToFirst()) {
                do {
                    KYNQuestionModel model = new KYNQuestionModel();
                    model.setId(mCursor.getLong(mCursor.getColumnIndex(QUESTION_ID)));
                    model.setName(mCursor.getString(mCursor.getColumnIndex(QUESTION_NAME)));
                    model.setQuestion(mCursor.getString(mCursor.getColumnIndex(QUESTION_QUESTION)));
                    model.setAnswer1(mCursor.getString(mCursor.getColumnIndex(QUESTION_ANSWER_1)));
                    model.setAnswer2(mCursor.getString(mCursor.getColumnIndex(QUESTION_ANSWER_2)));
                    model.setAnswer3(mCursor.getString(mCursor.getColumnIndex(QUESTION_ANSWER_3)));
                    model.setAnswer4(mCursor.getString(mCursor.getColumnIndex(QUESTION_ANSWER_4)));
                    model.setBobot(mCursor.getInt(mCursor.getColumnIndex(QUESTION_BOBOT)));
                    model.setIntervieweeAnswer(mCursor.getString(mCursor.getColumnIndex(QUESTION_INTERVIEWEE_ANSWER)));
                    model.setKeyAnswer(mCursor.getString(mCursor.getColumnIndex(QUESTION_KEY_ANSWER)));
                    model.setIntervieweeModel(getInterviewee(mCursor.getLong(mCursor.getColumnIndex(QUESTION_FK_INTERVIEWEE))));

                    result.add(model);
                } while (mCursor.moveToNext());
            }
        } catch (Exception e) {

        } finally {
            mCursor.close();
        }
        return result;
    }

    public List<KYNUserModel> getUsers() {
        ArrayList result = new ArrayList();
        String mQuery = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase mReadableDatabase = getReadableDatabase();
        Cursor mCursor = mReadableDatabase.rawQuery(mQuery, null);
        try {
            if (mCursor.moveToFirst()) {
                do {
                    KYNUserModel model = new KYNUserModel();
                    model.setId(mCursor.getLong(mCursor.getColumnIndex(USER_ID)));
                    model.setNama(mCursor.getString(mCursor.getColumnIndex(USER_NAMA)));
                    model.setUsername(mCursor.getString(mCursor.getColumnIndex(USER_USERNAME)));
                    model.setPassword(mCursor.getString(mCursor.getColumnIndex(USER_PASSWORD)));
                    model.setRole(mCursor.getString(mCursor.getColumnIndex(USER_ROLE)));

                    result.add(model);
                } while (mCursor.moveToNext());
            }
        } catch (Exception e) {

        } finally {
            mCursor.close();
        }
        return result;
    }

    public KYNUserModel getUser(Long userId) {
        KYNUserModel model = new KYNUserModel();
        String mQuery = "SELECT * FROM " + TABLE_USER + " WHERE " + USER_ID + "='" + userId + "'";
        SQLiteDatabase mReadableDatabase = getReadableDatabase();
        Cursor mCursor = mReadableDatabase.rawQuery(mQuery, null);
        try {
            if (mCursor.moveToFirst()) {
                model.setId(mCursor.getLong(mCursor.getColumnIndex(USER_ID)));
                model.setNama(mCursor.getString(mCursor.getColumnIndex(USER_NAMA)));
                model.setUsername(mCursor.getString(mCursor.getColumnIndex(USER_USERNAME)));
                model.setPassword(mCursor.getString(mCursor.getColumnIndex(USER_PASSWORD)));
                model.setRole(mCursor.getString(mCursor.getColumnIndex(USER_ROLE)));
            }
        } catch (Exception e) {

        } finally {
            mCursor.close();
        }
        return model;
    }

    public KYNUserModel getSession() {
        KYNUserModel model = new KYNUserModel();
        String mQuery = "SELECT * FROM " + TABLE_SESSION;
        SQLiteDatabase mReadableDatabase = getReadableDatabase();
        Cursor mCursor = mReadableDatabase.rawQuery(mQuery, null);
        try {
            if (mCursor.moveToFirst()) {
                model.setId(mCursor.getLong(mCursor.getColumnIndex(SESSION_ID)));
                model.setNama(mCursor.getString(mCursor.getColumnIndex(SESSION_NAMA)));
                model.setUsername(mCursor.getString(mCursor.getColumnIndex(SESSION_USERNAME)));
                model.setPassword(mCursor.getString(mCursor.getColumnIndex(SESSION_PASSWORD)));
                model.setRole(mCursor.getString(mCursor.getColumnIndex(SESSION_ROLE)));
                model.setToken(mCursor.getString(mCursor.getColumnIndex(SESSION_TOKEN)));
            }
        } catch (Exception e) {

        } finally {
            mCursor.close();
        }
        return model;
    }

    public KYNTemplateModel getTemplate(Long templateId) {
        KYNTemplateModel result = new KYNTemplateModel();
        String mQuery = "SELECT * FROM " + TABLE_TEMPLATE + " WHERE " + TEMPLATE_ID + "='" + templateId + "'";
        SQLiteDatabase mReadableDatabase = getReadableDatabase();
        Cursor mCursor = mReadableDatabase.rawQuery(mQuery, null);
        try {
            if (mCursor.moveToFirst()) {
                result.setId(mCursor.getLong(mCursor.getColumnIndex(TEMPLATE_ID)));
                result.setNama(mCursor.getString(mCursor.getColumnIndex(TEMPLATE_NAMA)));
                result.setJumlahSoal(mCursor.getInt(mCursor.getColumnIndex(TEMPLATE_JUMLAH_SOAL)));
            }
        } catch (Exception e) {

        } finally {
            mCursor.close();
        }
        return result;
    }

    public List<KYNTemplateModel> getTemplateList() {
        ArrayList result = new ArrayList();
        String mQuery = "SELECT * FROM " + TABLE_TEMPLATE;
        SQLiteDatabase mReadableDatabase = getReadableDatabase();
        Cursor mCursor = mReadableDatabase.rawQuery(mQuery, null);
        try {
            if (mCursor.moveToFirst()) {
                do {
                    KYNTemplateModel model = new KYNTemplateModel();
                    model.setId(mCursor.getLong(mCursor.getColumnIndex(TEMPLATE_ID)));
                    model.setNama(mCursor.getString(mCursor.getColumnIndex(TEMPLATE_NAMA)));
                    model.setJumlahSoal(mCursor.getInt(mCursor.getColumnIndex(TEMPLATE_JUMLAH_SOAL)));

                    result.add(model);
                } while (mCursor.moveToNext());
            }
        } catch (Exception e) {

        } finally {
            mCursor.close();
        }
        return result;
    }

    public List<KYNTemplateQuestionModel> getTemplateQuestionList(Long templateId) {
        ArrayList result = new ArrayList();
        String mQuery = "SELECT * FROM " + TABLE_TEMPLATE_QUESTION + " WHERE " + TEMPLATE_QUESTION_FK_TEMPLATE + "='" + templateId + "'";
        SQLiteDatabase mReadableDatabase = getReadableDatabase();
        Cursor mCursor = mReadableDatabase.rawQuery(mQuery, null);
        try {
            if (mCursor.moveToFirst()) {
                do {
                    KYNTemplateQuestionModel model = new KYNTemplateQuestionModel();
                    model.setId(mCursor.getLong(mCursor.getColumnIndex(TEMPLATE_QUESTION_ID)));
                    model.setBobot(mCursor.getInt(mCursor.getColumnIndex(TEMPLATE_QUESTION_BOBOT)));
                    model.setJumlahSoal(mCursor.getInt(mCursor.getColumnIndex(TEMPLATE_QUESTION_JUMLAH_SOAL)));
                    model.setTemplateModel(getTemplate(mCursor.getLong(mCursor.getColumnIndex(TEMPLATE_QUESTION_FK_TEMPLATE))));

                    result.add(model);
                } while (mCursor.moveToNext());
            }
        } catch (Exception e) {

        } finally {
            mCursor.close();
        }
        return result;
    }

    public List<KYNFeedbackModel> getFeedbackList(Long intervieweeId) {
        ArrayList result = new ArrayList();
        String mQuery = "SELECT * FROM " + TABLE_FEEDBACK + " WHERE " + FEEDBACK_FK_INTERVIEWEE + "='" + intervieweeId + "'";
        SQLiteDatabase mReadableDatabase = getReadableDatabase();
        Cursor mCursor = mReadableDatabase.rawQuery(mQuery, null);
        try {
            if (mCursor.moveToFirst()) {
                do {
                    KYNFeedbackModel model = new KYNFeedbackModel();
                    model.setId(mCursor.getLong(mCursor.getColumnIndex(FEEDBACK_ID)));
                    model.setDescription(mCursor.getString(mCursor.getColumnIndex(FEEDBACK_DESCRIPTION)));
                    model.setName(mCursor.getString(mCursor.getColumnIndex(FEEDBACK_NAME)));
                    model.setIntervieweeModel(getInterviewee(mCursor.getLong(mCursor.getColumnIndex(FEEDBACK_FK_INTERVIEWEE))));

                    result.add(model);
                } while (mCursor.moveToNext());
            }
        } catch (Exception e) {

        } finally {
            mCursor.close();
        }
        return result;
    }

    //delete
    public void deleteInterviewee() {
        delete(TABLE_INTERVIEWEE, null, null);
    }

    public void deleteInterviewee(Long id) {
        delete(TABLE_INTERVIEWEE, INTERVIEWEE_ID + " =? ", new String[]{id + ""});
    }

    public void deleteQuestion() {
        delete(TABLE_QUESTION, null, null);
    }

    public void deleteQuestion(Long id) {
        delete(TABLE_QUESTION, QUESTION_ID + " =? ", new String[]{id + ""});
    }

    public void deleteUser(Long id) {
        delete(TABLE_USER, USER_ID + " =? ", new String[]{id + ""});
    }

    public void deleteUser() {
        delete(TABLE_USER, null, null);
    }
    public void deleteSession() {
        delete(TABLE_SESSION, null, null);
    }

    public void deleteTemplate(Long id) {
        delete(TABLE_TEMPLATE, TEMPLATE_ID + " =? ", new String[]{id + ""});
    }

    public void deleteTemplate() {
        delete(TABLE_TEMPLATE, null, null);
    }

    public void deleteTemplateQuestion(Long id) {
        delete(TABLE_TEMPLATE_QUESTION, TEMPLATE_QUESTION_FK_TEMPLATE + " =? ", new String[]{id + ""});
    }

    public void deleteTemplateQuestion() {
        delete(TABLE_TEMPLATE_QUESTION, null, null);
    }

    public void deleteFeedback(Long id) {
        delete(TABLE_FEEDBACK, FEEDBACK_ID + " =? ", new String[]{id + ""});
    }

    public void deleteFeedback() {
        delete(TABLE_FEEDBACK, null, null);
    }


    //UTILITY HELPER TO DATABASE
    private void insert(String tableName, ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            db.insert(tableName, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            if (db.isOpen()) {
                db.close();
            }
        }
    }

    private void update(String tableName, ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            db.update(tableName, values, whereClause, whereArgs);
            db.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            db.endTransaction();
            if (db.isOpen()) {
                db.close();
            }
        }
    }

    private void delete(String tableName, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            db.delete(tableName, whereClause, whereArgs);
            db.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            db.endTransaction();
            if (db.isOpen()) {
                db.close();
            }
        }
    }
}
