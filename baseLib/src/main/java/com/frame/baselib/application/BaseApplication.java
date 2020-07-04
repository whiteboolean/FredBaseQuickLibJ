package com.frame.baselib.application;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.facebook.stetho.Stetho;
import com.frame.baselib.BuildConfig;
import com.frame.baselib.utils.preference.PreferencesUtil;
import com.frame.baselib.view.loadsir.CustomCallback;
import com.frame.baselib.view.loadsir.EmptyCallback;
import com.frame.baselib.view.loadsir.ErrorCallback;
import com.frame.baselib.view.loadsir.LoadingCallback;
import com.frame.baselib.view.loadsir.LottieEmptyCallback;
import com.frame.baselib.view.loadsir.LottieLoadingCallback;
import com.frame.baselib.view.loadsir.TimeoutCallback;
import com.kingja.loadsir.core.LoadSir;
import com.squareup.leakcanary.LeakCanary;

public class BaseApplication extends Application {

    public static Application sAppContext;
    public static boolean sDebug;

    public void setDebug(boolean isDebug) {
        sDebug = isDebug;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = this;
        PreferencesUtil.init(this);
        Utils.init(this);
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())//添加各种状态页
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                .addCallback(new LottieEmptyCallback())
                .addCallback(new LottieLoadingCallback())
                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
                .commit();

        if (sDebug) {
            Stetho.initializeWithDefaults(this);
            if (!LeakCanary.isInAnalyzerProcess(this)) {
                LeakCanary.install(this);
            }
        }
    }
}
