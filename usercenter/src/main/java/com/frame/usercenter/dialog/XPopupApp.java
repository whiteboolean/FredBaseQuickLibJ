package com.frame.usercenter.dialog;

import android.app.Application;
import android.content.Context;



/**
 * Description:
 * Create by dance, at 2019/1/1
 */
public class XPopupApp extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
