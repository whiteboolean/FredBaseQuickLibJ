package com.frame.fred_quick_lib.main.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.frame.fred_quick_lib.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}