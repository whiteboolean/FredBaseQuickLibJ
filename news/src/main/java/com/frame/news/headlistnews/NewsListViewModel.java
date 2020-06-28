package com.frame.news.headlistnews;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.frame.baselib.fragment.MvvmFragment;
import com.frame.baselib.viewmodel.BaseViewModel;
import com.frame.basenetwork.bean.NewsListBean;
import com.frame.basenetwork.errorhandler.ExceptionHandle;
import com.frame.basenetwork.observer.BaseSingleObserver;
import com.frame.common.views.picturetitleview.PictureTitleViewViewModel;
import com.frame.news.api.NewsApi;
import com.frame.news.headlinenews.OnLoadDataListener;

import java.util.ArrayList;
import java.util.List;


public class NewsListViewModel extends BaseViewModel {


    private int pageNumber;
    private boolean isRefresh;

    public MutableLiveData<List<PictureTitleViewViewModel>> dataList = new MutableLiveData<>();

    public NewsListViewModel(@NonNull Application application) {
        super(application);
    }

    public void load(String channelId, String channelName, OnLoadDataListener onLoadDataListener) {
        isRefresh = true;
        getNewsData(channelId, channelName, onLoadDataListener);
    }

    public void loadNextPage(String channelId, String channelName, OnLoadDataListener onLoadDataListener) {
        isRefresh = false;
        getNewsData(channelId, channelName, onLoadDataListener);
    }


    public void getNewsData(String channelId, String channelName, OnLoadDataListener onLoadDataListener) {
        NewsApi.getInstance().getNewsList(new BaseSingleObserver<NewsListBean>(this) {
            @Override
            public void onSuccessful(NewsListBean newsChannelsBean) {
                pageNumber = isRefresh ? 2 : pageNumber + 1;
                List<PictureTitleViewViewModel> list = new ArrayList<>();

                for (NewsListBean.Contentlist source : newsChannelsBean.showapiResBody.pagebean.contentlist) {
                    PictureTitleViewViewModel viewModel = new PictureTitleViewViewModel();
                    viewModel.avatarUrl = "http://img0.imgtn.bdimg.com/it/u=4250058738,780121024&fm=11&gp=0.jpg";
                    viewModel.link = source.link;
                    viewModel.title = source.title;
                    list.add(viewModel);
                }
                if (isRefresh) {
                    if (dataList.getValue() != null) {
                        dataList.getValue().clear();
                    }
                    dataList.postValue(list);
                } else {
                    List<PictureTitleViewViewModel> value = dataList.getValue();
                    if (value != null) {
                        value.addAll(list);
                        dataList.postValue(value);
                    } else {
                        dataList.postValue(list);
                    }
                }
                onLoadDataListener.onLoadSuccess();
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                onLoadDataListener.onLoadFailed(e.toString());
            }
        }, channelId, channelName, String.valueOf(isRefresh ? 1 : pageNumber));
        Log.e("请求：", "mChannelId:" + channelId + ",mChannelName:" + channelName + (isRefresh ? 1 : pageNumber) + ":Number");

    }


}
