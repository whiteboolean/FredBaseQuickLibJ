package com.frame.fred_quick_lib.main.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * Created by Fred Lei
 * 2020年06月24日19:18:12
 *
 * @param <V> View
 * @param <M> Model
 */
public abstract class BannerBaseAdapter<V extends ViewDataBinding, M> extends BannerAdapter<M, BannerBaseAdapter.BannerViewHolder> {

    protected V binding;

    public BannerBaseAdapter(List<M> datas) {
        super(datas);
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        V v = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutId(), parent, false);
        return new BannerViewHolder(v.getRoot());
    }


    @Override
    public void onBindView(BannerViewHolder holder, M data, int position, int size) {
        binding = DataBindingUtil.getBinding(holder.itemView);
        if (binding == null){
            throw new NullPointerException("找不到布局");
        }
        onChildBindView(binding, holder, data, position, size);
    }

    protected static class BannerViewHolder extends RecyclerView.ViewHolder {
        public BannerViewHolder(@NonNull View view) {
            super(view);
        }
    }

    public abstract @LayoutRes
    int getLayoutId();

    public abstract void onChildBindView(V binding, BannerViewHolder holder, M data, int position, int size);
}
