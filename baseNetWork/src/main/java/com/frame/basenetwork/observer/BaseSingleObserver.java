package com.frame.basenetwork.observer;

import android.util.Log;

import com.frame.baselib.model.SuperBaseModel;
import com.frame.baselib.viewmodel.BaseViewModel;
import com.frame.basenetwork.errorhandler.ExceptionHandle;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;


public abstract class BaseSingleObserver<T> implements SingleObserver<T> {
    BaseViewModel baseModel;
    public BaseSingleObserver(BaseViewModel baseModel) {
        this.baseModel = baseModel;
    }
    @Override
    public void onError(Throwable e) {
        Log.e("lvr", e.getMessage());
        // todo error somthing

        if(e instanceof ExceptionHandle.ResponeThrowable){
            onError((ExceptionHandle.ResponeThrowable)e);
        } else {
            onError(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        if(baseModel != null){
            baseModel.addDisposable(d);
        }
    }

    @Override
    public void onSuccess(T t) {
        onSuccessful(t);
    }

    public abstract void onSuccessful(T t);

    public abstract void onError(ExceptionHandle.ResponeThrowable e);

}
