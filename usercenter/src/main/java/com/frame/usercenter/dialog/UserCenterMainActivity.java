package com.frame.usercenter.dialog;

import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.BarUtils;
import com.frame.baselib.utils.CommonUtils;
import com.frame.baselib.utils.StatusBarUtil;
import com.frame.usercenter.R;
import com.frame.usercenter.databinding.UsercenterActivityMainBinding;
import com.frame.usercenter.dialog.fragment.AllAnimatorDemo;
import com.frame.usercenter.dialog.fragment.CustomAnimatorDemo;
import com.frame.usercenter.dialog.fragment.CustomPopupDemo;
import com.frame.usercenter.dialog.fragment.ImageViewerDemo;
import com.frame.usercenter.dialog.fragment.PartShadowDemo;
import com.frame.usercenter.dialog.fragment.QuickStartDemo;
import com.google.android.material.tabs.TabLayout;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

public class UserCenterMainActivity extends AppCompatActivity {

    PageInfo[] pageInfos = new PageInfo[]{
            new PageInfo("快速开始", new QuickStartDemo()),
            new PageInfo("局部阴影", new PartShadowDemo()),
            new PageInfo("图片浏览", new ImageViewerDemo()),
            new PageInfo("尝试不同动画", new AllAnimatorDemo()),
            new PageInfo("自定义弹窗", new CustomPopupDemo()),
            new PageInfo("自定义动画", new CustomAnimatorDemo())
    };

    TabLayout tabLayout;
    public ViewPager viewPager;

    LoadingPopupView loadingPopupView;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usercenter_activity_main);

//        UsercenterActivityMainBinding inflate = UsercenterActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(inflate.getRoot());
//        BarUtils.setStatusBarLightMode(this, true);
//        BarUtils.setNavBarColor(this, Color.RED);

        initStatusBar();
        ActionBar supportActionBar = getSupportActionBar();
        if (null != supportActionBar) {
            supportActionBar.show();
            //去除默认Title显示
            supportActionBar.setDisplayShowTitleEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle(supportActionBar.getTitle() + "自定义Dialog");
        }

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        XPopup.setPrimaryColor(getResources().getColor(R.color.colorPrimary));
//        XPopup.setAnimationDuration(1000);
//        XPopup.setPrimaryColor(Color.RED);
//        ScreenUtils.setLandscape(this);
        loadingPopupView = new XPopup.Builder(this).asLoading("嘻嘻嘻嘻嘻");
        loadingPopupView.show();
        loadingPopupView.delayDismiss(1000);
    }


    protected void initStatusBar() {
        // 设置透明状态栏，兼容4.4
        StatusBarUtil.setColor(this, CommonUtils.getColor(R.color.colorPrimary), 0);
    }

    class MainAdapter extends FragmentPagerAdapter {


        private SparseArray<Fragment> mRegisteredFragments = new SparseArray<>();

        public MainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return pageInfos.length;
        }

        @Override
        public Fragment getItem(int i) {
            return pageInfos[i].fragment;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return pageInfos[position].title;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.removeAllViews();
        viewPager = null;
        pageInfos = null;
    }

}
