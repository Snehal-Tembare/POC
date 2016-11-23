package com.example.synerzip.poc;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Snehal Tembare on 18/11/16.
 * Copyright © 2016 Synerzip. All rights reserved
 */
public class PrefHeaderOne extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_header_one);
    }
}
