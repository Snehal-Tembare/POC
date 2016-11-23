package com.example.synerzip.poc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DependactPrefActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dependent_pref);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_show_dependent)
    public void showDependentPref(){
        startActivity(new Intent(this,DependentPrefScreenActivity.class));

    }
}
