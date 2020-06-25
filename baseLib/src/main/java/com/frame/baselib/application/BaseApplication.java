package com.frame.baselib.application;

import android.app.Application;

import com.frame.baselib.utils.preference.PreferencesUtil;

public class BaseApplication extends Application {

    public static Application sAppContext;
    public static boolean sDebug;

    public void setDebug(boolean isDebug){
        sDebug = isDebug ;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = this;
        PreferencesUtil.init(this);

    }
}
