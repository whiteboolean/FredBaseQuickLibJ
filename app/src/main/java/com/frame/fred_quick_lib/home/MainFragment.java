package com.frame.fred_quick_lib.home;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.frame.baselib.fragment.MvvmFragment;
import com.frame.fred_quick_lib.R;
import com.frame.fred_quick_lib.databinding.FragmentHomeBinding;
import com.frame.fred_quick_lib.main.fragments.ItemFragment;
import com.frame.fred_quick_lib.main.viewmodel.MainViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;

import q.rorbin.badgeview.QBadgeView;

public class MainFragment extends MvvmFragment<FragmentHomeBinding, MainViewModel> {

    private static final String TAG = "MainFragment";
    private Fragment mHomeFragment;
    private Fragment userCenterFragment;
    private Fragment newsFragment;
    private Fragment webViewFragment;
    Fragment fromFragment;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews() {
        showBadgeView(3, 5);
    }

    @Override
    protected void initParameters() {
        dataBinding.setData(viewModel);
        //同步请求 new模块
        CCResult call = CC.obtainBuilder("news")
                .setActionName("NewsFragment")
                .build()
                .call();
        //同步请求，usercenter模块
        CCResult userCenter = CC.obtainBuilder("usercenter")
                .setActionName("UserCenterFragment")
                .build()
                .call();
        //同步请求，webview模块
        CCResult webView = CC.obtainBuilder("webview")
                .setActionName("BaseWebViewFragment")
                .build()
                .call();
        mHomeFragment = call.getDataItem("fragment");
        userCenterFragment = userCenter.getDataItem("fragment");
        newsFragment = ItemFragment.newInstance(5);
        webViewFragment = webView.getDataItem("fragment");
        fromFragment = mHomeFragment;
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, mHomeFragment);
        transaction.commit();
        showBadgeView(3, 5);
        dataBinding.bottomView.setOnNavigationItemSelectedListener(this::onNavigateItemSelected);
    }


    private boolean onNavigateItemSelected(MenuItem item) {
        Fragment fragCategory;
        int itemId = item.getItemId();
        if (itemId == R.id.menuOne) {
            fragCategory = mHomeFragment;
            viewModel.tvTitle.postValue("首页");
        } else if (itemId == R.id.menuTwo) {
            fragCategory = newsFragment;
            viewModel.tvTitle.postValue("待定");
        } else if (itemId == R.id.menuThree) {
            fragCategory = webViewFragment;
            viewModel.tvTitle.postValue("webView");
        } else {
            fragCategory = userCenterFragment;
            viewModel.tvTitle.postValue("对话框");
        }


        switchFragment(fromFragment, fragCategory);
        fromFragment = fragCategory;
        return true;
    }

    @Override
    protected void onRetryBtnClick(View view) {

    }

    @Override
    protected String getFragmentTag() {
        return TAG;
    }




    private void switchFragment(Fragment from, Fragment to) {
        if (from != to) {
            FragmentManager manger = getChildFragmentManager();
            FragmentTransaction transaction = manger.beginTransaction();
            if (!to.isAdded()) {
                if (from != null) {
                    transaction.hide(from);
                }
                transaction.add(R.id.fragment, to, to.getClass().getName()).commit();
            } else {
                if (from != null) {
                    transaction.hide(from);
                }
                transaction.show(to).commit();

            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                getActivity().finish();
                break;
            }
        }
        return true;
    }

    /**
     * BottomNavigationView显示角标
     *
     * @param viewIndex  tab索引
     * @param showNumber 显示的数字，小于等于0是将不显示
     */
    private void showBadgeView(int viewIndex, int showNumber) {
        // 具体child的查找和view的嵌套结构请在源码中查看
        // 从bottomNavigationView中获得BottomNavigationMenuView
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) dataBinding.bottomView.getChildAt(0);
        // 从BottomNavigationMenuView中获得childview, BottomNavigationItemView
        if (viewIndex < menuView.getChildCount()) {
            // 获得viewIndex对应子tab
            View view = menuView.getChildAt(viewIndex);
            // 从子tab中获得其中显示图片的ImageView
            View icon = view.findViewById(com.google.android.material.R.id.icon);
            // 获得图标的宽度
            int iconWidth = icon.getWidth();
            // 获得tab的宽度/2
            int tabWidth = view.getWidth() / 2;
            // 计算badge要距离右边的距离
            int spaceWidth = tabWidth - iconWidth;

            // 显示badegeview
            new QBadgeView(getContext()).bindTarget(view).setGravityOffset(spaceWidth + 50, 13, false).setBadgeNumber(showNumber);
        }
    }
}
