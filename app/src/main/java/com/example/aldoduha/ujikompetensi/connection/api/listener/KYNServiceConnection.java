package com.example.aldoduha.ujikompetensi.connection.api.listener;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.example.aldoduha.ujikompetensi.KYNDatabaseHelper;
import com.example.aldoduha.ujikompetensi.connection.api.KYNAPILogin;
import com.example.aldoduha.ujikompetensi.connection.api.KYNAPILogout;
import com.example.aldoduha.ujikompetensi.connection.api.listener.impl.KYNAPILoginListener;
import com.example.aldoduha.ujikompetensi.connection.api.listener.impl.KYNAPILogoutListener;
import com.example.aldoduha.ujikompetensi.model.KYNUserModel;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aldoduha on 12/18/2017.
 */

public class KYNServiceConnection extends Service {
    private KYNDatabaseHelper database;
    private List<String> listPhoneNumberCustomerForAction = new ArrayList<String>();

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

//        System.loadLibrary("stlport_shared");
        SQLiteDatabase.loadLibs(getApplicationContext());

        database = new KYNDatabaseHelper(getApplicationContext());
        registerLocalBroadcastReceiver();
    }

    @Override
    public void onDestroy() {
        database.close();
        unregisterLocalBroadcastReceiver();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent==null)
            return super.onStartCommand(intent, flags, startId);

        if (intent.getAction().equals(KYNIntentConstant.ACTION_LOGIN)) {
            requestLogin(intent);
        }else if (intent.getAction().equals(KYNIntentConstant.ACTION_LOGOUT)) {
            requestLogout(intent);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void requestLogin(Intent intent){
        KYNUserModel userModel = (KYNUserModel)intent.getSerializableExtra(KYNIntentConstant.INTENT_EXTRA_DATA);

        KYNAPILogin requestLogin = new KYNAPILogin(getApplicationContext(), new KYNAPILoginListener());
        requestLogin.setData(userModel);
        requestLogin.execute();
    }

    private void requestLogout(Intent intent){
        KYNUserModel userModel = (KYNUserModel)intent.getSerializableExtra(KYNIntentConstant.INTENT_EXTRA_DATA);

        KYNAPILogout requestLogout = new KYNAPILogout(getApplicationContext(), new KYNAPILogoutListener());
        requestLogout.setData(userModel);
        requestLogout.execute();
    }

//    private void startTimeCounterForClearData(){
//        Thread dataKillerThread = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                while (isServiceStart) {
//                    try {
//                        Thread.sleep(MTFGenerator.getDiffTimeToMidNight());
//                        clearData();
//                    } catch (InterruptedException e) {
//
//                    } catch (Exception e) {
//                    }
//                }
//            }
//        });
//
//        dataKillerThread.start();
//    }

//    private void clearData(){
//        List<MTFLoanMasterModel> listLoanMasterDraft = database.getLoanMasterListBaseOnLoanStatus(MTFLoanStatus.DRAFT);
//        List<MTFLoanMasterModel> listLoanMasterApprove = database.getLoanMasterListBaseOnLoanStatus(MTFLoanStatus.DISETUJUI);
//        List<MTFLoanMasterModel> listLoanMasterReject = database.getLoanMasterListBaseOnLoanStatus(MTFLoanStatus.DITOLAK);
//        List<MTFLoanMasterModel> listLoanMasterTerkirim = database.getLoanMasterListBaseOnLoanStatus(MTFLoanStatus.TERKIRIM);
//
//        deleteDataLoanMaster(listLoanMasterDraft, MTFSystemParams.customerDataDuration);
//        deleteDataLoanMaster(listLoanMasterApprove, MTFSystemParams.customerCompleteDuration);
//        deleteDataLoanMaster(listLoanMasterReject, MTFSystemParams.customerCompleteDuration);
//        deleteDataLoanMaster(listLoanMasterTerkirim, MTFSystemParams.customerCompleteDuration);
//    }

//    private void deleteDataLoanMaster(List<MTFLoanMasterModel> listOfLoanMaster, int lifeTime){
//        for (int i = 0; i < listOfLoanMaster.size(); i++) {
//            MTFLoanMasterModel model = listOfLoanMaster.get(i);
//            if (MTFGenerator.isDataGreaterThanToday(model.getDate(), lifeTime)) {
//                MTFGenerator.deleteLoanMasterData(getApplicationContext(), model);
//                database.deleteLoanMasterDraft(model.getId());
//            }
//        }
//    }




    private void registerLocalBroadcastReceiver(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(KYNIntentConstant.ACTION_LOGIN);
        intentFilter.addCategory(KYNIntentConstant.CATEGORY_LOGIN);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(broadcastReceiver, intentFilter);
    }

    private void unregisterLocalBroadcastReceiver(){
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(broadcastReceiver);
    }
}
