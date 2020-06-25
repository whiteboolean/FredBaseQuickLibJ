package com.frame.baselib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;


import com.frame.baselib.application.BaseApplication;

import me.drakeet.support.toast.ToastCompat;

/**
 * Created by Fred Lei on
 * 2020年06月23日19:33:57
 * 单例Toast，
 * 1.兼容索尼部分手机不弹提示的问题
 * 2.VIVO 7.1.1部分手机崩溃问题
 * 3.An Android library to hook and fix Toast BadTokenException
 * 4.https://github.com/PureWriter/ToastCompat
 */

public class ToastUtil {

    private static ToastCompat mToast;

    private static Context getAppContext() {
        return BaseApplication.sAppContext;
    }

    @SuppressLint("ShowToast")
    public static void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = ToastCompat.makeText(getAppContext(), text, Toast.LENGTH_SHORT);
            } else {
                mToast.cancel();
                mToast = ToastCompat.makeText(getAppContext(), text, Toast.LENGTH_SHORT);
            }
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setText(text);
            mToast.show();
        }
    }

    @SuppressLint("ShowToast")
    public static void showToastLong(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = ToastCompat.makeText(getAppContext(), text, Toast.LENGTH_LONG);
            } else {
                mToast.cancel();
                mToast = ToastCompat.makeText(getAppContext(), text, Toast.LENGTH_LONG);
            }
            mToast.setDuration(Toast.LENGTH_LONG);
            mToast.setText(text);
            mToast.show();
        }
    }

}
