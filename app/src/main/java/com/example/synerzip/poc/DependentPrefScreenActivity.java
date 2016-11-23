package com.example.synerzip.poc;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class DependentPrefScreenActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.dependet_pref);

    }
}
