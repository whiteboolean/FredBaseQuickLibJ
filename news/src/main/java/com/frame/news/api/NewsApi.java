package com.frame.news.api;


import com.blankj.utilcode.util.LogUtils;
import com.frame.basenetwork.api.ApiBase;
import com.frame.basenetwork.bean.NewsChannelsBean;
import com.frame.basenetwork.bean.NewsListBean;
import com.frame.basenetwork.utils.TecentUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.SingleObserver;

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public final class NewsApi extends ApiBase {
    private static volatile NewsApi instance = null;
    private NewsApiInterface newsApiInterface;
    public static final String PAGE = "page";

    private NewsApi() {
        super("https://service-o5ikp40z-1255468759.ap-shanghai.apigateway.myqcloud.com/");
        newsApiInterface = retrofit.create(NewsApiInterface.class);
    }

    public static NewsApi getInstance() {
        if (instance == null) {
            synchronized (NewsApi.class) {
                if (instance == null) {
                    instance = new NewsApi();
                }
            }
        }
        return instance;
    }


    /**
     * 用于获取新闻栏目
     *
     * @param observer 由调用者传过来的观察者对象
     */
    public void getNewsChannels(SingleObserver<NewsChannelsBean> observer) {
        Map<String, String> requestMap = new HashMap<>();
        String timeStr = TecentUtil.getTimeStr();
        ApiSubscribeIo(newsApiInterface.getNewsChannels("source", TecentUtil.getAuthorization(timeStr), timeStr, requestMap), observer);
    }

    /**
     * 用于获取新闻列表
     *
     * @param observer    由调用者传过来的观察者对象
     * @param channelId   类型ID
     * @param channelName 获取页号
     * @param page        获取页号
     */
    public void getNewsList(SingleObserver<NewsListBean> observer, String channelId, String channelName, String page) {
        Map<String, String> requestMap = new HashMap<>();
        String timeStr = TecentUtil.getTimeStr();
        requestMap.put("channelId", channelId);
        requestMap.put("channelName", channelName);
        requestMap.put(PAGE, page);
        ApiSubscribe(newsApiInterface.getNewsList("source", TecentUtil.getAuthorization(timeStr), timeStr, requestMap), observer);
    }
}
