package com.example.synerzip.poc;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * Created by Snehal Tembare on 17/11/16.
 * Copyright © 2016 Synerzip. All rights reserved
 */
public class ListPref extends PreferenceActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager pm=getPreferenceManager();
        pm.setSharedPreferencesName("listpref");
        addPreferencesFromResource(R.xml.list_pref);
        ListPreference listPreference= (ListPreference) findPreference("citypref");
        listPreference.setEntries(R.array.city_names);
        listPreference.setEntryValues(R.array.city_values);




    }
}
