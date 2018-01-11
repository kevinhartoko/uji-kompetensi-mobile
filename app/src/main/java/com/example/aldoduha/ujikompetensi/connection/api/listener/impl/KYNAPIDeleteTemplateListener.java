package com.example.aldoduha.ujikompetensi.connection.api.listener.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.example.aldoduha.ujikompetensi.connection.listener.KYNConnectionListener;
import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

/**
 * Created by aldoduha on 1/11/2018.
 */

public class KYNAPIDeleteTemplateListener implements KYNConnectionListener {
    @Override
    public void onSend(Bundle bundle, Context context) {
        Intent intent = new Intent();
        intent.setAction(KYNIntentConstant.ACTION_DELETE_TEMPLATE);
        intent.addCategory(KYNIntentConstant.CATEGORY_DELETE_TEMPLATE);
        intent.putExtras(bundle);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}