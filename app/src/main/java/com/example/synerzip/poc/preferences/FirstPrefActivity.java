package com.example.synerzip.poc.preferences;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.synerzip.poc.R;

/**
 * Created by Snehal Tembare on 16/11/16.
 * Copyright © 2016 Synerzip. All rights reserved
 */
public class FirstPrefActivity extends PreferenceFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);
    }
}
