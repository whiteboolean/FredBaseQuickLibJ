package com.frame.baselib.application;

import android.app.Application;

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


    }
}
