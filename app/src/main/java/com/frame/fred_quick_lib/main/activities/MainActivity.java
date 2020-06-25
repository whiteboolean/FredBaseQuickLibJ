package com.frame.fred_quick_lib.main.activities;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.WindowManager;

import com.frame.baselib.activity.MvvmActivity;
import com.frame.baselib.utils.preference.PreferencesUtil;
import com.frame.fred_quick_lib.R;
import com.frame.fred_quick_lib.databinding.ActivityMainBinding;
import com.frame.fred_quick_lib.home.MainFragment;
import com.frame.fred_quick_lib.main.adapter.MainFragmentPagerAdapter;
import com.frame.fred_quick_lib.main.fragments.AdFragment;
import com.frame.fred_quick_lib.main.fragments.GuideFragment;
import com.frame.fred_quick_lib.main.viewmodel.MainViewModel;


public class MainActivity extends MvvmActivity<ActivityMainBinding, MainViewModel> implements IMainActivity {

    private static final String IS_SHOW_GUIDE = "is_show_guide";

    private Fragment adFragment = new AdFragment(this);
    private Fragment guideFragment = new GuideFragment(this);
    private Fragment mHomeFragment = new MainFragment();
    private MainFragmentPagerAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), getLifecycle());
        if (PreferencesUtil.getInstance().getBoolean(IS_SHOW_GUIDE, true)) {
            adapter.addFragment(adFragment);
            PreferencesUtil.getInstance().setBoolean(IS_SHOW_GUIDE, false);
        } else {
            adapter.addFragment(guideFragment);
        }
        adapter.addFragment(mHomeFragment);
        dataBinding.setLifecycleOwner(this);
        dataBinding.viewPager2.setAdapter(adapter);
        dataBinding.viewPager2.setOffscreenPageLimit(3);
    }

    @Override
    public void removeMeAndGoNextFragment() {
        adapter.removeIndex0Fragment(this::showSystemUI);
    }

    private void showSystemUI() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar supportActionBar = getSupportActionBar();
        if (null != supportActionBar) {
            supportActionBar.show();
        }
    }

}