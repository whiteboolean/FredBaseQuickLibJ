package com.frame.fred_quick_lib.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.frame.baselib.fragment.MvvmFragment;
import com.frame.fred_quick_lib.R;
import com.frame.fred_quick_lib.databinding.FragmentHomeBinding;
import com.frame.fred_quick_lib.main.viewmodel.MainViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import q.rorbin.badgeview.QBadgeView;

public class MainFragment extends MvvmFragment<FragmentHomeBinding, MainViewModel> {

    private static final String TAG = "MainFragment";
    private Fragment mHomeFragment ;
    private Fragment userCenterFragment;
    private Fragment settingFragment ;
    private Fragment settingFragment1 ;
    Fragment fromFragment;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showBadgeView(3, 5);
    }

    @Override
    protected void initParameters() {

        //同步请求 new模块
        CCResult call = CC.obtainBuilder("news")
                .setActionName("BlankFragment")
                .build()
                .call();
        mHomeFragment = call.getDataItem("fragment");

        //同步请求
        userCenterFragment = mHomeFragment;
        settingFragment = mHomeFragment;
        settingFragment1 = mHomeFragment;

        dataBinding.bottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragCategory = null;
                // init corresponding fragment
                switch (item.getItemId()) {
                    case R.id.menuOne:
                        fragCategory = mHomeFragment;
                        break;
                    case R.id.menuTwo:
                        fragCategory = mHomeFragment;
                        break;
                    case R.id.menuThree:
                        fragCategory = mHomeFragment;
                        break;
                    case R.id.menuFour:
                        fragCategory = mHomeFragment;
                        break;
                }
                //Set bottom menu selected item text in toolbar
                ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle(item.getTitle());
                }
                switchFragment(fromFragment, fragCategory);
                fromFragment = fragCategory;
                return true;
            }
        });
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, mHomeFragment, mHomeFragment.getClass().getSimpleName());
        transaction.commit();
        showBadgeView(3, 5);



    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected String getFragmentTag() {
        return TAG;
    }



    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }




    private void switchFragment(Fragment from, Fragment to) {
        if (from != to) {
            FragmentManager manger = getChildFragmentManager();
            FragmentTransaction transaction = manger.beginTransaction();
            if (!to.isAdded()) {
                if (from != null) {
                    transaction.hide(from);
                }
                if (to != null) {
                    transaction.add(R.id.container, to, to.getClass().getName()).commit();
                }
            } else {
                if (from != null) {
                    transaction.hide(from);
                }
                if (to != null) {
                    transaction.show(to).commit();
                }

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
            // case blocks for other MenuItems (if any)
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
