package com.frame.baselib.activity;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.frame.baselib.viewmodel.MvvmBaseViewModel;

public abstract class MvvmActivity<V extends ViewDataBinding, VM extends MvvmBaseViewModel> extends AppCompatActivity {

    protected VM viewModel;
    protected V viewDataBinding;

    public abstract @LayoutRes
    int getLayoutId();

    protected abstract VM getViewModel();
    protected abstract int getBindingVariable();

    private void performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        if (viewModel == null) {
            viewModel = getViewModel();
        }
    }
}
