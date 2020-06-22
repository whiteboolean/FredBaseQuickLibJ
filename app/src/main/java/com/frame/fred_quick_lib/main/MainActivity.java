package com.frame.fred_quick_lib.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.frame.fred_quick_lib.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends AppCompatActivity {

    public ActivityMainBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());
        showBadgeView(3,5);
    }


    /**
     * BottomNavigationView显示角标
     *
     */
    private void showBadgeView(int viewIndex,int showNumber){
        BottomNavigationMenuView menuView  = (BottomNavigationMenuView) viewBinding.bottomNavigationView.getChildAt(0);
        if (viewIndex<menuView.getChildCount()){
            View view = menuView.getChildAt(viewIndex);
            View icon = menuView.findViewById(com.google.android.material.R.id.icon);
            int iconWidth = icon.getWidth();
            int tabWidth = view.getWidth();
            int spaceWidth = tabWidth - iconWidth;
            new QBadgeView(this).bindTarget(view).setGravityOffset(spaceWidth+50,13,false).setBadgeNumber(showNumber);
        }
    }

}