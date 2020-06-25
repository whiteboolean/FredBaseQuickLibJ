package com.frame.fred_quick_lib.main.adapter;

import com.bumptech.glide.Glide;
import com.frame.fred_quick_lib.R;
import com.frame.fred_quick_lib.databinding.FragmentGuideBinding;
import com.frame.fred_quick_lib.main.fragments.BannerBaseAdapter;

import java.util.List;

public class ImageAdapter2 extends BannerBaseAdapter<FragmentGuideBinding, Integer> {

    public ImageAdapter2(List<Integer> datas) {
        super(datas);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_guide;
    }

    @Override
    public void onChildBindView(FragmentGuideBinding binding, BannerViewHolder holder, Integer data, int position, int size) {
        Glide.with(binding.getRoot().getContext())
                .load(data)
                .into(binding.guideImageview);
        if (onButtonViewChangeListener!=null){
            onButtonViewChangeListener.onChange(binding);
        }


    }

    public  interface OnButtonViewChangeListener{
        void onChange(FragmentGuideBinding binding);
    }

    public OnButtonViewChangeListener  onButtonViewChangeListener;

    public void setOnButtonViewChangeListener(OnButtonViewChangeListener onButtonViewChangeListener){
        this.onButtonViewChangeListener = onButtonViewChangeListener;
    }
}
