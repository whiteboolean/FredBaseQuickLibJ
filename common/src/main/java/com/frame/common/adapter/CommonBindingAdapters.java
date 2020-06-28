package com.frame.common.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by Fred Lei On 2020年06月28日09:07:48
 * 自定义适配器注解
 */
public class CommonBindingAdapters {

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String url) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(view.getContext())
                    .load(url)
                    .transition(withCrossFade())
                    .into(view);
        }
    }

    @BindingAdapter("loadImageEasy")
    public static void loadImageEasy(ImageView view, int url) {
        Glide.with(view.getContext())
                .load(url)
                .into(view);
    }
}
