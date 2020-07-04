package com.frame.fred_quick_lib.main.fragments;

import android.graphics.Color;
import android.view.View;
import android.view.WindowManager;

import androidx.fragment.app.FragmentActivity;

import com.frame.baselib.fragment.MvvmFragment;
import com.frame.fred_quick_lib.R;
import com.frame.fred_quick_lib.databinding.FragmentGuideBinding;
import com.frame.fred_quick_lib.databinding.FragmentGuideHomeBinding;
import com.frame.fred_quick_lib.main.activities.IMainActivity;
import com.frame.fred_quick_lib.main.adapter.ImageAdapter2;
import com.frame.fred_quick_lib.main.viewmodel.GuideViewModel;

import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnPageChangeListener;


public class GuideFragment extends MvvmFragment<FragmentGuideHomeBinding, GuideViewModel> {

    private static final String TAG = "GuideFragment";
    private IMainActivity iMainActivity;

    public GuideFragment() {
    }

    public GuideFragment(IMainActivity iMainActivity) {
        this.iMainActivity = iMainActivity;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_guide_home;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initParameters() {
        dataBinding.setLifecycleOwner(this);
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        initGuideView(activity);
    }


    @Override
    protected String getFragmentTag() {
        return TAG;
    }

    private void initGuideView(FragmentActivity activity) {
        ImageAdapter2 imageAdapter2 = new ImageAdapter2(viewModel.getData().getValue());
        viewModel.listMutableLiveData.observe(getViewLifecycleOwner(), integers -> {
            imageAdapter2.setDatas(integers);
            imageAdapter2.notifyDataSetChanged();
        });

        Banner banner = dataBinding.banner;
        banner.setAdapter(imageAdapter2)
                .addBannerLifecycleObserver(getViewLifecycleOwner())
                .setIndicator(new CircleIndicator(activity))
                .setIndicatorSelectedColor(Color.RED)
                .setIndicatorNormalColor(Color.BLACK)
                .start();

        imageAdapter2.setOnButtonViewChangeListener(this::onButtonViewChange);
        banner.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int size = viewModel.getData().getValue().size();
                viewModel.isShowButton.postValue(position == size - 1);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void onButtonViewChange(FragmentGuideBinding binding) {
        viewModel.isShowButton.observe(getViewLifecycleOwner(), binding::setData);
        binding.btnGuideEnter.setOnClickListener(this::btnOnClick);
    }


    private void btnOnClick(View view) {
        if (iMainActivity != null) {
            iMainActivity.removeMeAndGoNextFragment();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        iMainActivity = null;
    }
}
