package com.example.synerzip.poc;

import android.preference.PreferenceActivity;

import java.util.List;

public class LoadHeader extends PreferenceActivity {

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_header,target);
    }


    @Override
    protected boolean isValidFragment(String fragmentName) {
        return true;
    }
}
