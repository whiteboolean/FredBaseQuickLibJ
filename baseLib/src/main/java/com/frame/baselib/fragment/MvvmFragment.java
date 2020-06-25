package com.frame.baselib.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.frame.baselib.R;
import com.frame.baselib.utils.ClassUtil;
import com.frame.baselib.utils.DebugUtil;
import com.frame.baselib.utils.ToastUtil;
import com.frame.baselib.view.loadsir.EmptyCallback;
import com.frame.baselib.view.loadsir.ErrorCallback;
import com.frame.baselib.view.loadsir.LoadingCallback;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class MvvmFragment<V extends ViewDataBinding, VM extends AndroidViewModel> extends Fragment implements IBasePagingView {
    private static final String TAG = "MvvmFragment";
    protected VM viewModel;
    protected V dataBinding;
    private LoadService mLoadService;
    private CompositeDisposable mCompositeDisposable;


    public abstract
    @LayoutRes
    int getLayoutId();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return dataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Class<VM> vm = ClassUtil.getViewModel(this);
        if (vm != null) {
            viewModel = new ViewModelProvider(requireActivity()).get(vm);
        }
        initParameters();
    }

    /***
     *   初始化参数
     */
    protected abstract void initParameters();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DebugUtil.debug(TAG, "onDestroyView: ");
    }

    @Override
    public void onRefreshEmpty() {
        if (mLoadService != null) {
            mLoadService.showCallback(EmptyCallback.class);
        }
    }

    @Override
    public void onRefreshFailure(String message) {
        if (mLoadService != null) {
            if (!isShowedContent) {
                mLoadService.showCallback(ErrorCallback.class);
            } else {
                ToastUtil.showToast(message);
            }
        }
    }

    @Override
    public void showLoading() {
        if (mLoadService != null) {
            mLoadService.showCallback(LoadingCallback.class);
        }
    }

    private boolean isShowedContent = false;

    @Override
    public void showContent() {
        if (mLoadService != null) {
            isShowedContent = true;
            mLoadService.showSuccess();
        }
    }

    protected abstract void onRetryBtnClick();

    @Override
    public void onLoadMoreFailure(String message) {
        ToastUtil.showToast(message);
    }

    @Override
    public void onLoadMoreEmpty() {
        ToastUtil.showToast(getString(R.string.no_more_data));
    }

    public void setLoadSir(View view) {
        // You can change the callback on sub thread directly.
        mLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onRetryBtnClick();
            }
        });
    }

    protected abstract String getFragmentTag();

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        DebugUtil.debug(getFragmentTag(), this + ": " + "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        DebugUtil.debug(getFragmentTag(), this + ": " + "onDetach");
    }

    @Override
    public void onStop() {
        super.onStop();
        DebugUtil.debug(getFragmentTag(), this + ": " + "onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        DebugUtil.debug(getFragmentTag(), this + ": " + "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        DebugUtil.debug(getFragmentTag(), this + ": " + "onResume");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DebugUtil.debug(getFragmentTag(), this + ": " + "onDestroy");
    }


    public void addSubscription(Disposable s) {
        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = new CompositeDisposable();
        }
        this.mCompositeDisposable.add(s);
    }
}
