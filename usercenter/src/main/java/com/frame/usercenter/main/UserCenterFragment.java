package com.frame.usercenter.main;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.frame.baselib.fragment.MvvmFragment;
import com.frame.usercenter.R;
import com.frame.usercenter.databinding.FragmentUserCenterBinding;
import com.frame.usercenter.dialog.UserCenterMainActivity;

public class UserCenterFragment extends MvvmFragment<FragmentUserCenterBinding, UserCenterViewModel> {

    private static final String TAG = "UserCenterFragment";

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_center;
    }

    @Override
    protected void initViews() {
        dataBinding.textView.setOnClickListener(this::textOnClick);
    }

    private void textOnClick(View view) {
        Activity activity = getActivity();
        startActivity(new Intent(activity, UserCenterMainActivity.class));
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
