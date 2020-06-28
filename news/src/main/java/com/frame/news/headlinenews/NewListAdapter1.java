package com.frame.news.headlinenews;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.frame.basenetwork.bean.Channel;
import com.frame.news.headlistnews.NewsListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NewListAdapter1 extends FragmentStatePagerAdapter {


    private List<Channel> mChannels;

    public NewListAdapter1(FragmentManager fm) {
        super(fm);
    }

    public void setChannels(List<Channel> channels) {
        this.mChannels = channels;
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public Fragment getItem(int pos) {
        return NewsListFragment.newInstance(mChannels.get(pos).channelId, mChannels.get(pos).channelName);
    }

    @Override
    public int getCount() {
        return mChannels==null?0:mChannels.size();
    }

    @Override
    public void restoreState(Parcelable parcelable, ClassLoader classLoader) {
    }
}
