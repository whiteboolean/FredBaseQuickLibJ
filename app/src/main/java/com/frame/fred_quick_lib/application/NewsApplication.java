package com.frame.fred_quick_lib.application;

import com.billy.cc.core.component.CC;
import com.frame.baselib.application.BaseApplication;
import com.frame.fred_quick_lib.BuildConfig;

public class NewsApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        setDebug(BuildConfig.DEBUG);
        CC.enableDebug(BuildConfig.DEBUG);
        CC.enableVerboseLog(BuildConfig.DEBUG);
        CC.enableRemoteCC(BuildConfig.DEBUG);
    }
}
