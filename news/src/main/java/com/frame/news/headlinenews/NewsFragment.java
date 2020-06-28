package com.frame.news.headlinenews;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.frame.baselib.fragment.MvvmFragment;
import com.frame.baselib.utils.rx.RxBus;
import com.frame.baselib.utils.rx.RxBusBaseMessage;
import com.frame.baselib.utils.rx.RxCodeConstants;
import com.frame.basenetwork.bean.Channel;
import com.frame.news.R;
import com.frame.news.databinding.FragmentNewBlankBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;


public class NewsFragment extends MvvmFragment<FragmentNewBlankBinding, NewsViewModel> {

    private static final String TAG = "NewsFragment";
    private NewListAdapter newListAdapter;
    private NewListAdapter1 newListAdapter1;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_new_blank;
    }

    @Override
    protected void initData() {
        //获取分页数据
        dataBinding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        loadChannelData();
    }


    @Override
    protected void initParameters() {
//        newListAdapter = new NewListAdapter(requireActivity());
        newListAdapter1 = new NewListAdapter1(getChildFragmentManager());
        dataBinding.viewPager.setAdapter(newListAdapter1);
        dataBinding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(dataBinding.tabLayout));
//        绑定tab点击事件
        dataBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                dataBinding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        dataBinding.viewPager2.setAdapter(newListAdapter);
//        new TabLayoutMediator(dataBinding.tabLayout,
//                dataBinding.viewPager2, (tab, position) -> {
//            dataBinding.viewPager2.setCurrentItem(position);
//        }).attach();
//        setLoadSir(dataBinding.viewPager2);
        setLoadSir(dataBinding.viewPager);
    }


    @Override
    protected void onRetryBtnClick(View view) {
        loadChannelData();
    }

    @Override
    protected String getFragmentTag() {
        return TAG;
    }


    private void loadChannelData() {
        showLoading();
        viewModel.loadChannelData(new OnLoadDataListener() {
            @Override
            public void onLoadSuccess() {
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewModel.channelList.observe(getViewLifecycleOwner(), NewsFragment.this::onGetData);
                    }
                });
            }

            @Override
            public void onLoadFailed(String message) {
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onRefreshFailure(message);
                    }
                });
            }
        });
    }

    private void onGetData(List<Channel> channels) {
        showContent();
        newListAdapter1.setChannels(channels);
//        newListAdapter.setChannels(channels);
        dataBinding.tabLayout.removeAllTabs();
        for (Channel channel : channels) {
            dataBinding.tabLayout.addTab(dataBinding.tabLayout.newTab().setText(channel.channelName));
        }
    }
}

