package com.frame.webview.main.webview.config;

import com.frame.baselib.activity.MvvmActivity;
import com.frame.webview.R;
import com.frame.webview.databinding.ActivityBaseWebViewBinding;

public class BaseWebViewActivity extends MvvmActivity<ActivityBaseWebViewBinding, BaseWebViewModel> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_base_web_view;
    }

    @Override
    protected void initViews() {

    }


}
