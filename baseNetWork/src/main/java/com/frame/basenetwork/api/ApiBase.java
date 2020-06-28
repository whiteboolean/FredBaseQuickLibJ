package com.frame.basenetwork.api;

import com.frame.baselib.application.BaseApplication;
import com.frame.basenetwork.errorhandler.AppDataErrorHandler;
import com.frame.basenetwork.errorhandler.HttpErrorHandler;
import com.frame.basenetwork.interceptor.AddCacheInterceptor;
import com.frame.basenetwork.interceptor.RequestInterceptor;
import com.frame.basenetwork.interceptor.ResponseInterceptor;
import com.frame.basenetwork.interfaces.INetWorkRequestInfo;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiBase {

    protected Retrofit retrofit;
    protected static INetWorkRequestInfo netWorkRequestInfo;
    private static RequestInterceptor sHttpsRequestInterceptor;
    private static ResponseInterceptor sHttpsResponseInterceptor;
    private static ErrorTransformer sErrorTransformer = new ErrorTransformer();

    protected ApiBase(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS);
        addInterceptor(builder);
        return builder.build();
    }

    private void addInterceptor(OkHttpClient.Builder builder) {
        //统一添加网络参数到请求头
        if (sHttpsRequestInterceptor != null) {
            builder.addInterceptor(sHttpsRequestInterceptor);
        }
        //网络请求返回的时候的数据处理
        if (sHttpsResponseInterceptor != null) {
            builder.addInterceptor(sHttpsResponseInterceptor);
        }
        //打印日志级别
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        boolean debug = netWorkRequestInfo.isDebug();
        if (debug) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        builder.addInterceptor(httpLoggingInterceptor);
        //cache url
        File httpCacheDirectory = new File(BaseApplication.sAppContext.getCacheDir(), "responses");
        // 50 MiB
        int cacheSize = 50 * 1024 * 1024;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        builder.cache(cache);
        //缓存拦截器
        builder.addNetworkInterceptor(new AddCacheInterceptor());
    }

    public static void setNetWorkRequestInfo(INetWorkRequestInfo info) {
        netWorkRequestInfo = info;
        sHttpsRequestInterceptor = new RequestInterceptor(info);
        sHttpsResponseInterceptor = new ResponseInterceptor();
    }


    /**
     * 封装线程管理和订阅的过程
     */
    protected <T> void ApiSubscribe(Single<T> observable, SingleObserver<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(sErrorTransformer)
                .subscribe(observer);
    }

    protected <T> void ApiSubscribeIo(Single<T> observable, SingleObserver<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .compose(sErrorTransformer)
                .subscribe(observer);
    }

    /**
     * 处理错误的变换
     * 网络请求的错误处理，其中网络错误分为两类：
     * 1、http请求相关的错误，例如：404，403，socket timeout等等；
     * 2、http请求正常，但是返回的应用数据里提示发生了异常，表明服务器已经接收到了来自客户端的请求，但是由于
     * 某些原因，服务器没有正常处理完请求，可能是缺少参数，或者其他原因；
     */
    private static class ErrorTransformer<T> implements SingleTransformer {
        @Override
        public SingleSource apply(Single upstream) {
            //onErrorResumeNext当发生错误的时候，由另外一个Observable来代替当前的Observable并继续发射数据
            return upstream
                    .map(new AppDataErrorHandler())/*返回的数据统一错误处理*/
                    .onErrorResumeNext(new HttpErrorHandler<T>());/*Http 错误处理**/
        }
    }

}
