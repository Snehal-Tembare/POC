package com.example.synerzip.poc.preferences;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Snehal Tembare on 17/11/16.
 * Copyright © 2016 Synerzip. All rights reserved
 */
public class PrefActivityShow extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().add(android.R.id.content,new FirstPrefActivity()).commit();

    }
}
