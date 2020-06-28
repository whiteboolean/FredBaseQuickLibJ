package com.frame.news.api;


import com.frame.basenetwork.bean.NewsChannelsBean;
import com.frame.basenetwork.bean.NewsListBean;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;


public interface NewsApiInterface {

    @GET("release/news")
    Single<NewsListBean> getNewsList(
            @Header("Source") String source,
            @Header("Authorization") String authorization,
            @Header("Date") String date,
            @QueryMap Map<String, String> options);

    @GET("release/channel")
    @Headers("Cache-Control:public ,max-age=60000")
    Single<NewsChannelsBean> getNewsChannels(
            @Header("Source") String source,
            @Header("Authorization") String authorization,
            @Header("Date") String date,
            @QueryMap Map<String, String> options);
}
