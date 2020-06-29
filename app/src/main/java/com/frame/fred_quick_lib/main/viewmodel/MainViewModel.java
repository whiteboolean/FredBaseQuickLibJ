package com.frame.fred_quick_lib.main.viewmodel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.frame.baselib.viewmodel.BaseViewModel;

public class MainViewModel extends BaseViewModel {

    public MutableLiveData<String> tvTitle = new MutableLiveData<>("首页");

    public MainViewModel(Application application) {
        super(application);
    }


}

