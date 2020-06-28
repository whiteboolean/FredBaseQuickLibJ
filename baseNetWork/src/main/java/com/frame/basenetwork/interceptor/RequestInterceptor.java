package com.frame.basenetwork.interceptor;

import android.text.TextUtils;

import com.frame.basenetwork.interfaces.INetWorkRequestInfo;

import java.io.IOException;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor {

    private INetWorkRequestInfo iNetWorkRequestInfo;

    public RequestInterceptor(INetWorkRequestInfo netWorkRequestInfo) {
        this.iNetWorkRequestInfo = netWorkRequestInfo;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        if (iNetWorkRequestInfo != null) {
            Set<String> strings = iNetWorkRequestInfo.getRequestHeaderMap().keySet();
            for (String key : strings) {
                String value = iNetWorkRequestInfo.getRequestHeaderMap().get(key);
                if (!TextUtils.isEmpty(value)) {
                    builder.addHeader(key, value);
                }
            }
        }
        return chain.proceed(builder.build());
    }
}
