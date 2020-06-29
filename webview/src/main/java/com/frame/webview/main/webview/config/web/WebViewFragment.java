package com.frame.webview.main.webview.config.web;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.frame.baselib.fragment.MvvmFragment;
import com.frame.webview.R;

import com.frame.webview.main.webview.config.BaseWebViewActivity;
import com.frame.webview.main.webview.config.BaseWebViewModel;

public class WebViewFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.activity_base_web_view2, null);

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),BaseWebViewActivity.class));
            }
        });
        return view;
    }
}
