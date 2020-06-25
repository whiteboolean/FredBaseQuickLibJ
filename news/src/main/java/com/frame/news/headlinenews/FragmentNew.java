

package com.frame.news.headlinenews;


import com.frame.baselib.fragment.MvvmFragment;
import com.frame.news.databinding.FragmentBlank2Binding;

public class FragmentNew  extends MvvmFragment<FragmentBlank2Binding,NewsViewModel>{


    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    protected void initParameters() {

    }

    @Override
    protected void onRetryBtnClick() {

    }

    @Override
    protected String getFragmentTag() {
        return null;
    }
}

