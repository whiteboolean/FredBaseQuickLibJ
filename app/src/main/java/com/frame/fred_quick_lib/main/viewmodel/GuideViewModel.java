package com.frame.fred_quick_lib.main.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.frame.fred_quick_lib.R;

import java.util.ArrayList;
import java.util.List;

public class GuideViewModel extends AndroidViewModel {

    public MutableLiveData<List<Integer>> listMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> isShowButton = new MutableLiveData<>(false);


    public GuideViewModel(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<List<Integer>> getData() {
        List<Integer> urls = new ArrayList<>();
        urls.add(R.drawable.guide_test1);
        urls.add(R.drawable.guide_test2);
        urls.add(R.drawable.guide_test3);
        if (listMutableLiveData.getValue()==null){
            listMutableLiveData.setValue(urls);
        }

        return listMutableLiveData;

    }


}
