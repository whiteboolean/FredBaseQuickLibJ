package com.frame.fred_quick_lib.main.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Viewpager2 + Fragment 要用 FragmentStateAdapter
 *
 * viewpager2 移除 ：
 * https://stackoverflow.com/questions/57938930/remove-fragment-in-viewpager2-use-fragmentstateadapter-but-still-display
 */

public class MainFragmentPagerAdapter extends FragmentStateAdapter {
    private List<Fragment> fragments;

    private List<Long> ids = new ArrayList<>();

    public MainFragmentPagerAdapter( FragmentManager fm, Lifecycle lifecycle) {
        super(fm,lifecycle);
        fragments = new ArrayList<>();
        for (Fragment fragment : fragments) {
            ids.add((long) fragment.hashCode());
        }
    }

    public void removeIndex0Fragment(OnSystemUIShowListener onSystemUIShowListener) {
        fragments.remove(0);
        notifyItemRangeChanged(0, fragments.size());
        notifyDataSetChanged();
        onSystemUIShowListener.showSystemUi();
    }


    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }


    @Override
    public boolean containsItem(long itemId) {
        return ids.contains(itemId);
    }

    @Override
    public long getItemId(int position) {
        return fragments.get(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

}