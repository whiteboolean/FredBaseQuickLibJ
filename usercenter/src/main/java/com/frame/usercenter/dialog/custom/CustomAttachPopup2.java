package com.frame.usercenter.dialog.custom;

import android.content.Context;

import androidx.annotation.NonNull;

import com.frame.usercenter.R;
import com.lxj.xpopup.core.AttachPopupView;


/**
 * Description: 自定义背景的Attach弹窗
 * Create by lxj, at 2019/3/13
 */
public class CustomAttachPopup2 extends AttachPopupView {
    public CustomAttachPopup2(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_attach_popup2;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

    }
}
