package com.frame.basenetwork.interceptor;

import android.text.TextUtils;

import com.blankj.utilcode.util.NetworkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCacheInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //如果服务设置相应的缓存策略，则遵从服务器不设置
//        if (response.header("Cache_Control") == null) {
//            return response;
//        }
        if (!NetworkUtils.isAvailable()) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);
        //无网会自动访问缓存
        //有网会访问缓存 (超时访问缓存)
        if (NetworkUtils.isAvailable()) {
            String cacheControl = String.valueOf(request.cacheControl().maxAgeSeconds());
            Response.Builder builder = response.newBuilder();
            builder.removeHeader("Pragma");//清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
            if (cacheControl.equals("-1")) {
                //如果请求参数上Headers为空，则返回-1，则统一设置失效时间为60s
                int maxAge = 60 * 60;
                builder.header("Cache-Control", "public ,max-age=" + maxAge);
            } else {
                builder.header("Cache-Control", "public ,max-age=" + cacheControl);
            }
            return builder.build();
        } else {
            int maxTime = 60 * 60 * 24 * 7;//缓存过期时间为七天
            return response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxTime)
                    .removeHeader("Pragma")
                    .build();
        }
    }
}