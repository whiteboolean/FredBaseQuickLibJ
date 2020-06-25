package com.frame.webview.main;

import com.frame.baselib.fragment.MvvmFragment;
import com.frame.webview.R;
import com.frame.webview.databinding.FragmentWebViewBinding;

public class WebViewFragment extends MvvmFragment<FragmentWebViewBinding,WebViewModel> {
    private static final String TAG = "WebViewFragment";
    @Override
    public int getLayoutId() {
        return R.layout.fragment_web_view;
    }

    @Override
    protected void initParameters() {

    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected String getFragmentTag() {
        return TAG;
    }
}
