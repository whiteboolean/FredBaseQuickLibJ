package com.frame.baselib.fragment;


import com.frame.baselib.view.IBaseView;

public interface IBasePagingView extends IBaseView {

    void onLoadMoreFailure(String message);

    void onLoadMoreEmpty();
}
