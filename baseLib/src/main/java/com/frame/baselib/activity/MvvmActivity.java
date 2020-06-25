package com.frame.baselib.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.frame.baselib.utils.ClassUtil;
import com.frame.baselib.view.IBaseView;
import com.frame.baselib.view.loadsir.EmptyCallback;
import com.frame.baselib.view.loadsir.ErrorCallback;
import com.frame.baselib.view.loadsir.PlaceholderCallback;
import com.frame.baselib.viewmodel.BaseViewModel;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 1.initViewModel()
 * 2.performDataBinding();
 *
 * @param <V>
 * @param <VM>
 */

public abstract class MvvmActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity implements IBaseView {

    protected VM vm;
    protected V dataBinding;
    protected LoadService<?> loadService;
    private CompositeDisposable mCompositeDisposable;

    public abstract @LayoutRes
    int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        initDataBinding();
    }

    protected void initViewModel() {
        Class<VM> viewModel = ClassUtil.getViewModel(this);
        if (viewModel != null) {
            vm = new ViewModelProvider(this).get(viewModel);
        }
    }


    public void setLoadSir() {
        loadService = LoadSir.getDefault().register(this, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onRetryButtonClick();
            }
        });
    }


    public void onRetryButtonClick() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onRefreshEmpty() {
        if (loadService != null) {
            loadService.showCallback(EmptyCallback.class);
        }
    }

    @Override
    public void onRefreshFailure(String message) {
        if (loadService != null) {
            loadService.showCallback(ErrorCallback.class);
        }
    }

    @Override
    public void showLoading() {
        if (loadService != null) {
            loadService.showCallback(PlaceholderCallback.class);
        }
    }

    @Override
    public void showContent() {
        if (loadService != null) {
            loadService.showSuccess();
        }
    }

    private void initDataBinding() {
        dataBinding = DataBindingUtil.setContentView(this, getLayoutId());
    }


    public void addSubscription(Disposable s) {
        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = new CompositeDisposable();
        }
        this.mCompositeDisposable.add(s);
    }
}
