package com.frame.fred_quick_lib.application;



import com.blankj.utilcode.util.NetworkUtils;
import com.frame.basenetwork.interfaces.INetWorkRequestInfo;
import com.frame.fred_quick_lib.BuildConfig;

import java.util.HashMap;

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class NetworkRequestInfo implements INetWorkRequestInfo {
    HashMap<String, String> headers = new HashMap<>();

    public NetworkRequestInfo(){
        headers.put("os", "android");
        headers.put("versionName", BuildConfig.VERSION_NAME);
        headers.put("versionCode", String.valueOf(BuildConfig.VERSION_CODE));
//        headers.put("applicationId", BuildConfig.APPLICATION_ID);
        headers.put("Accept-Charset", "utf-8");
        headers.put("Accept", "application/xml");
        headers.put("Connection", "Keep-Alive");
        headers.put("Accept-Language", "zh-CN,en,*");
//        headers.put("Cookie", "SESSION=" + YiboPreference.instance(context).getToken());
        headers.put("X-Requested-With", "XMLHttpRequest");//仿AJAX访问
        headers.put("User-Agent", "android/" + BuildConfig.VERSION_NAME);
    }

    @Override
    public HashMap<String, String> getRequestHeaderMap() {
        return headers;
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
