package com.frame.news.headlinenews;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.frame.basenetwork.bean.Channel;
import com.frame.news.headlistnews.NewsListFragment;

import java.util.List;

public class NewListAdapter extends FragmentStateAdapter {

    private List<Channel> channels;

    public NewListAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return NewsListFragment.newInstance(channels.get(position).channelId, channels.get(position).channelName);
    }

    @Override
    public int getItemCount() {
        return channels == null ? 0 : channels.size();
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }


}
