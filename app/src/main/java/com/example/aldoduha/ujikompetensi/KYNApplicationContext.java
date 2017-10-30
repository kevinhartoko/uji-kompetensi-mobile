package com.example.aldoduha.ujikompetensi;

import android.app.Application;

/**
 * Created by aldoduha on 10/26/2017.
 */

public class KYNApplicationContext extends Application {
    private int tabPosition = 0;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public int getTabPosition() {
        return tabPosition;
    }

    public void setTabPosition(int tabPosition) {
        this.tabPosition = tabPosition;
    }
}
