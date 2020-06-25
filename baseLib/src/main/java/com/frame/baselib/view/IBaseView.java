package com.frame.baselib.view;

/**
 * 页面回调抽象接口
 */
public interface IBaseView {
    void showContent();

    void showLoading();

    void onRefreshEmpty();

    void onRefreshFailure(String message);
}
