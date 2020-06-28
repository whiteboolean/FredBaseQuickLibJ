package com.frame.usercenter.main;

import android.view.View;

import com.frame.baselib.fragment.MvvmFragment;
import com.frame.usercenter.R;
import com.frame.usercenter.databinding.FragmentUserCenterBinding;

public class UserCenterFragment extends MvvmFragment<FragmentUserCenterBinding,UserCenterViewModel> {

    private static final String TAG = "UserCenterFragment";
    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_center;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initParameters() {

    }

    @Override
    protected void onRetryBtnClick(View view) {

    }


    @Override
    protected String getFragmentTag() {
        return TAG;
    }
}
