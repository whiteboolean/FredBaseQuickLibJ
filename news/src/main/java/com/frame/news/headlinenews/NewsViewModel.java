package com.frame.news.headlinenews;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.frame.baselib.viewmodel.BaseViewModel;
import com.frame.basenetwork.bean.Channel;
import com.frame.basenetwork.bean.NewsChannelsBean;
import com.frame.basenetwork.bean.NewsListBean;
import com.frame.basenetwork.errorhandler.ExceptionHandle;
import com.frame.basenetwork.observer.BaseSingleObserver;
import com.frame.news.api.NewsApi;

import java.util.ArrayList;
import java.util.List;

public class NewsViewModel extends BaseViewModel {

    public MutableLiveData<List<Channel>> channelList = new MutableLiveData<>();


    public NewsViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * 获取网络数据
     */
    protected void loadChannelData(OnLoadDataListener onLoadDataListener) {
        NewsApi.getInstance()
                .getNewsChannels(new BaseSingleObserver<NewsChannelsBean>(this) {
                    @Override
                    public void onSuccessful(NewsChannelsBean newsChannelsBean) {
                        ArrayList<Channel> channels = new ArrayList<>();
                        for (NewsChannelsBean.ChannelList source : newsChannelsBean.showapiResBody.channelList) {
                            Channel channel = new Channel();
                            channel.channelId = source.channelId;
                            channel.channelName = source.name;
                            channels.add(channel);
                        }
                        channelList.postValue(channels);
                        onLoadDataListener.onLoadSuccess();
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        e.printStackTrace();
                        onLoadDataListener.onLoadFailed(e.toString());
                    }
                });
    }


    /**
     * 获取新闻列表数据
     */

    protected void loadNewsListData() {
//        NewsApi.getInstance().getNewsList(new BaseSingleObserver<NewsListBean>(this) {
//            @Override
//            public void onSuccessful(NewsListBean newsListBean) {
//
//            }
//
//            @Override
//            public void onError(ExceptionHandle.ResponeThrowable e) {
//
//            }
//        });

    }


}
