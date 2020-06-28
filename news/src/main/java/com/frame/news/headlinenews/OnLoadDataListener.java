package com.frame.news.headlinenews;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;

public interface OnLoadDataListener {
    default void onLoadSuccess(){
        LogUtils.e("成功");
    };

    default void onLoadFailed(String message) {
        Log.e("message",message);
    }




}
