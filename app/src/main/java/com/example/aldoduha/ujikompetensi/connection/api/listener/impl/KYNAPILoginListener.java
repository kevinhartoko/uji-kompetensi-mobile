package com.example.aldoduha.ujikompetensi.connection.api.listener.impl;

        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v4.content.LocalBroadcastManager;

        import com.example.aldoduha.ujikompetensi.connection.listener.KYNConnectionListener;
        import com.example.aldoduha.ujikompetensi.utility.KYNIntentConstant;

/**
 * Created by aldoduha on 12/18/2017.
 */

public class KYNAPILoginListener implements KYNConnectionListener {
    @Override
    public void onSend(Bundle bundle, Context context) {
        Intent intent = new Intent();
        intent.setAction(KYNIntentConstant.ACTION_LOGIN);
        intent.addCategory(KYNIntentConstant.CATEGORY_LOGIN);
        intent.putExtras(bundle);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
