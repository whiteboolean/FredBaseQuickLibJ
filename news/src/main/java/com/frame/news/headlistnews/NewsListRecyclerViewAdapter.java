package com.frame.news.headlistnews;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.frame.common.views.picturetitleview.PictureTitleViewViewModel;
import com.frame.news.databinding.PictureTitleViewBinding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NewsListRecyclerViewAdapter extends BaseQuickAdapter<PictureTitleViewViewModel, BaseDataBindingHolder<PictureTitleViewBinding>> implements LoadMoreModule {


    public NewsListRecyclerViewAdapter(int layoutResId, @Nullable List<PictureTitleViewViewModel> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(@NotNull BaseDataBindingHolder<PictureTitleViewBinding> holder,
                           PictureTitleViewViewModel model) {
        // 获取 Binding
        PictureTitleViewBinding binding = holder.getDataBinding();
        //无法在布局中绑定控件，这是这个库的一个bug
        if (binding != null) {
            Glide.with(getContext()).load(model.avatarUrl).into(binding.itemImage);
            binding.itemFileName.setText(model.title);
            binding.executePendingBindings();
        }
    }


}
