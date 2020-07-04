package com.frame.news.headlistnews;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.frame.baselib.fragment.MvvmFragment;
import com.frame.common.views.loadmore.CustomLoadMoreView;
import com.frame.common.views.picturetitleview.PictureTitleViewViewModel;
import com.frame.news.R;
import com.frame.news.databinding.FragmentNewsListBinding;
import com.frame.news.headlinenews.OnLoadDataListener;

import java.lang.ref.WeakReference;
import java.util.List;


public class NewsListFragment extends MvvmFragment<FragmentNewsListBinding, NewsListViewModel> {

    private static final String TAG = "NewsListFragment";
    private NewsListRecyclerViewAdapter mAdapter;
    private String mChannelId = "";
    private String mChannelName = "";
    protected final static String BUNDLE_KEY_PARAM_CHANNEL_ID = "bundle_key_param_channel_id";
    protected final static String BUNDLE_KEY_PARAM_CHANNEL_NAME = "bundle_key_param_channel_name";
    private OnLoadDataStateListener onLoadDataListener;

    public static NewsListFragment newInstance(String channelId, String channelName) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_ID, channelId);
        bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_NAME, channelName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news_list;
    }

    @Override
    protected void initParameters() {
        if (getArguments() != null) {
            mChannelId = getArguments().getString(BUNDLE_KEY_PARAM_CHANNEL_ID);
            mChannelName = getArguments().getString(BUNDLE_KEY_PARAM_CHANNEL_NAME);
        }
    }


    @Override
    protected void initViews() {
        List<PictureTitleViewViewModel> value = viewModel.dataList.getValue();
        mAdapter = new NewsListRecyclerViewAdapter(R.layout.picture_title_view, value);
        onLoadDataListener = new OnLoadDataStateListener(this, dataBinding);
        viewModel.load(mChannelId, mChannelName, onLoadDataListener);
        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        dataBinding.recyclerView.setAdapter(mAdapter);
        viewModel.dataList.observe(getViewLifecycleOwner(), pictureTitleViewViewModels -> {
            mAdapter.setList(pictureTitleViewViewModels);
            showContent();
        });
        dataBinding.swipeRefreshLayout.setOnRefreshListener(() -> viewModel.load(mChannelId, mChannelName, onLoadDataListener));
        setLoadSir(dataBinding.swipeRefreshLayout);
        showLoading();
        initLoadMore(onLoadDataListener);
        initSwitch();
    }

    private void initLoadMore(OnLoadDataListener onLoadDataListener) {
        mAdapter.getLoadMoreModule().setLoadMoreView(new CustomLoadMoreView());
        mAdapter.getLoadMoreModule().setOnLoadMoreListener(() -> viewModel.loadNextPage(mChannelId, mChannelName, onLoadDataListener));
        mAdapter.getLoadMoreModule().setAutoLoadMore(true);
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        mAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
    }

    @Override
    protected void onRetryBtnClick(View view) {
        showLoading();
        viewModel.load(mChannelId, mChannelName, onLoadDataListener);
    }

    private void initSwitch() {
        dataBinding.autoLoadMoreSwitch.setChecked(mAdapter.getLoadMoreModule().isAutoLoadMore());
        dataBinding.autoLoadMoreSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mAdapter.getLoadMoreModule().setAutoLoadMore(isChecked);
                if (isChecked) {
                    dataBinding.autoLoadMoreSwitch.setText("自动加载（开）");
                } else {
                    dataBinding.autoLoadMoreSwitch.setText("自动加载（关）");
                }
            }
        });
    }


    public static class OnLoadDataStateListener implements OnLoadDataListener {
        //使用弱引用避免内存泄漏问题
        private WeakReference<FragmentNewsListBinding> dataBinding;
        private WeakReference<NewsListFragment> newsListFragment;

        public OnLoadDataStateListener(NewsListFragment newsListFragment, FragmentNewsListBinding dataBinding) {
            this.dataBinding = new WeakReference<>(dataBinding);
            this.newsListFragment = new WeakReference<>(newsListFragment);
        }

        @Override
        public void onLoadSuccess() {
            if (dataBinding.get() != null) {
                dataBinding.get().llContent.postDelayed(() -> dataBinding.get().swipeRefreshLayout.setRefreshing(false), 500);
            }
        }

        @Override
        public void onLoadFailed(String message) {
            if (dataBinding.get() != null) {
                dataBinding.get().llContent.postDelayed(() -> dataBinding.get().swipeRefreshLayout.setRefreshing(false), 500);
            }
            if (newsListFragment.get() != null) {
                newsListFragment.get().onRefreshFailure(message);
            }
        }
    }

}
